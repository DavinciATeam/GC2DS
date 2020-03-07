package gc2.ds.cell;

import gc2.ds.module.DSTerm;
import gc2.ds.plan.FindDesirePlan;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class FindDesireMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public FindDesireMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(DSTerm.DSLigand);
		if(ligand!=null){
			return true;
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(DSTerm.DSLigand);
		InnerPool pool = (InnerPool) Effectors.get(NLPTerm.NLPPool);
		IListenAndSay gui = (IListenAndSay) Effectors.get(DSTerm.SimpleGUI);
		ligand.getReference().put(NLPTerm.NLPPool, pool);
		ligand.getReference().put(DSTerm.SimpleGUI, gui);
		return new FindDesirePlan(anima, ligand);
	}

}
