package gc2.intent.reserve_hospital.module.model;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ncu.csie.lab.util.FileIO;

public class ReserveHospitalOneWaySchedule {
	protected List<List<ReserveHospitalTime>> hsrTimeList = new ArrayList<List<ReserveHospitalTime>>();
	protected List<String> hsrNumberList = new ArrayList<String>();
	protected List<String> hsrStations = new ArrayList<String>();
	
	public int getNumberIndex(String number){
		int index = -1;
		for(int i=0; i<hsrNumberList.size(); i++){
			if(hsrNumberList.get(i).equals(number)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int getStationIndex(String station){
		int index = -1;
		for(int i=0; i<hsrStations.size(); i++){
			if(hsrStations.get(i).equals(station)){
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
			hsrStations.add(stations[i]);
		}
		// Order & Time
		for(int i=1; i<lines.size(); i++){
			String[] items = lines.get(i).split(",");
			// Order
			String order = items[0];
			hsrNumberList.add(order);
			// Time
			List<ReserveHospitalTime> timeList = new ArrayList<ReserveHospitalTime>();
			for(int j=1; j<items.length; j++){
				String text = items[j];
				if(text.equals("")){
					timeList.add(null);
				}else{
					String[] time = text.split(":");
					ReserveHospitalTime hsrTime = new ReserveHospitalTime(Integer.valueOf(time[0]), Integer.valueOf(time[1]));
					timeList.add(hsrTime);
				}
			}
			hsrTimeList.add(timeList);
		}
	}

	public List<List<ReserveHospitalTime>> getHsrTimeList() {
		return hsrTimeList;
	}

	public List<String> getHsrNumberList() {
		return hsrNumberList;
	}

	public List<String> getHsrStations() {
		return hsrStations;
	}
}
