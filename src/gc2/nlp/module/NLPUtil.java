package gc2.nlp.module;

import java.util.ArrayList;
import java.util.List;

public class NLPUtil {
	
	private static List<String> unit = new ArrayList<String>();
	static{
		unit.add("張");
	}
	
	public static boolean isUnit(String text){
		if(unit.contains(text)){
			return true;
		}
		return false;
	}
	
	public static String getTarget(List<Sentence> sentences, String word, List<String> targets){
		String target = "";
		for(int i=sentences.size()-1; i>-1; i--){
			Sentence sentence = sentences.get(i);
			String temp = sentence.findClosestLeftTerm(word, targets);
			if(!temp.equals("")){
				target = temp;
			}
		}
		return target;
	}
}
