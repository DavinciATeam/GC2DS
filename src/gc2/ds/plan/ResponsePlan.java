package gc2.ds.plan;

import java.util.Map;

import gc2.ds.module.DSTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class ResponsePlan extends AExecutionPlan {

	public ResponsePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		IListenAndSay gui = (IListenAndSay) this.ligand.getReference().get(DSTerm.SimpleGUI);
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(DSTerm.IntentMap);
		String exportForm = (String) intentMap.get(IntentTerm.ExportForm);
		gui.updateAgentMessage(exportForm);
		gui.updateAgentMessage("請問您需要什麼服務?");
	}
}
