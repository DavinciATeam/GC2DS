package gc2.nlp.module;

import java.util.List;
import java.util.Map;

import tw.edu.ncu.csie.lab.core.ai.cells.communication.ACellSPool;

public class NLPPool extends ACellSPool {

	public NLPPool(List<String> queue, Map<String, Object> inPool, Map<String, Object> outPool) {
		super(queue, inPool, outPool);
	}

}
