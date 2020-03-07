package gc2.intent.common.cell;

import gc2.intent.common.ligand.IntentLigand;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.plan.IntentFeedback;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.SCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.FeedbackPlanFactory;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class IntentSCell extends SCell {

	private static final long serialVersionUID = 1L;

	public IntentSCell(Anima anima) {
		super(anima);
	}

	@Override
	public IPlan selectPlan() {
		InnerPool pool = (InnerPool) Receptors.get(IntentTerm.IntentPool);
		this.ligand.getReference().put(IntentTerm.IntentPool, pool);
		return FeedbackPlanFactory.getInstance().generateFeedbackPlan(anima, new IntentFeedback(ligand, new IntentLigand()));
	}

}
