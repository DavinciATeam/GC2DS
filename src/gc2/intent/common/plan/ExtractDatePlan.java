package gc2.intent.common.plan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

public class ExtractDatePlan extends AExecutionPlan {

	public ExtractDatePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
		String agentSay = dialogHistory.getLastAgentSay();
		List<String> dates = new ArrayList<String>();
		List<String> termList = new ArrayList<String>();
		// Extract Date
		this.extractDate(sentences, dates, termList);
		// Set Goal Term
		if(dates.size()>0){
			this.setGoalTerm(thisRunMap, dates, termList, agentSay, sentences);
		}
	}
	
	private void setGoalTerm(Map<String, String> thisRunMap, List<String> dates, List<String> termList, String agentSay, List<Sentence> sentences){
		if(dates.size()==2){
			if(agentSay.equals(AskBackTerm.請問您是幾號出國幾號回來)){
				thisRunMap.put(GoalTerm.去程日期, dates.get(0));
				thisRunMap.put(GoalTerm.回程日期, dates.get(1));
			}
		}else{
			if(agentSay.equals(AskBackTerm.出發時間呢)){
				thisRunMap.put(GoalTerm.日期, dates.get(0));
			}else if(agentSay.equals(AskBackTerm.出發日期呢)){
				thisRunMap.put(GoalTerm.日期, dates.get(0));
			}else if(agentSay.equals(AskBackTerm.去程的出發時間呢)){	
				thisRunMap.put(GoalTerm.去程日期, dates.get(0));
			}else if(agentSay.equals(AskBackTerm.請問去程的日期)){
				thisRunMap.put(GoalTerm.去程日期, dates.get(0));
			}else if(agentSay.equals(AskBackTerm.回程的出發時間呢)){
				thisRunMap.put(GoalTerm.回程日期, dates.get(0));
			}else if(agentSay.equals(AskBackTerm.請問回程的日期)){
				thisRunMap.put(GoalTerm.回程日期, dates.get(0));
			}else if(agentSay.equals(AskBackTerm.好的再麻煩您提供出生年月日)){
				thisRunMap.put(GoalTerm.出生日期, dates.get(0));
			}else{ //TODO 更改資訊
				String[] targetArray = {GoalTerm.日期, GoalTerm.去程日期, GoalTerm.回程日期, GoalTerm.出生日期};
				List<String> targets = Arrays.asList(targetArray);
				String target = NLPUtil.getTarget(sentences, termList.get(0), targets);
				if(target.equals("")){
					thisRunMap.put(GoalTerm.日期, dates.get(0));
				}else{
					thisRunMap.put(target, dates.get(0));
				}
			}
		}
	}
	
	private void extractDate(List<Sentence> sentences, List<String> dates, List<String> termList){
		for(Sentence sentence: sentences){
			String text = sentence.getText();
			if(text.matches("(.*)([1,2])([0-9])([0-9])([0-9])/([0-1])([0-9])/([0-3])([0-9])(.*)")){
				Pattern pattern = Pattern.compile("([1,2])([0-9])([0-9])([0-9])/([0-1])([0-9])/([0-3])([0-9])");
				Matcher matcher = pattern.matcher(text);
				boolean isBreak = false;
				while(matcher.find()){
					String date = matcher.group(0);
					dates.add(date);
					termList.add(date);
					isBreak=true;
				}
				if(isBreak){ break;}
			}else if(text.matches("(.*)([1,2])([0-9])([0-9])([0-9])/([1-9])/([0-3])([0-9])(.*)")){
				Pattern pattern = Pattern.compile("([1,2])([0-9])([0-9])([0-9])/([1-9])/([0-3])([0-9])");
				Matcher matcher = pattern.matcher(text);
				boolean isBreak = false;
				while(matcher.find()){
					String date = matcher.group(0);
					dates.add(date);
					termList.add(date);
					isBreak=true;
				}
				if(isBreak){ break;}
			}else if(text.matches("(.*)月(.*)日(.*)")){
				String date = this.getMD(sentence);
				if(!date.equals("")){ 
					dates.add(date);
					termList.add(date);
					break;
				}
			}else if(text.matches("(.*)([今天,明天,後天])(.*)")){
				String date = this.getDateByName(text, termList);
				if(!date.equals("")){ 
					dates.add(date);
					break;
				}
			}
		}
	}
	
	private String getMD(Sentence sentence){
		int size = sentence.getWords().size();
		for(int i=0; i<size; i++){
			String word = sentence.getWords().get(i);
			String pos = sentence.getPos().get(word);
			if(pos.equals("CD")){
				if(i+3<size-1){
					String next1 = sentence.getWords().get(i+1);
					String next2 = sentence.getWords().get(i+2);
					String next3 = sentence.getWords().get(i+3);
					String pos2 = sentence.getPos().get(next2);
					if(next1.equals("月") && pos2.equals("CD") && next3.equals("日")){
						String year = new SimpleDateFormat("yyyy").format(new Date());
						String notion = sentence.getNotion().get(word);
						String notion2 =sentence.getNotion().get(next2);
						return year+"/"+notion.replace("@", "")+"/"+notion2.replace("@", "");
					}
				}
			}
		}
		return "";
	}
	
	private String getDateByName(String text, List<String> termList){
		String date = "";
		if(text.matches("(.*)今天(.*)")){
			date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			termList.add("今天");
		}else if(text.matches("(.*)明天(.*)")){
			Date today = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(today);
			calendar.add(calendar.DATE,1);
			Date tomorrow = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			date = formatter.format(tomorrow);
			termList.add("明天");
		}else if(text.matches("(.*)後天(.*)")){
			Date today = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(today);
			calendar.add(calendar.DATE,2);
			Date dayAfterTomorrow = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			date = formatter.format(dayAfterTomorrow);
			termList.add("後天");
		}
		return date;
	}

}
