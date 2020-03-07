package gc2.intent.order_drink.cell;

import gc2.intent.order_drink.plan.MatchOrderDrinksPlan;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class MatchOrderDrinksBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public MatchOrderDrinksBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(IntentTerm.Intent);
			if(intent.equals(NLPTerm.IntentOrderDrinks)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
		return new MatchOrderDrinksPlan(anima, ligand);
		
		
	}
}
