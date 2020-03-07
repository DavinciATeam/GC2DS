package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDateTime {

	public static void main(String[] args) {
		String text = "2019/01/30到2019/02/05";
		if(text.matches("(.*)([1,2])([0-9])([0-9])([0-9])/([0-1])([0-9])/([0-3])([0-9])(.*)")){
			System.out.println("success!");
		}else{
			System.out.println("fail!");
		}
		
		Pattern pattern = Pattern.compile("([1,2])([0-9])([0-9])([0-9])/([0-1])([0-9])/([0-3])([0-9])");
		Matcher matcher = pattern.matcher(text);
		int count = matcher.groupCount();
		System.out.println(count);
		while(matcher.find()){
			System.out.println(matcher.group(0));
		}
	}
	
	private void testTime(){
		String text = "9:01";
		if(text.matches("(.*)([0-9,11,12,13,14,15,16,17,18,19,20,21,22,23]):([0-5])([0-9])(.*)")){
			System.out.println("success!");
		}else{
			System.out.println("fail!");
		}
		
		Pattern pattern = Pattern.compile("([0-9,11,12,13,14,15,16,17,18,19,20,21,22,23]):([0-5])([0-9])");
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()){
			System.out.println(matcher.group(0));
		}
	}

}
