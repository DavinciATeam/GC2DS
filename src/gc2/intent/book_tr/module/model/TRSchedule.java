package gc2.intent.book_tr.module.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TRSchedule {
	private TROneWaySchedule nsSchedule = new TROneWaySchedule();
	private TROneWaySchedule snSchedule = new TROneWaySchedule();
	// Singleton
	private TRSchedule(){}
	private static TRSchedule hsrSchedule = new TRSchedule();
	public static TRSchedule getInstance(){
		if(hsrSchedule==null){
			hsrSchedule = new TRSchedule();
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
		TROneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int departureIndex = schedule.getStationIndex(departure);
		TRTime hsrTime = schedule.getHsrTimeList().get(orderIndex).get(departureIndex);
		String startTime = String.format("%02d",Integer.valueOf(hsrTime.getHour()))+":"+String.format("%02d",Integer.valueOf(hsrTime.getMinute()));
		return startTime;
	}
	
	// Find Order Map
	public Map<String, Object> findOrderMap(String departure, String destination, String startTime){
		Map<String, Object> map = new HashMap<String, Object>();
		TROneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		TRTime startHSRTime = new TRTime(startTime);
		for(int i=0; i<schedule.hsrTimeList.size(); i++){
			List<TRTime> hsrTimes = schedule.hsrTimeList.get(i);
			TRTime departurehsrTime = hsrTimes.get(departureIndex);
			if((startHSRTime.getHour()<departurehsrTime.getHour()) 
			   || (startHSRTime.getHour()==departurehsrTime.getHour() && startHSRTime.getMinute()<=departurehsrTime.getMinute())
			){
				TRTime destinationTime = hsrTimes.get(destinationIndex);
				if(destinationTime!=null){
					map.put("OrderIndex", i);
					map.put("OrderList", schedule.getHsrNumberList());
					break;
				}
			}
		}
		return map;
	}
	// Find Order
	public String findOrder(String departure, String destination, String startTime){
		String order = "";
		TROneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		TRTime startHSRTime = new TRTime(startTime);
		for(int i=0; i<schedule.hsrTimeList.size(); i++){
			List<TRTime> hsrTimes = schedule.hsrTimeList.get(i);
			TRTime departurehsrTime = hsrTimes.get(departureIndex);
			if((startHSRTime.getHour()<departurehsrTime.getHour()) 
			   || (startHSRTime.getHour()==departurehsrTime.getHour() && startHSRTime.getMinute()<=departurehsrTime.getMinute())
			){
				TRTime destinationTime = hsrTimes.get(destinationIndex);
				if(destinationTime!=null){
					order = schedule.getHsrNumberList().get(i);
					break;
				}
			}
		}
		return order;
	}
	private TROneWaySchedule selectSchedule(String departure, String destination, String startTime){
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
		TROneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int destinationIndex = schedule.getStationIndex(destination);
		TRTime hsrTime = schedule.getHsrTimeList().get(orderIndex).get(destinationIndex);
		String arrivedTime = String.format("%02d",Integer.valueOf(hsrTime.getHour()))+":"+String.format("%02d",Integer.valueOf(hsrTime.getMinute()));
		return arrivedTime;
	}
	private TROneWaySchedule selectSchedule(String order){
		if(nsSchedule.getHsrNumberList().contains(order)){
			return nsSchedule;
		}else{
			return snSchedule;
		}
	}
	public TROneWaySchedule getNsSchedule() {
		return nsSchedule;
	}
	public TROneWaySchedule getSnSchedule() {
		return snSchedule;
	}
	
}
