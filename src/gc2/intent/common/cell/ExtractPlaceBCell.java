package gc2.intent.common.cell;

import java.util.List;
import java.util.Map;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.plan.ExtractPlacePlan;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class ExtractPlaceBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public ExtractPlaceBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		if(ligand!=null){
			Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
			List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
			for(Sentence sentence: sentences){
				for(String word: sentence.getWords()){
					String notion = sentence.getNotion().get(word);
					if(notion.equals("@地點")){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		return new ExtractPlacePlan(anima, ligand);
	}
}
