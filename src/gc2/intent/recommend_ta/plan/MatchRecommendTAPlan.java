package gc2.intent.recommend_ta.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.recommend_restaurant.module.RecommendRestaurantTerm;
import gc2.intent.recommend_ta.module.RecommendTATerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchRecommendTAPlan extends AExecutionPlan {

	public MatchRecommendTAPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		
		if(!keySet.contains(RecommendTATerm.推薦景點)){
			intentMap.put(IntentTerm.AskBack, "請問推薦景點");
		}else if(!keySet.contains(RecommendTATerm.出遊時間)){
			intentMap.put(IntentTerm.AskBack, "請問出遊時間");
		}else if(!keySet.contains(RecommendTATerm.天氣)){
			intentMap.put(IntentTerm.AskBack, "請問天氣如何");
		}else if(!keySet.contains(RecommendTATerm.縣市)){
			intentMap.put(IntentTerm.AskBack, "請問縣市");
		}else if(!keySet.contains(RecommendTATerm.地址)){
			intentMap.put(IntentTerm.AskBack, "請問地址");
		}else if(!keySet.contains(RecommendTATerm.電話)){
			intentMap.put(IntentTerm.AskBack, "請問電話");
		}else if(!keySet.contains(RecommendTATerm.溫泉好湯)){
			intentMap.put(IntentTerm.AskBack, "請問溫泉好湯");
		}else if(!keySet.contains(RecommendTATerm.寺廟祈福)){
			intentMap.put(IntentTerm.AskBack, "請問寺廟祈福");
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}

}
