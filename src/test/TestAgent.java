package test;

import gc2.GC2Agent;
import gc2.ds.module.SimpleGUI;
import gc2.intent.common.module.AskBackTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class TestAgent {

	public static void main(String[] args) {
		TestAgent t = new TestAgent();
		// Build GUI
		IListenAndSay gui = new SimpleGUI(AskBackTerm.請問需要什麼服務);
		// Test Cases
//		t.testBookHSR_1(gui);
//		t.testBookHSR_2(gui);
//		t.testBookHSR_3(gui);
		// Triger Agent
		GC2Agent agent = new GC2Agent(gui);
	}
	
	private void testBookHSR_3(IListenAndSay gui){
		// Create Form
		gui.getSentencePool().add("高鐵訂票");
		gui.getUserAnswerPool().add("單程");
		gui.getUserAnswerPool().add("吳牧哲");
		gui.getUserAnswerPool().add("b121729628");
		gui.getUserAnswerPool().add("台北到台中");
		gui.getUserAnswerPool().add("下午三點");
		gui.getUserAnswerPool().add("更晚");
		gui.getUserAnswerPool().add("不用");
		gui.getUserAnswerPool().add("明天");
		gui.getUserAnswerPool().add("對號");
		gui.getUserAnswerPool().add("全票");
		gui.getUserAnswerPool().add("一位");
		// Modify Form
		gui.getUserAnswerPool().add("改成二位");
		gui.getUserAnswerPool().add("改成今天");
		gui.getUserAnswerPool().add("改成優待票");
		gui.getUserAnswerPool().add("改成從板橋出發");
		gui.getUserAnswerPool().add("更晚");
		gui.getUserAnswerPool().add("不需要");
		gui.getUserAnswerPool().add("改成來回票");
		gui.getUserAnswerPool().add("不需要");
		gui.getUserAnswerPool().add("2019/10/01 十點半左右");
		gui.getUserAnswerPool().add("不需要");
		gui.getUserAnswerPool().add("改成自由座");
		gui.getUserAnswerPool().add("回程出發時間改成下午三點");
		gui.getUserAnswerPool().add("不需要");
		gui.getUserAnswerPool().add("輸出表單");
	}
	
	private void testBookHSR_2(IListenAndSay gui){
		gui.getSentencePool().add("您好，我要訂一張9月22日從高雄到台中的來回票。");
		gui.getUserAnswerPool().add("王先生");
		gui.getUserAnswerPool().add("A111111111");
		gui.getUserAnswerPool().add("台中到左營");
		gui.getUserAnswerPool().add("對號座");
		gui.getUserAnswerPool().add("明天下午");
		gui.getUserAnswerPool().add("三點左右");
		gui.getUserAnswerPool().add("不需要");
		gui.getUserAnswerPool().add("2019/10/01 十點半左右");
		gui.getUserAnswerPool().add("不需要");
		gui.getUserAnswerPool().add("輸出表單");
	}
	
	private void testBookHSR_1(IListenAndSay gui){
		gui.getSentencePool().add("高鐵訂票。您好，我要一張車票。");
		gui.getUserAnswerPool().add("單程");
		gui.getUserAnswerPool().add("蔡依玲");
		gui.getUserAnswerPool().add("A222222222");
		gui.getUserAnswerPool().add("台北到台中");
		gui.getUserAnswerPool().add("2019/09/25  9:00");
		gui.getUserAnswerPool().add("不用");
		gui.getUserAnswerPool().add("對號座");
		gui.getUserAnswerPool().add("輸出表單");
	}

}
