package gc2.intent.order_in.plan;

import java.util.Map;

import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class OrderInAskBackPlan extends AExecutionPlan {

	public OrderInAskBackPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		String intent = (String) this.ligand.getReference().get(IntentTerm.Intent);
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		boolean isAchieveGoal = (boolean) intentMap.get(IntentTerm.IsAchieveGoal);
		InnerPool pool = (InnerPool) ligand.getReference().get(IntentTerm.IntentPool);
		if(isAchieveGoal){			
			String exportForm = this.generateExportForm(extractMap);
			intentMap.put(IntentTerm.ExportForm, exportForm);
		}else{
			pool.getOutPool().put(intent, intentMap);
		}
	}
	
	private String generateExportForm(Map<String, String> extractMap){
		StringBuffer exportForm = new StringBuffer();
		for(String key: extractMap.keySet()){
			String value = extractMap.get(key);
			exportForm.append(key+":"+value+"\n");
		}
		return exportForm.toString();
	}
	
}
