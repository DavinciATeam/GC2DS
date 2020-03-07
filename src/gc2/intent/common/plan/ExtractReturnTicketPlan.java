package gc2.intent.common.plan;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import gc2.intent.book_hsr.module.BookHSRTerm;
import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.DialogHistory;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class ExtractReturnTicketPlan extends AExecutionPlan {

	public ExtractReturnTicketPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
		String agentSay = dialogHistory.getLastAgentSay();
		for(Sentence sentence: sentences){
			String text = sentence.getText();
			Collection<String> notions = sentence.getNotion().values();
			if(notions.contains("@單程票")){
				thisRunMap.put(GoalTerm.去回程, "單程票");
				break;
			}else if(notions.contains("@來回票")){
				thisRunMap.put(GoalTerm.去回程, "來回票");
				break;
			}else if(agentSay.equals(AskBackTerm.單程票還是來回票呢) && text.contains("單程")){
				thisRunMap.put(GoalTerm.去回程, "單程票");
				break;
			}else if(agentSay.equals(AskBackTerm.單程票還是來回票呢) && text.contains("來回")){
				thisRunMap.put(GoalTerm.去回程, "來回票");
				break;
			}
		}
		// For Return Change
		if(extractMap.containsKey(BookHSRTerm.去回程)||thisRunMap.containsKey(BookHSRTerm.去回程)){
			this.transformReturn(intentMap, extractMap, thisRunMap);
		}
	}
	
	private void transformReturn(Map<String, Object> intentMap, Map<String, String> extractMap, Map<String, String> thisRunMap){
		String oldInfo = extractMap.get(BookHSRTerm.去回程);
		String newInfo = thisRunMap.get(BookHSRTerm.去回程);
		if(!oldInfo.equals(newInfo)){
			if(newInfo.equals("單程票")){
				String order = extractMap.remove(BookHSRTerm.去程車次);
				String date = extractMap.remove(BookHSRTerm.去程日期);
				String startTime = extractMap.remove(BookHSRTerm.去程出發時間);
				String arrivedTime = extractMap.remove(BookHSRTerm.去程抵達時間);
				extractMap.remove(BookHSRTerm.回程車次);
				extractMap.remove(BookHSRTerm.回程日期);
				extractMap.remove(BookHSRTerm.回程出發時間);
				extractMap.remove(BookHSRTerm.回程抵達時間);
				thisRunMap.put(BookHSRTerm.去程車次, order);
				thisRunMap.put(BookHSRTerm.去程日期, date);
				thisRunMap.put(BookHSRTerm.去程出發時間, startTime);
				thisRunMap.put(BookHSRTerm.去程抵達時間, arrivedTime);
				intentMap.put(IntentTerm.IsAchieveGoal, false);
			}else if(newInfo.equals("來回票")){
				String order = extractMap.remove(BookHSRTerm.車次);
				String date = extractMap.remove(BookHSRTerm.日期);
				String startTime = extractMap.remove(BookHSRTerm.出發時間);
				String arrivedTime = extractMap.remove(BookHSRTerm.抵達時間);
				thisRunMap.put(BookHSRTerm.去程車次, order);
				thisRunMap.put(BookHSRTerm.去程日期, date);
				thisRunMap.put(BookHSRTerm.去程出發時間, startTime);
				thisRunMap.put(BookHSRTerm.去程抵達時間, arrivedTime);
				intentMap.put(IntentTerm.IsAchieveGoal, false);
			}
		}
	}
}
