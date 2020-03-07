package gc2.intent.common.module;

import java.util.List;
import java.util.Map;

import tw.edu.ncu.csie.lab.core.ai.cells.communication.ACellSPool;

public class IntentPool extends ACellSPool {

	public IntentPool(List<String> queue, Map<String, Object> inPool, Map<String, Object> outPool) {
		super(queue, inPool, outPool);
	}
}
