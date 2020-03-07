package gc2.intent.common.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.DialogHistory;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class ExtractTicketCountPlan extends AExecutionPlan {

	public ExtractTicketCountPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		String intent = (String) ligand.getReference().get(IntentTerm.Intent);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
		String agentSay = dialogHistory.getLastAgentSay();
		List<String> count = new ArrayList<String>();
		for(Sentence sentence: sentences){
			int size = sentence.getWords().size();
			for(int i=0; i<size; i++){
				String word = sentence.getWords().get(i);
				String pos = sentence.getPos().get(word);
				String notion = sentence.getNotion().get(word);
				if(pos.equals("CD")){
					if(i+1<=size-1){
						String next = sentence.getWords().get(i+1);
						if(next.equals("張")){
							thisRunMap.put(GoalTerm.票數, notion.replace("@", ""));
							break;
						}else if(next.equals("位") && intent.equals(NLPTerm.IntentBookHSR)){
							thisRunMap.put(GoalTerm.票數, notion.replace("@", ""));
							break;
						}
					}
					count.add(notion.replace("@", ""));
				}
			}
		}
		if(!thisRunMap.containsKey(GoalTerm.票數) && agentSay.equals(AskBackTerm.幾位乘客搭乘)){
			thisRunMap.put(GoalTerm.票數, count.get(0));
		}
	}

}
