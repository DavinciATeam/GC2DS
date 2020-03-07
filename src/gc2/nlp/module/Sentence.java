package gc2.nlp.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.ncu.csie.lab.core.ai.kt.module.sentence.SentenceHandler;

public class Sentence {
	
	private String text;
	private List<String> words = new ArrayList<String>();
	private Map<String, String> pos = new HashMap<String, String>();
	private Map<String, String> notion = new HashMap<String, String>();
	// Sentence Type: 肯定(A)，否定(N);一般(G)，疑問(Q)，感嘆(E)，祈使(I)
	private String type = "";
	
	public Sentence(String text){
		this.text = text;
		List<List<String>> wordLList = SentenceHandler.analysisText(text);
		for(int i=0; i<wordLList.size(); i++){
			List<String> wordList = wordLList.get(i);
			this.words.add(wordList.get(0));
			this.pos.put(wordList.get(0), wordList.get(1));
			this.notion.put(wordList.get(0), wordList.get(2));
		}
		this.type = this.generateType();
	}
	
	private String generateType(){
		StringBuffer type = new StringBuffer();
		if(this.text.matches("(.*)([不|沒])(.*)")){
			type.append("N");
		}else{
			type.append("A");
		}
		if(this.text.matches("(.*)([嗎|如何|什麼|哪裡])(.*)")){
			type.append("Q");
		}else if(this.text.matches("(.*)([吧|啊|喔])(.*)")){
			type.append("E");
		}else if(this.pos.get(this.words.get(0)).equals("V")){
			type.append("I");
		}else{
			type.append("G");
		}
		return type.toString();
	}
	
	public String findClosestLeftTerm(String word, List<String> targets){
		String term = "";
		if(this.words.contains(word)){
			int wordIndex = this.findWordIndex(word);
			for(int i=wordIndex-1; i>-1; i--){
				String temp = this.words.get(i);
				if(targets.contains(temp)){
					term = temp;
					break;
				}
			}
		}
		return term;
	}
	
	public int findWordIndex(String word){
		int index = -1;
		for(int i=this.words.size()-1; i>-1; i--){
			if(this.words.get(i).equals(word)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	

	public String getText() {
		return text;
	}

	public List<String> getWords() {
		return words;
	}

	public Map<String, String> getPos() {
		return pos;
	}

	public Map<String, String> getNotion() {
		return notion;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Sentence:{ text:" + text + ", words:" + words + ", pos:" + pos + ", notion:" + notion + ", type:" + type+ "}";
	}
	
	

}
