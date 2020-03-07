package gc2.intent.common.plan;

import java.util.ArrayList;
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

public class ExtractPlacePlan extends AExecutionPlan {

	public ExtractPlacePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
		String agentSay = dialogHistory.getLastAgentSay();
		// Find All Places
		List<String> places = new ArrayList<String>();
		StringBuffer text = new StringBuffer();
		for(Sentence sentence: sentences){
			text.append(sentence.getText());
			for(String word: sentence.getWords()){
				String notion = sentence.getNotion().get(word);
				if(notion.equals("@地點")){
					places.add(word);
				}
			}
		}
		// Decide Departure & Destination
		if(places.size()==2){
			thisRunMap.put(GoalTerm.出發地, places.get(0));
			thisRunMap.put(GoalTerm.目的地, places.get(1));
		}else if(places.size()==1){
			if(agentSay.equals(AskBackTerm.要從哪一站出發)){
				thisRunMap.put(GoalTerm.出發地, places.get(0));
			}else if(agentSay.equals(AskBackTerm.目的地是哪一站)){
				thisRunMap.put(GoalTerm.目的地, places.get(0));
			}else if(text.toString().contains("從")||text.toString().contains("出發")){
				if(extractMap.containsKey(GoalTerm.目的地)){
					if(!places.get(0).equals(extractMap.get(GoalTerm.出發地)) && !places.get(0).equals(extractMap.get(GoalTerm.目的地))){
						thisRunMap.put(GoalTerm.出發地, places.get(0));
					}
				}else{
					thisRunMap.put(GoalTerm.出發地, places.get(0));
				}
			}else if(text.toString().contains("到")||text.toString().contains("去")||text.toString().contains("目的地")){
				if(!places.get(0).equals(extractMap.get(GoalTerm.出發地)) && !places.get(0).equals(extractMap.get(GoalTerm.目的地))){
					if(!places.get(0).equals(extractMap.get(GoalTerm.出發地))){
						thisRunMap.put(GoalTerm.目的地, places.get(0));
					}
				}else{
					thisRunMap.put(GoalTerm.目的地, places.get(0));
				}
			}
		}
	}
}
