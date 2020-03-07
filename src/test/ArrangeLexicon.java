package test;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ncu.csie.lab.util.FileIO;

public class ArrangeLexicon {

	public static void main(String[] args) {
		List<String> lines = FileIO.readFile("resource/nlp/lexicon/lexicon.txt", "UTF-8");
		List<String> keep = new ArrayList<String>();
		List<String> output = new ArrayList<String>();
		for(String line: lines){
			line = line.trim();
			if(line.startsWith("##")){
				output.add(line);
			}else if(keep.contains(line)){
				output.add("## "+line);
			}else{
				keep.add(line);
				output.add(line);
			}
		}
		for(String line: output){
			System.out.println(line);
		}
	}

}
