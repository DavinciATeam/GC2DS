package gc2.ds.cell;

import gc2.ds.module.DSTerm;
import gc2.ds.plan.ResponsePlan;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class ResponseMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public ResponseMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(DSTerm.IntentionLigand);
		if(ligand!=null){
			return true;
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) this.anima.getBDI().getBelief(DSTerm.IntentionLigand);
		IListenAndSay gui = (IListenAndSay) this.Effectors.get(DSTerm.SimpleGUI);
		ligand.getReference().put(DSTerm.SimpleGUI, gui);
		return new ResponsePlan(anima, ligand);
	}

}
