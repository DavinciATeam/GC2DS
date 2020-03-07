package gc2.intent.book_movie.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.book_movie.module.BookMovieTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchBookMoviePlan extends AExecutionPlan {

	public MatchBookMoviePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		
		if(!keySet.contains(BookMovieTerm.電影)){
			intentMap.put(IntentTerm.AskBack, "您好請問您要看哪一部電影呢");
		}else if(!keySet.contains(BookMovieTerm.電影)){
			intentMap.put(IntentTerm.AskBack, "您要看哪一部電影呢");			
		}else if(!keySet.contains(BookMovieTerm.學生票)){
			intentMap.put(IntentTerm.AskBack, "請問有學生軍警優惠嗎");
		}else if(!keySet.contains(BookMovieTerm.軍警票)){
			intentMap.put(IntentTerm.AskBack, "請問有學生軍警優惠嗎");
		}else if(!keySet.contains(BookMovieTerm.票券聯絡人)){
			intentMap.put(IntentTerm.AskBack, "請問票券聯絡人的大名");
		}else if(!keySet.contains(BookMovieTerm.連絡電話)){
			intentMap.put(IntentTerm.AskBack, "請問連絡電話是");
		}else if(!keySet.contains(BookMovieTerm.放映廳)){
			intentMap.put(IntentTerm.AskBack, "請問放映廳");
		}else if(!keySet.contains(BookMovieTerm.全票)){
			intentMap.put(IntentTerm.AskBack, "請問要訂全票");
		}else if(!keySet.contains(BookMovieTerm.早場票)){
			intentMap.put(IntentTerm.AskBack, "請問要訂早場票");
		}else if(!keySet.contains(BookMovieTerm.學生票)){
			intentMap.put(IntentTerm.AskBack, "請問要訂學生票");
		}else if(!keySet.contains(BookMovieTerm.軍警票)){
			intentMap.put(IntentTerm.AskBack, "請問要訂軍警票");
		}else if(!keySet.contains(BookMovieTerm.日期)){
			intentMap.put(IntentTerm.AskBack, "請問要訂日期");
		}else if(!keySet.contains(BookMovieTerm.廳號)){
			intentMap.put(IntentTerm.AskBack, "請問廳號");
		}else if(!keySet.contains(BookMovieTerm.星期)){
			intentMap.put(IntentTerm.AskBack, "請問星期");												
		}else if(!keySet.contains(BookMovieTerm.電影院)){
			intentMap.put(IntentTerm.AskBack, "請問要哪個電影院");												
		}else if(!keySet.contains(BookMovieTerm.票數)){
			intentMap.put(IntentTerm.AskBack, "請問票數");												
		}else if(!keySet.contains(BookMovieTerm.票數)){
			intentMap.put(IntentTerm.AskBack, "請問幾張票");										
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}

}
