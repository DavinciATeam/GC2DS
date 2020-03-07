package gc2.ds.plan;

import java.util.ArrayList;
import java.util.List;

import gc2.ds.module.DSTerm;
import gc2.ds.module.DSUtil;
import gc2.nlp.module.NLPTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.memory.Belief;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.Notion;

public class FindDesirePlan extends AExecutionPlan {

	public FindDesirePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception { 
		// Get Info
		String text = (String) this.ligand.getReference().get(DSTerm.Text);
		IListenAndSay gui = (IListenAndSay) this.ligand.getReference().get(DSTerm.SimpleGUI);
		InnerPool pool = (InnerPool) this.ligand.getReference().get(NLPTerm.NLPPool);
		// Find Desire
		List<Sentence> sentences = new ArrayList<Sentence>();
		String intent = this.selectIntent(text, pool, sentences);
		// Ask Desire
		while(intent.equals("")){
			if(Belief.getInstance().recall().containsKey(DSTerm.Intent)){
				intent = (String) Belief.getInstance().recall().get(DSTerm.Intent);
			}else if(text.contains("結束對話")){
				DSUtil.stop();
			}else{
				String response = gui.getAnswer("目前找不到您所需的服務，麻煩您，具體說明所需的服務。");
				intent = this.selectIntent(response, pool, sentences);
				DSFeedback.isLock = false;
			}
		}
		Belief.getInstance().recall().put(DSTerm.Intent, intent);
		this.ligand.getCollection().put(DSTerm.Intent, intent);
		this.ligand.getCollection().put(DSTerm.Sentences, sentences);
	}
	
	private String selectIntent(String text, InnerPool pool, List<Sentence> list){
		String ticket = String.valueOf(System.currentTimeMillis());
		pool.getInPool().put(ticket, text);
		List<Sentence> sentences = (List<Sentence>) pool.getOutputByTicket(ticket);
		list.addAll(sentences);
		boolean isFindDesire = false;
		String intent = "";
		for(Sentence sentence: sentences){
			for(String word: sentence.getWords()){
				String topNotion = Notion.getInstance().getTop(word);
				if(topNotion.equals(NLPTerm.Intent)){
					intent = sentence.getNotion().get(word);
					isFindDesire = true;
					break;
				}
			}
			if(isFindDesire){ break;}
		}
		return intent;
	}
	
	// TODO Voice Similar
	// TODO Condition Similar
	// TODO Intent Similar

}
