package gc2.intent.common.plan;

import java.util.List;
import java.util.Map;

import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public abstract class AAskBackPlan extends AExecutionPlan implements IAskBackPlan {

	public AAskBackPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}
	
	public boolean isExport(List<Sentence> sentences){
		for(Sentence sentence: sentences){
			if(sentence.getText().contains(IntentTerm.輸出表單)){
				return true;
			}
		}
		return false;
	}
	
    public String generateFinishAskBack(String text, Map<String, String> extractMap){
        StringBuffer askBack = new StringBuffer();
        askBack.append(text);
        for(String key: extractMap.keySet()){
            String value = extractMap.get(key);
            askBack.append(key+":"+value+"，");
        }
        askBack.append("謝謝。");
        return askBack.toString();
    }

}
