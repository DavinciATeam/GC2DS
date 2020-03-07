package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestYMD {

	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
		String year = new SimpleDateFormat("yyyy").format(new Date());
		System.out.println(year);
		
		Date today = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(today);
		calendar.add(calendar.DATE,1);
		Date tomorrow = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String date = formatter.format(tomorrow);
		System.out.println(date);
	}

}
