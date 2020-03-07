package gc2.intent.book_airplane.module.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirplaneSchedule {
	private AirplaneOneWaySchedule nsSchedule = new AirplaneOneWaySchedule();
	private AirplaneOneWaySchedule snSchedule = new AirplaneOneWaySchedule();
	// Singleton
	private AirplaneSchedule(){}
	private static AirplaneSchedule airplaneSchedule = new AirplaneSchedule();
	public static AirplaneSchedule getInstance(){
		if(airplaneSchedule==null){
			airplaneSchedule = new AirplaneSchedule();
		}
		return airplaneSchedule;
	}
	// Initial
	public void enhance(String pathNS, String pathSN){
		nsSchedule.enhance(pathNS);
		snSchedule.enhance(pathSN);
	}
	// Get Start Time
	public String getStartTime(String order, String departure){
		AirplaneOneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int departureIndex = schedule.getStationIndex(departure);
		AirplaneTime airplaneTime = schedule.getHsrTimeList().get(orderIndex).get(departureIndex);
		String startTime = String.format("%02d",Integer.valueOf(airplaneTime.getHour()))+":"+String.format("%02d",Integer.valueOf(airplaneTime.getMinute()));
		return startTime;
	}
	
	// Find Order Map
	public Map<String, Object> findOrderMap(String departure, String destination, String startTime){
		Map<String, Object> map = new HashMap<String, Object>();
		AirplaneOneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		AirplaneTime startAirplaneTime = new AirplaneTime(startTime);
		for(int i=0; i<schedule.airplaneTimeList.size(); i++){
			List<AirplaneTime> airplaneTimes = schedule.airplaneTimeList.get(i);
			AirplaneTime departureairplaneTime = airplaneTimes.get(departureIndex);
			if((startAirplaneTime.getHour()<departureairplaneTime.getHour()) 
			   || (startAirplaneTime.getHour()==departureairplaneTime.getHour() && startAirplaneTime.getMinute()<=departureairplaneTime.getMinute())
			){
				AirplaneTime destinationTime = airplaneTimes.get(destinationIndex);
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
		AirplaneOneWaySchedule schedule = this.selectSchedule(departure, destination, startTime);
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		AirplaneTime startAirplaneTime = new AirplaneTime(startTime);
		for(int i=0; i<schedule.airplaneTimeList.size(); i++){
			List<AirplaneTime> airplaneTimes = schedule.airplaneTimeList.get(i);
			AirplaneTime departureairplaneTime = airplaneTimes.get(departureIndex);
			if((startAirplaneTime.getHour()<departureairplaneTime.getHour()) 
			   || (startAirplaneTime.getHour()==departureairplaneTime.getHour() && startAirplaneTime.getMinute()<=departureairplaneTime.getMinute())
			){
				AirplaneTime destinationTime = airplaneTimes.get(destinationIndex);
				if(destinationTime!=null){
					order = schedule.getHsrNumberList().get(i);
					break;
				}
			}
		}
		return order;
	}
	private AirplaneOneWaySchedule selectSchedule(String departure, String destination, String startTime){
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
		AirplaneOneWaySchedule schedule = this.selectSchedule(order);
		int orderIndex = schedule.getNumberIndex(order);
		int destinationIndex = schedule.getStationIndex(destination);
		AirplaneTime airplaneTime = schedule.getHsrTimeList().get(orderIndex).get(destinationIndex);
		String arrivedTime = String.format("%02d",Integer.valueOf(airplaneTime.getHour()))+":"+String.format("%02d",Integer.valueOf(airplaneTime.getMinute()));
		return arrivedTime;
	}
	private AirplaneOneWaySchedule selectSchedule(String order){
		if(nsSchedule.getHsrNumberList().contains(order)){
			return nsSchedule;
		}else{
			return snSchedule;
		}
	}
	public AirplaneOneWaySchedule getNsSchedule() {
		return nsSchedule;
	}
	public AirplaneOneWaySchedule getSnSchedule() {
		return snSchedule;
	}
	
}
