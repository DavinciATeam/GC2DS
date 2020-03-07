package gc2.ds.cell;

import gc2.ds.module.DSTerm;
import gc2.ds.plan.IntentProcessPlan;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.module.IntentUtil;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class IntentProcessMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public IntentProcessMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(DSTerm.DesireLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(DSTerm.Intent);
			if(IntentUtil.isIntent(intent)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(DSTerm.DesireLigand);
		IListenAndSay gui = (IListenAndSay) Effectors.get(DSTerm.SimpleGUI);
		InnerPool nlpPool = (InnerPool) Effectors.get(NLPTerm.NLPPool);
		InnerPool intentPool = (InnerPool) Effectors.get(IntentTerm.IntentPool);
		ligand.getReference().put(DSTerm.SimpleGUI, gui);
		ligand.getReference().put(NLPTerm.NLPPool, nlpPool);
		ligand.getReference().put(IntentTerm.IntentPool, intentPool);
		return new IntentProcessPlan(anima, ligand);
	}

}
