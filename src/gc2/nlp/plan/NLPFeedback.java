package gc2.nlp.plan;

import java.awt.im.spi.InputMethod;
import java.util.ArrayList;
import java.util.List;

import gc2.nlp.module.NLPTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.AFeedback;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;

public class NLPFeedback extends AFeedback {

	public NLPFeedback(ILigand currentLigand, ILigand nextLigand) {
		super(currentLigand, nextLigand);
	}

	@Override
	public boolean sense() {
		InnerPool pool = (InnerPool) this.current.getReference().get(NLPTerm.NLPPool);
		if(pool.getInPool().size()>0){
			String ticket = "";
			for(String key: pool.getInPool().keySet()){
				ticket = key;
				break;
			}
			String text = (String) pool.getInPool().remove(ticket);
			List<Sentence> sentences = this.getSentences(text);
			if(sentences.size()>0){
				this.current.getCollection().put(NLPTerm.Ticket, ticket);
				this.current.getCollection().put(NLPTerm.Sentences, sentences);
				return true;
			}else{
				pool.getOutPool().put(ticket, new ArrayList<Sentence>());
			}
		}
		return false;
	}
	
	/**
	 * 前處理:(斷句 + 斷詞 + POS + Notion)
	 * @param text
	 * @return
	 */
	private List<Sentence> getSentences(String text){
		List<Sentence> sentences = new ArrayList<Sentence>();
		String[] textArray = text.split("。|\\?|!");
		for(String item: textArray){
			if(!item.trim().equals("")){
				Sentence sentence = new Sentence(item.trim());
				sentences.add(sentence);
			}
		}
		return sentences;
	}

}
