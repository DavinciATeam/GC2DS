package gc2.intent.reserve_hospital.module.model;

public class ReserveHospitalTime {
	private Integer hour;
	private Integer minute;

	public ReserveHospitalTime(String text){
		String[] time = text.split(":");
		this.hour = Integer.valueOf(time[0]);
		this.minute = Integer.valueOf(time[1]);
	}
	
	public ReserveHospitalTime(Integer hour, Integer minute){
		this.hour = hour;
		this.minute = minute;
	}

	public Integer getHour() {
		return hour;
	}

	public Integer getMinute() {
		return minute;
	}
}
