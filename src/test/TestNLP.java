package test;

import java.util.List;

import tw.edu.ncu.csie.lab.core.ai.kt.module.sentence.SentenceHandler;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.Lexicon;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.Notion;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.POS;


public class TestNLP {

	public static void main(String[] args) {
		// Initial
		Lexicon.getInstance().load("./resource/nlp/lexicon/lexicon.txt");
		POS.getInstance().merge("./resource/nlp/pos/");
		Notion.getInstance().merge("./resource/nlp/notion/");
		//
//		String text = "給我一張台北到左營的車票";
//		String text = "高鐵訂票";
//		String text = "您好，我要三十張車票。";
//		String text = "三點左右";
		String text = "9:01";
		List<List<String>> words = SentenceHandler.analysisText(text);
		for(List<String> word: words){
			System.out.print(word.get(0)+"_"+word.get(1)+"_"+word.get(2)+", ");
//			System.out.println(Notion.getInstance().getTop(word.get(0)));
//			System.out.println(Notion.getInstance().getHierarchy(word.get(0)));
		}
		

	}

}
