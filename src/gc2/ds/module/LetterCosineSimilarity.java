package gc2.ds.module;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 字符串相似性匹配算法
 * 第一步，预处理主要是进行中文分词和去停用词，分词。
 * 第二步，列出所有的词。
 * 公共词 ：我爱北京甜安门喜欢吃烤鸭
 * 第三步，计算词频，写出词频向量。
 * 向量1：<1,1,1,1,1,1,1,0,0,0,0,0>
 * 向量2：<1,0,1,1,0,0,0,1,1,1,1,1>
 * 3/6 > cos =3/根号42 > 3/7即结果在3/6和3/7之间
 * @author Mick_Wu
 *
 */
public class LetterCosineSimilarity {
	// 数据结构解析:<单词,二维数组>,其中单词表示公共词，
	// 二维数组一维度表示句子一的向量,另一维度表示句子二的向量
	Map<Character, int[]> vectorMap = new HashMap<Character, int[]>();
	int[] tempArray = null;
	String candidate, inference;

	public LetterCosineSimilarity(String candidate, String inference) {
		this.candidate = candidate;
		this.inference = inference;
		for (Character character1 : candidate.toCharArray()) {
			if (vectorMap.containsKey(character1)) {
				vectorMap.get(character1)[0]=1;// 1 or 0
//				vectorMap.get(character1)[0]++;// Count Frequency
			} else {
				tempArray = new int[2];
				tempArray[0] = 1;
				tempArray[1] = 0;
				vectorMap.put(character1, tempArray);
			}
		}

		for (Character character2 : inference.toCharArray()) {
			if (vectorMap.containsKey(character2)) {
				vectorMap.get(character2)[1]=1;// 1 or 0
//				vectorMap.get(character2)[1]++;// Count Frequency
			} else {
				tempArray = new int[2];
				tempArray[0] = 0;
				tempArray[1] = 1;
				vectorMap.put(character2, tempArray);
			}
		}
	}
	/**
	 * Cos Similar
	 * @return
	 */
	public double cos() {
		double result = 0;
		result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
		return result;
	}
	
	
	public static boolean isEqual(double num1, double num2, int n){
		double approximation1 = new BigDecimal(num1).setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		double approximation2 = new BigDecimal(num2).setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return approximation1 == approximation2;
	}

	private double sqrtMulti(Map<Character, int[]> paramMap) {
		double result = 0;
		result = squares(paramMap);
		result = Math.sqrt(result);
		return result;
	}
	/**
	 * 平方和
	 * @param paramMap
	 * @return
	 */
	private double squares(Map<Character, int[]> paramMap) {
		double result1 = 0;
		double result2 = 0;
		Set<Character> keySet = paramMap.keySet();
		for (Character character : keySet) {
			int temp[] = paramMap.get(character);
			result1 += (temp[0] * temp[0]);
			result2 += (temp[1] * temp[1]);
		}
		return result1 * result2;
	}
	/**
	 * 內積
	 * @param paramMap
	 * @return
	 */
	private double pointMulti(Map<Character, int[]> paramMap) {
		double result = 0;
		for(Map.Entry<Character,int[]> entry: paramMap.entrySet()){
			Character character = entry.getKey();
			int temp[] = entry.getValue();
			result += (temp[0] * temp[1]);
		}
		return result;
	}
}
