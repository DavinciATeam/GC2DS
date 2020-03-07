package gc2.intent.recommend_restaurant.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.recommend_restaurant.module.RecommendRestaurantTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchRecommendRestaurantPlan extends AExecutionPlan {

	public MatchRecommendRestaurantPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		
		if(!keySet.contains(RecommendRestaurantTerm.價位)){
			intentMap.put(IntentTerm.AskBack, "請問有預算限制嗎");
		}else if(!keySet.contains(RecommendRestaurantTerm.餐廳地址)){
			intentMap.put(IntentTerm.AskBack, "請問餐廳地點");
		}else if(!keySet.contains(RecommendRestaurantTerm.麻辣鍋)){
			intentMap.put(IntentTerm.AskBack, "想吃麻辣鍋嗎");	
		}else if(!keySet.contains(RecommendRestaurantTerm.西餐廳)){
			intentMap.put(IntentTerm.AskBack, "想吃西餐廳嗎");
		}else if(!keySet.contains(RecommendRestaurantTerm.燒肉店)){
			intentMap.put(IntentTerm.AskBack, "想吃燒肉店嗎");						
		}else if(!keySet.contains(RecommendRestaurantTerm.價格範圍)){
			intentMap.put(IntentTerm.AskBack, "請問價格範圍");
		}else if(!keySet.contains(RecommendRestaurantTerm.訂位)){
			intentMap.put(IntentTerm.AskBack, "請問要訂位");	
		}else if(!keySet.contains(RecommendRestaurantTerm.限時)){
			intentMap.put(IntentTerm.AskBack, "請問要限時嗎");	
		}else if(!keySet.contains(RecommendRestaurantTerm.親子友善)){
			intentMap.put(IntentTerm.AskBack, "請問需要親子友善嗎");					
		}else if(!keySet.contains(RecommendRestaurantTerm.寵物友善)){
			intentMap.put(IntentTerm.AskBack, "請問需要寵物友善嗎");
		}else if(!keySet.contains(RecommendRestaurantTerm.支付方式)){
			intentMap.put(IntentTerm.AskBack, "請問支付方式");	
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}

}
