package gc2.intent.book_airplane.module.model;

public class AirplaneTime {
	private Integer hour;
	private Integer minute;

	public AirplaneTime(String text){
		String[] time = text.split(":");
		this.hour = Integer.valueOf(time[0]);
		this.minute = Integer.valueOf(time[1]);
	}
	
	public AirplaneTime(Integer hour, Integer minute){
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
