package gc2.intent.common.cell;

import gc2.intent.common.ligand.ExtractLigand;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.Feedback;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.FeedbackPlanFactory;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class ExtractBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public ExtractBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		if(ligand!=null){
			return true;
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		return FeedbackPlanFactory.getInstance().generateFeedbackPlan(anima, new Feedback(ligand, new ExtractLigand()));
	}

}
