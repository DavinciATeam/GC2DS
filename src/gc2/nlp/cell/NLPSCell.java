package gc2.nlp.cell;

import gc2.nlp.ligand.NLPLigand;
import gc2.nlp.module.NLPTerm;
import gc2.nlp.plan.NLPFeedback;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.SCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.FeedbackPlanFactory;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class NLPSCell extends SCell {

	private static final long serialVersionUID = 1L;

	public NLPSCell(Anima anima) {
		super(anima);
	}

	@Override
	public IPlan selectPlan() {
		InnerPool pool = (InnerPool) this.Receptors.get(NLPTerm.NLPPool);
		this.ligand.getReference().put(NLPTerm.NLPPool, pool);
		return FeedbackPlanFactory.getInstance().generateFeedbackPlan(anima, new NLPFeedback(ligand, new NLPLigand()));
	}

}
