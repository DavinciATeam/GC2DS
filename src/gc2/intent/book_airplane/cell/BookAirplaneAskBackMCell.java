package gc2.intent.book_airplane.cell;

import gc2.ds.module.DSTerm;
import gc2.intent.book_airplane.plan.BookAirplaneAskBackPlan;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.MCell;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class BookAirplaneAskBackMCell extends MCell {

	private static final long serialVersionUID = 1L;

	public BookAirplaneAskBackMCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.MatchingLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(IntentTerm.Intent);
			if(intent.equals(NLPTerm.IntentBookAirplane)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.MatchingLigand);
		InnerPool pool = (InnerPool) Effectors.get(IntentTerm.IntentPool);
		IListenAndSay gui = (IListenAndSay) Effectors.get(DSTerm.SimpleGUI);
		ligand.getReference().put(IntentTerm.IntentPool, pool);
		ligand.getReference().put(DSTerm.SimpleGUI, gui);
		return new BookAirplaneAskBackPlan(anima, ligand);
	}
}
