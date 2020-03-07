package gc2.intent.recommend_restaurant.cell;

import gc2.ds.module.DSTerm;
import gc2.intent.book_hsr.plan.BookHSRAskBackPlan;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.recommend_restaurant.plan.RecommendRestaurantAskBackPlan;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class RecommendRestaurantAskBackMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public RecommendRestaurantAskBackMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.MatchingLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(IntentTerm.Intent);
			if(intent.equals(NLPTerm.IntentRecommendRestaurant)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.MatchingLigand);
		InnerPool pool = (InnerPool) Effectors.get(IntentTerm.IntentPool);
		IListenAndSay gui = (IListenAndSay) Effectors.get(DSTerm.SimpleGUI);
		ligand.getReference().put(IntentTerm.IntentPool, pool);
		ligand.getReference().put(DSTerm.SimpleGUI, gui);
		return new RecommendRestaurantAskBackPlan(anima, ligand);
	}
}
