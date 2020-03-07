package gc2.nlp.cell;

import gc2.nlp.module.NLPTerm;
import gc2.nlp.plan.ResponsePlan;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class ResponseMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public ResponseMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(NLPTerm.AALigand);
		if(ligand!=null){
			return true;
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(NLPTerm.AALigand);
		InnerPool pool = (InnerPool) this.Effectors.get(NLPTerm.NLPPool);
		ligand.getReference().put(NLPTerm.NLPPool, pool);
		return new ResponsePlan(anima, ligand);
	}

}
