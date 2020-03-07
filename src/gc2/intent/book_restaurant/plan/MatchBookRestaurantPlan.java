package gc2.intent.book_restaurant.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.book_hsr.module.BookHSRTerm;
import gc2.intent.book_restaurant.module.BookRestaurantTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchBookRestaurantPlan extends AExecutionPlan {

	public MatchBookRestaurantPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(BookRestaurantTerm.日期) && !keySet.contains(BookRestaurantTerm.時間)){
			intentMap.put(IntentTerm.AskBack, BookRestaurantTerm.請問您要訂什麼時候);
		}else if(!keySet.contains(BookRestaurantTerm.日期)){
			intentMap.put(IntentTerm.AskBack, BookRestaurantTerm.請問你訂的日期是什麼時候);
		}else if(!keySet.contains(BookRestaurantTerm.時間)){
			intentMap.put(IntentTerm.AskBack, BookRestaurantTerm.請問你訂的幾點鐘);
		}else if(!keySet.contains(BookRestaurantTerm.人數)){
			intentMap.put(IntentTerm.AskBack, BookRestaurantTerm.請問共多少人);
		}else if(!keySet.contains(BookRestaurantTerm.人名)){
			intentMap.put(IntentTerm.AskBack, BookRestaurantTerm.請問您的大名);
		}else if(!keySet.contains(BookRestaurantTerm.電話)){
			intentMap.put(IntentTerm.AskBack, BookRestaurantTerm.請問連絡電話是);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}
}
