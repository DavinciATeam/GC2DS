package gc2.intent.reserve_hospital.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.reserve_hospital.module.ReserveHospitalTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchReserveHospitalPlan extends AExecutionPlan {

	public MatchReserveHospitalPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	
	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		Set<String> keySet = extractMap.keySet();
		// Generate Ask Back
		if(!keySet.contains(ReserveHospitalTerm.身分證) && !keySet.contains(ReserveHospitalTerm.病歷號碼)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請提供您病歷號碼或身分證字號);
		}else if(!keySet.contains(ReserveHospitalTerm.科別)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問你要掛什麼科);
		}else if(!keySet.contains(ReserveHospitalTerm.掛號醫師)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.請問您是否有指定醫師);
		}else if(!keySet.contains(ReserveHospitalTerm.日期)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.出發日期呢);
		}else if(keySet.contains(ReserveHospitalTerm.日期已滿)){
			intentMap.put(IntentTerm.AskBack, AskBackTerm.想請問您想掛別日還是換醫師呢);
		}else{
			intentMap.put(IntentTerm.IsAchieveGoal, true);
		}
	}


}
