package gc2.intent.common.cell;

import java.util.Map;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.DialogHistory;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.plan.ExtractNamePlan;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class ExtractNameBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public ExtractNameBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		if(ligand!=null){
			Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
			DialogHistory dialogHistory = (DialogHistory) intentMap.get(IntentTerm.DialogHistory);
			String agentSay = dialogHistory.getLastAgentSay();
			if(agentSay!=null && agentSay.equals(AskBackTerm.請問您的姓名)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		return new ExtractNamePlan(anima, ligand);
	}
}
