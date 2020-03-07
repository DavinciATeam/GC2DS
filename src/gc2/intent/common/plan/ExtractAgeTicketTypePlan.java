package gc2.intent.common.plan;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.DialogHistory;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class ExtractAgeTicketTypePlan extends AExecutionPlan {

	public ExtractAgeTicketTypePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
		String agentSay = dialogHistory.getLastAgentSay();
		for(Sentence sentence: sentences){
			String text = sentence.getText();
			Collection<String> notions = sentence.getNotion().values();
			if(notions.contains("@對號座全票")){
				thisRunMap.put(GoalTerm.座位票種, "對號座");
				thisRunMap.put(GoalTerm.年齡票種, "全票");
				break;
			}else if(notions.contains("@自由座全票")){
				thisRunMap.put(GoalTerm.座位票種, "自由座全票");
				thisRunMap.put(GoalTerm.年齡票種, "全票");
				break;
			}else if(notions.contains("@對號座優待票")){
				thisRunMap.put(GoalTerm.座位票種, "對號座優待票");
				thisRunMap.put(GoalTerm.年齡票種, "優待票");
				break;
			}else if(notions.contains("@自由座優待票")){
				thisRunMap.put(GoalTerm.座位票種, "自由座優待票");
				thisRunMap.put(GoalTerm.年齡票種, "優待票");
				break;
			}else if(agentSay.equals(AskBackTerm.全票還是優待票) && text.contains("全")){
				thisRunMap.put(GoalTerm.年齡票種, "全票");
				break;
			}else if(agentSay.equals(AskBackTerm.全票還是優待票) && text.contains("優待")){
				thisRunMap.put(GoalTerm.年齡票種, "優待票");
				break;
			}else if(text.contains("全票")){
				thisRunMap.put(GoalTerm.年齡票種, "全票");
				break;
			}else if(text.contains("優待票")){
				thisRunMap.put(GoalTerm.年齡票種, "優待票");
				break;
			}
		}
	}

}
