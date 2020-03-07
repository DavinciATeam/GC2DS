package gc2.intent.order_drink.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.order_drink.module.OrderDrinksTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchOrderDrinksPlan extends AExecutionPlan {

	public MatchOrderDrinksPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(OrderDrinksTerm.品名) ){
			intentMap.put(IntentTerm.AskBack, OrderDrinksTerm.請問需要什麼呢);
		}else if(!keySet.contains(OrderDrinksTerm.地點)){
			intentMap.put(IntentTerm.AskBack, OrderDrinksTerm.請問地址);
		}else if(!keySet.contains(OrderDrinksTerm.時間)){
			intentMap.put(IntentTerm.AskBack, OrderDrinksTerm.請問時間);
		}else if(!keySet.contains(OrderDrinksTerm.電話)){
			intentMap.put(IntentTerm.AskBack, OrderDrinksTerm.請問連絡電話是);
		}else if(!keySet.contains(OrderDrinksTerm.袋子)){
			intentMap.put(IntentTerm.AskBack, OrderDrinksTerm.買袋子嗎);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}
}
