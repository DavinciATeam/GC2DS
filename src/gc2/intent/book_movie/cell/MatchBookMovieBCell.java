package gc2.intent.book_movie.cell;

import java.util.Map;

import gc2.intent.book_hsr.plan.MatchBookHSROneWayPlan;
import gc2.intent.book_hsr.plan.MatchBookHSRPlan;
import gc2.intent.book_movie.plan.MatchBookMoviePlan;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.recommend_restaurant.plan.MatchRecommendRestaurantPlan;
import gc2.intent.recommend_ta.plan.MatchRecommendTAPlan;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class MatchBookMovieBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public MatchBookMovieBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(IntentTerm.Intent);
			if(intent.equals(NLPTerm.IntentBookMovie)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);

		return new MatchBookMoviePlan(anima, ligand);
	}
}
