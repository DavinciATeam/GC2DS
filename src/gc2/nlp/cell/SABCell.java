package gc2.nlp.cell;

import gc2.nlp.ligand.SALigand;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.Feedback;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.FeedbackPlanFactory;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class SABCell extends BCell {

	private static final long serialVersionUID = 1L;

	public SABCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(NLPTerm.WALigand);
		if(ligand!=null){
			return true;
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(NLPTerm.WALigand);
		return FeedbackPlanFactory.getInstance().generateFeedbackPlan(anima, new Feedback(ligand, new SALigand()));
	}

}
