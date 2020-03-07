package test;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ncu.csie.lab.util.FileIO;

public class ArrangeNotion {

	public static void main(String[] args) {
		List<String> lines = FileIO.readFile("resource/nlp/notion/notion_dialog.kt", "UTF-8");
		List<String> keep = new ArrayList<String>();
		List<String> output = new ArrayList<String>();
		for(String line: lines){
			line = line.trim();
			if(line.contains("@")){
			output.add(line.replaceAll("#", "").trim());
			}else if(line.startsWith("##")){
				output.add(line);
			}else{
				output.add("### "+line);
			}
		}
		for(String line: output){
			System.out.println(line);
		}

	}

}
