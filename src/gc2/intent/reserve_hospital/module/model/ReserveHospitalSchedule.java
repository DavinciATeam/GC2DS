package gc2.intent.reserve_hospital.module.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReserveHospitalSchedule {
	private ReserveHospitalOneWaySchedule nsSchedule = new ReserveHospitalOneWaySchedule();
	private ReserveHospitalOneWaySchedule snSchedule = new ReserveHospitalOneWaySchedule();
	// Singleton
	private ReserveHospitalSchedule(){}
	private static ReserveHospitalSchedule hsrSchedule = new ReserveHospitalSchedule();
	public static ReserveHospitalSchedule getInstance(){
		if(hsrSchedule==null){
			hsrSchedule = new ReserveHospitalSchedule();
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
		ReserveHospitalOneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int departureIndex = schedule.getStationIndex(departure);
		ReserveHospitalTime hsrTime = schedule.getHsrTimeList().get(orderIndex).get(departureIndex);
		String startTime = String.format("%02d",Integer.valueOf(hsrTime.getHour()))+":"+String.format("%02d",Integer.valueOf(hsrTime.getMinute()));
		return startTime;
	}
	
	// Find Order Map
	public Map<String, Object> findOrderMap(String departure, String destination, String startTime){
		Map<String, Object> map = new HashMap<String, Object>();
		ReserveHospitalOneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		ReserveHospitalTime startHSRTime = new ReserveHospitalTime(startTime);
		for(int i=0; i<schedule.hsrTimeList.size(); i++){
			List<ReserveHospitalTime> hsrTimes = schedule.hsrTimeList.get(i);
			ReserveHospitalTime departurehsrTime = hsrTimes.get(departureIndex);
			if((startHSRTime.getHour()<departurehsrTime.getHour()) 
			   || (startHSRTime.getHour()==departurehsrTime.getHour() && startHSRTime.getMinute()<=departurehsrTime.getMinute())
			){
				ReserveHospitalTime destinationTime = hsrTimes.get(destinationIndex);
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
		ReserveHospitalOneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		ReserveHospitalTime startHSRTime = new ReserveHospitalTime(startTime);
		for(int i=0; i<schedule.hsrTimeList.size(); i++){
			List<ReserveHospitalTime> hsrTimes = schedule.hsrTimeList.get(i);
			ReserveHospitalTime departurehsrTime = hsrTimes.get(departureIndex);
			if((startHSRTime.getHour()<departurehsrTime.getHour()) 
			   || (startHSRTime.getHour()==departurehsrTime.getHour() && startHSRTime.getMinute()<=departurehsrTime.getMinute())
			){
				ReserveHospitalTime destinationTime = hsrTimes.get(destinationIndex);
				if(destinationTime!=null){
					order = schedule.getHsrNumberList().get(i);
					break;
				}
			}
		}
		return order;
	}
	private ReserveHospitalOneWaySchedule selectSchedule(String departure, String destination, String startTime){
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
		ReserveHospitalOneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int destinationIndex = schedule.getStationIndex(destination);
		ReserveHospitalTime hsrTime = schedule.getHsrTimeList().get(orderIndex).get(destinationIndex);
		String arrivedTime = String.format("%02d",Integer.valueOf(hsrTime.getHour()))+":"+String.format("%02d",Integer.valueOf(hsrTime.getMinute()));
		return arrivedTime;
	}
	private ReserveHospitalOneWaySchedule selectSchedule(String order){
		if(nsSchedule.getHsrNumberList().contains(order)){
			return nsSchedule;
		}else{
			return snSchedule;
		}
	}
	public ReserveHospitalOneWaySchedule getNsSchedule() {
		return nsSchedule;
	}
	public ReserveHospitalOneWaySchedule getSnSchedule() {
		return snSchedule;
	}
	
}
