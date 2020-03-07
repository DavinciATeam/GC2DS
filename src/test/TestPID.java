package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPID {
	public static void main(String[] args) {
		String text = "我的身分證字號是A222222222";
		
		if(text.matches("(.*)([a-z,A-z])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])(.*)")){
			System.out.println("success!");
		}else{
			System.out.println("fail!");
		}
		
		Pattern pattern = Pattern.compile("([a-z,A-z])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])([0-9])");
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()){
			System.out.println(matcher.group(0));
		}
	}
}
