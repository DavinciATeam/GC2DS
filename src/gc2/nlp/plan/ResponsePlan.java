package gc2.nlp.plan;

import java.util.List;

import gc2.nlp.module.NLPTerm;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class ResponsePlan extends AExecutionPlan {

	public ResponsePlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		String ticket = (String) this.ligand.getReference().get(NLPTerm.Ticket);
		List<Sentence> sentences = (List<Sentence>) this.ligand.getReference().get(NLPTerm.Sentences);
		InnerPool pool = (InnerPool) this.ligand.getReference().get(NLPTerm.NLPPool);
		pool.getOutPool().put(ticket, sentences);
	}

}
