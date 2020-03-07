package gc2.intent.common.plan;

import java.util.List;
import java.util.Map;

import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class ExtractNamePlan extends AExecutionPlan {

	public ExtractNamePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		
		String name = sentences.get(0).getText();
		if(!name.equals("")){
			thisRunMap.put(GoalTerm.姓名, name);
		}
	}
}
