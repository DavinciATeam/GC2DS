package gc2.intent.reserve_pcp.cell;

import gc2.intent.reserve_pcp.plan.ReservePCPAskBackPlan;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class ReservePCPAskBackMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public ReservePCPAskBackMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.MatchingLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(IntentTerm.Intent);
			if(intent.equals(NLPTerm.IntentReservePCP)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.MatchingLigand);
		InnerPool pool = (InnerPool) Effectors.get(IntentTerm.IntentPool);
		ligand.getReference().put(IntentTerm.IntentPool, pool);
		return new ReservePCPAskBackPlan(anima, ligand);
	}
}
