package gc2.intent.book_hsr.module.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HSRSchedule {
	private HSROneWaySchedule nsSchedule = new HSROneWaySchedule();
	private HSROneWaySchedule snSchedule = new HSROneWaySchedule();
	// Singleton
	private HSRSchedule(){}
	private static HSRSchedule hsrSchedule = new HSRSchedule();
	public static HSRSchedule getInstance(){
		if(hsrSchedule==null){
			hsrSchedule = new HSRSchedule();
		}
		return hsrSchedule;
	}
	// Initial
	public void enhance(String pathNS, String pathSN){
		nsSchedule.enhance(pathNS);
		snSchedule.enhance(pathSN);
	}
	// Get Start Time
	public String getStartTime(String order, String departure){
		HSROneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int departureIndex = schedule.getStationIndex(departure);
		HSRTime hsrTime = schedule.getHsrTimeList().get(orderIndex).get(departureIndex);
		String startTime = String.format("%02d",Integer.valueOf(hsrTime.getHour()))+":"+String.format("%02d",Integer.valueOf(hsrTime.getMinute()));
		return startTime;
	}
	
	// Find Order Map
	public Map<String, Object> findOrderMap(String departure, String destination, String startTime){
		Map<String, Object> map = new HashMap<String, Object>();
		HSROneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		HSRTime startHSRTime = new HSRTime(startTime);
		for(int i=0; i<schedule.hsrTimeList.size(); i++){
			List<HSRTime> hsrTimes = schedule.hsrTimeList.get(i);
			HSRTime departurehsrTime = hsrTimes.get(departureIndex);
			if(departurehsrTime!=null){
				if((startHSRTime.getHour()<departurehsrTime.getHour()) 
				   ||(startHSRTime.getHour()==departurehsrTime.getHour() && startHSRTime.getMinute()<=departurehsrTime.getMinute())
				){
					HSRTime destinationTime = hsrTimes.get(destinationIndex);
					if(destinationTime!=null){
						map.put("OrderIndex", i);
						map.put("OrderList", schedule.getHsrNumberList());
						map.put("Schedule", schedule);
						break;
					}
				}
			}
		}
		if(map.size()==0){
			map.put("OrderIndex", this.earlyNonNullIndex(schedule, schedule.getHsrTimeList().size()-1, departureIndex, destinationIndex));
			map.put("OrderList", schedule.getHsrNumberList());
			map.put("Schedule", schedule);
		}
		return map;
	}
	// Find Order
	public String findOrder(String departure, String destination, String startTime){
		String order = "";
		HSROneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		HSRTime startHSRTime = new HSRTime(startTime);
		for(int i=0; i<schedule.hsrTimeList.size(); i++){
			List<HSRTime> hsrTimes = schedule.hsrTimeList.get(i);
			HSRTime departurehsrTime = hsrTimes.get(departureIndex);
			if(departurehsrTime!=null){
				if((startHSRTime.getHour()<departurehsrTime.getHour()) 
				   ||(startHSRTime.getHour()==departurehsrTime.getHour() && startHSRTime.getMinute()<=departurehsrTime.getMinute())
				){
					HSRTime destinationTime = hsrTimes.get(destinationIndex);
					if(destinationTime!=null){
						order = schedule.getHsrNumberList().get(i);
						break;
					}
				}
			}
		}
		return order;
	}
	
	public int earlyNonNullIndex(HSROneWaySchedule schedule, int start, int departureIndex, int destinationIndex){
		int index = start;
		for(int i=start-1; i>-1; i--){
			if(schedule.hsrTimeList.get(i).get(departureIndex)!=null && schedule.hsrTimeList.get(i).get(destinationIndex)!=null){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int lateNonNullIndex(HSROneWaySchedule schedule, int start, int departureIndex, int destinationIndex){
		int index = start;
		for(int i=start+1; i<schedule.getHsrTimeList().size(); i++){
			if(schedule.hsrTimeList.get(i).get(departureIndex)!=null && schedule.hsrTimeList.get(i).get(destinationIndex)!=null){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public HSROneWaySchedule selectSchedule(String departure, String destination, String startTime){
		int departureIndex = nsSchedule.getStationIndex(departure);
		int destinationIndex = nsSchedule.getStationIndex(destination);
		if(departureIndex<destinationIndex){
			return nsSchedule;
		}else{
			return snSchedule;
		}
	}
	// Find Arrived Time
	public String findArrivedTime(String order, String destination){
		HSROneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int destinationIndex = schedule.getStationIndex(destination);
		HSRTime hsrTime = schedule.getHsrTimeList().get(orderIndex).get(destinationIndex);
		String arrivedTime = String.format("%02d",Integer.valueOf(hsrTime.getHour()))+":"+String.format("%02d",Integer.valueOf(hsrTime.getMinute()));
		return arrivedTime;
	}
	public HSROneWaySchedule selectSchedule(String order){
		if(nsSchedule.getHsrNumberList().contains(order)){
			return nsSchedule;
		}else{
			return snSchedule;
		}
	}
	public HSROneWaySchedule getNsSchedule() {
		return nsSchedule;
	}
	public HSROneWaySchedule getSnSchedule() {
		return snSchedule;
	}
	
}
