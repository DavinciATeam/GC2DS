package gc2.intent.book_tr.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.book_tr.module.BookTRTerm;
import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchBookTRPlan extends AExecutionPlan {

	public MatchBookTRPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(BookTRTerm.姓名)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問您的姓名);
		}else if(!keySet.contains(BookTRTerm.身分證)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問身分證號碼);
		}else if(!keySet.contains(BookTRTerm.出發地) && !keySet.contains(BookTRTerm.目的地)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.要從哪裡出發去哪裡);
		}else if(!keySet.contains(BookTRTerm.出發地)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.要從哪一站出發);
		}else if(!keySet.contains(BookTRTerm.目的地)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.目的地是哪一站);
		}else if(!keySet.contains(BookTRTerm.出發時間) && !keySet.contains(BookTRTerm.日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.出發時間呢);
		}else if(!keySet.contains(BookTRTerm.日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.出發日期呢);
		}else if(!keySet.contains(BookTRTerm.出發時間)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問幾點鐘出發);
		}else if(!keySet.contains(BookTRTerm.車種)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.想搭乘什麼車種呢);
		}else if(!keySet.contains(BookTRTerm.票數)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.幾位乘客搭乘);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}

}
