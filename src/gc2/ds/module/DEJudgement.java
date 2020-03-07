package gc2.ds.module;

import java.util.List;

import gc2.nlp.module.Sentence;

public class DEJudgement {
	
	public static boolean isAgree(String response){
		Sentence sentence = new Sentence(response);
		if(sentence.getText().contains("不")){ return false;}
		for(String word: sentence.getWords()){
			String notion = sentence.getNotion().get(word);
			if(notion.equals("@同意")){
				return true;
			}
		}
		return false;
	}
	
	public static int selectNumber(String answer, List<String> options){
		// Select Option
		double maxSimilarity = 0;
		int bestOption = 0;
		for(int i=0; i<options.size(); i++){
			LetterCosineSimilarity similarity = new LetterCosineSimilarity(answer, options.get(i));
			double cosine = similarity.cos();
			if(cosine > maxSimilarity){
				bestOption = i+1;
				maxSimilarity = cosine;
			}
		}
		// Not in options
		LetterCosineSimilarity similarity = new LetterCosineSimilarity(answer, "都不是");
		if(similarity.cos()>maxSimilarity){
			return 0;
		}else{
			return bestOption;
		}
	}
    
    public static boolean isChinese(String strName) {
		char[] charArray = strName.toCharArray();
		for (int i = 0; i<charArray.length; i++) {
			char c = charArray[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}
    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     * @param c
     * @return
     */
 	private static boolean isChinese(char c) {
 		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
 		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
 				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
 				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
 				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
 				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
 				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
 				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
 			return true;
 		}
 		return false;
 	}
}