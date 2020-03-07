package gc2.intent.order_in.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.order_in.module.OrderInTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchOrderInPlan extends AExecutionPlan {

	public MatchOrderInPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(OrderInTerm.品名) ){
			intentMap.put(IntentTerm.AskBack, OrderInTerm.請問需要什麼呢);
		}else if(!keySet.contains(OrderInTerm.地點)){
			intentMap.put(IntentTerm.AskBack, OrderInTerm.請問地址);
		}else if(!keySet.contains(OrderInTerm.連絡人)){
			intentMap.put(IntentTerm.AskBack, OrderInTerm.請問怎麼稱呼呢);
		}else if(!keySet.contains(OrderInTerm.時間)){
			intentMap.put(IntentTerm.AskBack, OrderInTerm.請問時間);
		}else if(!keySet.contains(OrderInTerm.電話)){
			intentMap.put(IntentTerm.AskBack, OrderInTerm.請問連絡電話是);
		}else if(!keySet.contains(OrderInTerm.其他需求)){
			intentMap.put(IntentTerm.AskBack, OrderInTerm.請問有其他需求嗎);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}
}
