package gc2.ds.cell;

import gc2.ds.ligand.DSLigand;
import gc2.ds.module.DSTerm;
import gc2.ds.plan.DSFeedback;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.SCell;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.FeedbackPlanFactory;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class DSSCell extends SCell {

	private static final long serialVersionUID = 1L;

	public DSSCell(Anima anima) {
		super(anima);
	}

	@Override
	public IPlan selectPlan() {
		IListenAndSay gui = (IListenAndSay) this.Receptors.get(DSTerm.SimpleGUI);
		this.ligand.getReference().put(DSTerm.SimpleGUI, gui);
		return FeedbackPlanFactory.getInstance().generateFeedbackPlan(this.anima, new DSFeedback(ligand, new DSLigand()));
	}

}
