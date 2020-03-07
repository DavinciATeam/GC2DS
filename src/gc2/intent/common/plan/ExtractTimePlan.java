package gc2.intent.common.plan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.DialogHistory;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPUtil;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class ExtractTimePlan extends AExecutionPlan {

	public ExtractTimePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
		String agentSay = dialogHistory.getLastAgentSay();
		boolean isPM = this.isPM(dialogHistory);
		List<String> timeList = new ArrayList<String>();
		List<String> termList = new ArrayList<String>();
		// Extract Time
		for(Sentence sentence: sentences){
			String text = sentence.getText();
			if(text.matches("(.*)([0-9,11,12,13,14,15,16,17,18,19,20,21,22,23]):([0-5])([0-9])(.*)")){
				Pattern pattern = Pattern.compile("([0-9,11,12,13,14,15,16,17,18,19,20,21,22,23]):([0-5])([0-9])");
				Matcher matcher = pattern.matcher(text);
				boolean isBreak = false;
				while(matcher.find()){
					String time = matcher.group(0);
					timeList.add(time);
					termList.add(time);
					isBreak=true;
				}
				if(isBreak){ break;}
			}else if(text.matches("(.*)點(.*)分(.*)")){
				String time = this.getTimeHM(sentence, isPM, termList);
				if(!time.equals("")){ 
					timeList.add(time);
					break;
				}
			}else if(text.matches("(.*)點(.*)")){
				String time = this.getTimeH(sentence, isPM, termList);
				if(!time.equals("")){ 
					timeList.add(time);
					termList.add(time);
					break;
				}
			}
		}
		// Decide Goal Term
		if(timeList.size()>0){
			if(agentSay.equals(AskBackTerm.出發時間呢)){
				thisRunMap.put(GoalTerm.出發時間, timeList.get(0));
			}else if(agentSay.equals(AskBackTerm.去程的出發時間呢)){
				thisRunMap.put(GoalTerm.去程時間, timeList.get(0));
			}else if(agentSay.equals(AskBackTerm.回程的出發時間呢)){
				thisRunMap.put(GoalTerm.回程時間, timeList.get(0));
			}else if(agentSay.equals(AskBackTerm.請問幾點鐘出發)){
				thisRunMap.put(GoalTerm.出發時間, timeList.get(0));
			}else if(agentSay.equals(AskBackTerm.請問去程是幾點鐘出發)){
				thisRunMap.put(GoalTerm.去程時間, timeList.get(0));
			}else if(agentSay.equals(AskBackTerm.請問回程是幾點鐘出發)){
				thisRunMap.put(GoalTerm.回程時間, timeList.get(0));
			}else{
				String[] targetArray = {GoalTerm.出發時間, GoalTerm.去程時間, GoalTerm.回程時間};
				List<String> targets = Arrays.asList(targetArray);
				String target = NLPUtil.getTarget(sentences, termList.get(0), targets);
				if(!target.equals("")){
					thisRunMap.put(target, timeList.get(0));
				}
			}
		}
	}
	
	private String getTimeHM(Sentence sentence, boolean isPM, List<String> termList){
		int size = sentence.getWords().size();
		for(int i=0; i<size; i++){
			String word = sentence.getWords().get(i);
			String pos = sentence.getPos().get(word);
			if(pos.equals("CD")){
				if(i+3<size){
					String next1 = sentence.getWords().get(i+1);
					String next2 = sentence.getWords().get(i+2);
					String next3 = sentence.getWords().get(i+3);
					String pos2 = sentence.getPos().get(next2);
					if(next1.equals("點") && pos2.equals("CD") && next3.equals("分")){
						String hour = sentence.getNotion().get(word).replace("@", "");
						String min =sentence.getNotion().get(next2).replace("@", "");
						if(Integer.valueOf(hour)<13 && isPM){
							hour = String.valueOf(Integer.valueOf(hour)+12);
						}
						termList.add("點");
						return hour+":"+min;
					}
				}
			}
		}
		return "";
	}
	
	private String getTimeH(Sentence sentence, boolean isPM, List<String> termList){
		int size = sentence.getWords().size();
		for(int i=0; i<size; i++){
			String word = sentence.getWords().get(i);
			String pos = sentence.getPos().get(word);
			if(pos.equals("CD")){
				if(i+1<size){
					String next1 = sentence.getWords().get(i+1);
					if(next1.equals("點")){
						String hour = sentence.getNotion().get(word).replace("@", "");
						if(Integer.valueOf(hour)<13 && isPM){
							hour = String.valueOf(Integer.valueOf(hour)+12);
						}
						termList.add("點");
						return hour+":00";
					}
				}
			}
		}
		return "";
	}
	
	private boolean isPM(DialogHistory dialogHistory){
		int lastTimestamp = dialogHistory.getLastTimestamp();
		for(int i=0; i<lastTimestamp+1; i++){
			List<Sentence> responses = dialogHistory.getLastUserResponse();
			for(Sentence sentence: responses){
				Collection<String> notions = sentence.getNotion().values();
				if(notions.contains("@下午")){
					return true;
				}
			}
		}
		return false;
	}
	
	private String getKeyByValue(Map<String, String> map, String target){
		for(String key: map.keySet()){
			String value = map.get(key);
			if(value.equals(target)){
				return key;
			}
		}
		return "";
	}

}
