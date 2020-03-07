package gc2.intent.common.cell;

import java.util.List;
import java.util.Map;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.plan.ExtractDatePlan;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

public class ExtractDateBCell extends BCell {

	private static final long serialVersionUID = 1L;

	public ExtractDateBCell(Anima anima) {
		super(anima);
	}

	@Override
	public boolean isTrigger() throws Exception {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		if(ligand!=null){
			Map<String, Object> intentMap = (Map<String, Object>) ligand.getReference().get(IntentTerm.IntentMap);
			List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
			for(Sentence sentence: sentences){
				String text = sentence.getText();
				if(text.matches("(.*)([1,2])([0-9])([0-9])([0-9])/([0-1])([0-9])/([0-3])([0-9])(.*)")){
					return true;
				}else if(text.matches("(.*)([1,2])([0-9])([0-9])([0-9])/([1-9])/([0-3])([0-9])(.*)")){
						return true;
				}else if(text.matches("(.*)月(.*)日(.*)")){
					return true;
				}else if(text.matches("(.*)([今天,明天,後天])(.*)")){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public IPlan selectPlan() {
		ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.IntentLigand);
		return new ExtractDatePlan(anima, ligand);
	}
}
