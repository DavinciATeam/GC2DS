package gc2.intent.common.plan;

import java.util.Map;

import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public interface IAskBackPlan {
	
	public void addInfo(Map<String, String> extractMap, Map<String, String> thisRunMap, IListenAndSay gui);
	
	public String generateExportForm(Map<String, String> extractMap);
	
	
}
