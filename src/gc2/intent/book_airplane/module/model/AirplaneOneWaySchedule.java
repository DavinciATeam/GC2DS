package gc2.intent.book_airplane.module.model;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ncu.csie.lab.util.FileIO;

public class AirplaneOneWaySchedule {
	protected List<List<AirplaneTime>> airplaneTimeList = new ArrayList<List<AirplaneTime>>();
	protected List<String> airplaneNumberList = new ArrayList<String>();
	protected List<String> airplaneStations = new ArrayList<String>();
	
	public int getNumberIndex(String number){
		int index = -1;
		for(int i=0; i<airplaneNumberList.size(); i++){
			if(airplaneNumberList.get(i).equals(number)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int getStationIndex(String station){
		int index = -1;
		for(int i=0; i<airplaneStations.size(); i++){
			if(airplaneStations.get(i).equals(station)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void enhance(String path){
		List<String> lines = FileIO.readFile(path, "UTF-8");
		// Station
		String[] stations = lines.get(0).split(",");
		for(int i=1; i<stations.length; i++){
			airplaneStations.add(stations[i]);
		}
		// Order & Time
		for(int i=1; i<lines.size(); i++){
			String[] items = lines.get(i).split(",");
			// Order
			String order = items[0];
			airplaneNumberList.add(order);
			// Time
			List<AirplaneTime> timeList = new ArrayList<AirplaneTime>();
			for(int j=1; j<items.length; j++){
				String text = items[j];
				if(text.equals("")){
					timeList.add(null);
				}else{
					String[] time = text.split(":");
					AirplaneTime airplaneTime = new AirplaneTime(Integer.valueOf(time[0]), Integer.valueOf(time[1]));
					timeList.add(airplaneTime);
				}
			}
			airplaneTimeList.add(timeList);
		}
	}

	public List<List<AirplaneTime>> getHsrTimeList() {
		return airplaneTimeList;
	}

	public List<String> getHsrNumberList() {
		return airplaneNumberList;
	}

	public List<String> getHsrStations() {
		return airplaneStations;
	}
}
