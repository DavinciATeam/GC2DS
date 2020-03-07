package gc2.intent.book_hsr.module.model;

public class HSRTime {
	private Integer hour;
	private Integer minute;

	public HSRTime(String text){
		String[] time = text.split(":");
		this.hour = Integer.valueOf(time[0]);
		this.minute = Integer.valueOf(time[1]);
	}
	
	public HSRTime(Integer hour, Integer minute){
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
