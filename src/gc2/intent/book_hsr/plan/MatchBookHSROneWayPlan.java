package gc2.intent.book_hsr.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.book_hsr.module.BookHSRTerm;
import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchBookHSROneWayPlan extends AExecutionPlan {

	public MatchBookHSROneWayPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(BookHSRTerm.姓名)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問您的姓名);
		}else if(!keySet.contains(BookHSRTerm.身分證)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問身分證號碼);
		}else if(!keySet.contains(BookHSRTerm.出發地) && !keySet.contains(BookHSRTerm.目的地)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.要從哪裡出發去哪裡);
		}else if(!keySet.contains(BookHSRTerm.出發地)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.要從哪一站出發);
		}else if(!keySet.contains(BookHSRTerm.目的地)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.目的地是哪一站);
		}else if(!keySet.contains(BookHSRTerm.出發時間) && !keySet.contains(BookHSRTerm.日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.出發時間呢);
		}else if(!keySet.contains(BookHSRTerm.出發時間)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問幾點鐘出發);
		}else if(!keySet.contains(BookHSRTerm.日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.出發日期呢);
		}else if(!keySet.contains(BookHSRTerm.座位票種)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.想搭乘對號座還是自由座);
		}else if(!keySet.contains(BookHSRTerm.年齡票種)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.全票還是優待票);
		}else if(!keySet.contains(BookHSRTerm.乘客數)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.幾位乘客搭乘);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}
}
