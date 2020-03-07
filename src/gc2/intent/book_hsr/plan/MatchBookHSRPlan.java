package gc2.intent.book_hsr.plan;

import java.util.Map;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchBookHSRPlan extends AExecutionPlan {

	public MatchBookHSRPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		intentMap.put(IntentTerm.AskBack, AskBackTerm.單程票還是來回票呢);
	}

}
