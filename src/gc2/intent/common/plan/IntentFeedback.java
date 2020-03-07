package gc2.intent.common.plan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.module.IntentUtil;
import gc2.nlp.module.NLPTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.AFeedback;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;

public class IntentFeedback extends AFeedback {

	public IntentFeedback(ILigand currentLigand, ILigand nextLigand) {
		super(currentLigand, nextLigand);
	}

	@Override
	public boolean sense() {
		InnerPool pool = (InnerPool) this.current.getReference().get(IntentTerm.IntentPool);
		if(pool.getInPool().size()>0){
			String intent = "";
			for(String key: pool.getInPool().keySet()){
				intent = key;
				break;
			}
			Map<String, Object> intentMap = (Map<String, Object>) pool.getInPool().remove(intent);
			if(IntentUtil.isIntent(intent)){
				this.current.getCollection().put(IntentTerm.Intent, intent);
				this.current.getCollection().put(IntentTerm.IntentMap, intentMap);
				this.current.getCollection().put(IntentTerm.ThisRunExtractMap, new HashMap<String, String>());
				return true;
			}else{
				pool.getOutPool().put(intent, intentMap);
				return false;
			}
		}
		return false;
	}
	


}
