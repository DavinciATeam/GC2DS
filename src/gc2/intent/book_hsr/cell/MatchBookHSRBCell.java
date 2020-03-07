package gc2.intent.book_hsr.cell;

import java.util.Map;

import gc2.intent.book_hsr.plan.MatchBookHSROneWayPlan;
import gc2.intent.book_hsr.plan.MatchBookHSRPlan;
import gc2.intent.book_hsr.plan.MatchBookHSRGBPlan;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class MatchBookHSRBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public MatchBookHSRBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
		if(ligand!=null){
			String intent = (String) ligand.getReference().get(IntentTerm.Intent);
			if(intent.equals(NLPTerm.IntentBookHSR)){
				return true;
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		extractMap.putAll(thisRunMap);
		if(!extractMap.containsKey(GoalTerm.去回程)){
			return new MatchBookHSRPlan(anima, ligand);
		}else if(extractMap.get(GoalTerm.去回程).equals("來回票")){
			return new MatchBookHSRGBPlan(anima, ligand);
		}else{
			return new MatchBookHSROneWayPlan(anima, ligand);
		}
	}
}
