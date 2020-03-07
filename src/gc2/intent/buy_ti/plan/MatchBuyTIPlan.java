package gc2.intent.buy_ti.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.buy_ti.module.BuyTITerm;
import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchBuyTIPlan extends AExecutionPlan {

	public MatchBuyTIPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(BuyTITerm.姓名)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問您的姓名);
		}else if(!keySet.contains(BuyTITerm.去程日期) && !keySet.contains(BuyTITerm.回程日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問您是幾號出國幾號回來);
		}else if(!keySet.contains(BuyTITerm.去程日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問去程的日期);
		}else if(!keySet.contains(BuyTITerm.回程日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問回程的日期);
		}else if(!keySet.contains(BuyTITerm.身分證)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問身分證號碼);
		}else if(!keySet.contains(BuyTITerm.出生日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.好的再麻煩您提供出生年月日);
		}else if(!keySet.contains(BuyTITerm.手機號碼)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問手機號碼);
		}else if(!keySet.contains(BuyTITerm.EMail)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.再幫我填一下電子信箱);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}
}
