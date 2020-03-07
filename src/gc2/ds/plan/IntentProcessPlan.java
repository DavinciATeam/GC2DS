package gc2.ds.plan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gc2.ds.module.DSTerm;
import gc2.ds.module.DSUtil;
import gc2.intent.common.module.DialogHistory;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class IntentProcessPlan extends AExecutionPlan {

	public IntentProcessPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		// Get Info
		String intent = (String) this.ligand.getReference().get(DSTerm.Intent);
		List<Sentence> sentences = (List<Sentence>) this.ligand.getReference().get(DSTerm.Sentences);
		IListenAndSay gui = (IListenAndSay) this.ligand.getReference().get(DSTerm.SimpleGUI);
		InnerPool nlpPool = (InnerPool) this.ligand.getReference().get(NLPTerm.NLPPool);
		InnerPool intentPool = (InnerPool) this.ligand.getReference().get(IntentTerm.IntentPool);
		// Build Intent Map
		Map<String, Object> intentMap = new HashMap<String, Object>();
		DialogHistory dialogHistory = new DialogHistory();
		Map<String, String> extractMap = new HashMap<String, String>();
		boolean isAchieveGoal = false;
		intentMap.put(IntentTerm.IsAchieveGoal, isAchieveGoal);
		intentMap.put(IntentTerm.Sentences, sentences);
		intentMap.put(IntentTerm.ExtractMap, extractMap);
		intentMap.put(IntentTerm.AskBack, "");
		intentMap.put(IntentTerm.ExportForm, "");
		intentMap.put(IntentTerm.DialogHistory, dialogHistory);
		// Try to Achieve Goal
		intentPool.getInPool().put(intent, intentMap);
		Map<String, Object> tempMap = (Map<String, Object>) intentPool.getOutputByTicket(intent);
		intentMap.putAll(tempMap);
		// Intent Process (Update Sentences)
		boolean isDone = this.isDone(sentences);
		while(!isDone){
			isAchieveGoal = (boolean) intentMap.get(IntentTerm.IsAchieveGoal);
			String askBack = (String) intentMap.get(IntentTerm.AskBack);
			if(isAchieveGoal){ askBack = askBack+DSTerm.若資訊有誤請修正資訊否則請輸出表單;}
			String response = gui.getAnswer(askBack);
			String ticket = String.valueOf(System.currentTimeMillis());
			nlpPool.getInPool().put(ticket, response);
			List<Sentence> newSentences = (List<Sentence>) nlpPool.getOutputByTicket(ticket);
			intentMap.put(IntentTerm.Sentences, newSentences);
			intentMap.put(IntentTerm.AskBack, "");
			dialogHistory.addDialog(askBack, newSentences);
			intentPool.getInPool().put(intent, intentMap);
			tempMap = (Map<String, Object>) intentPool.getOutputByTicket(intent);
			intentMap.putAll(tempMap);
			isDone = this.isDone(newSentences);
		}
		this.ligand.getCollection().put(DSTerm.IntentMap, intentMap);
	}
	
	private boolean isDone(List<Sentence> sentences){
		for(Sentence sentence: sentences){
			if(sentence.getText().contains(DSTerm.輸出表單)){
				return true;
			}else if(sentence.getText().contains(DSTerm.結束對話)){
				DSUtil.stop();
				return true;
			}
		}
		return false;
	}
}
