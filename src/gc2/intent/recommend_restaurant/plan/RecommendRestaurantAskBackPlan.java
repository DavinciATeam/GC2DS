package gc2.intent.recommend_restaurant.plan;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gc2.ds.module.DSTerm;
import gc2.intent.book_hsr.module.model.HSRPrice;
import gc2.intent.book_hsr.module.model.HSRSchedule;
import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class RecommendRestaurantAskBackPlan extends AExecutionPlan {

	public RecommendRestaurantAskBackPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		// Get Information
		String intent = (String) this.ligand.getReference().get(IntentTerm.Intent);
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		boolean isAchieveGoal = (boolean) intentMap.get(IntentTerm.IsAchieveGoal);
		InnerPool pool = (InnerPool) ligand.getReference().get(IntentTerm.IntentPool);
		IListenAndSay gui = (IListenAndSay) ligand.getReference().get(DSTerm.SimpleGUI);
		// Add Information

		// Achieve Goal
		if(isAchieveGoal){
			String finishAskBack = this.generateFinishAskBack(extractMap);
			String exportForm = this.generateExportForm(extractMap);
			intentMap.put(IntentTerm.AskBack, finishAskBack);
			intentMap.put(IntentTerm.ExportForm, exportForm);
		}
		pool.getOutPool().put(intent, intentMap);
		
	}
	
    private String generateFinishAskBack(Map<String, String> extractMap){
        StringBuffer askBack = new StringBuffer();
        for(String key: extractMap.keySet()){
            String value = extractMap.get(key);
            askBack.append(key+":"+value+"\n");
        }
        return askBack.toString();
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
