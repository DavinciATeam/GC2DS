package gc2.intent.reserve_pcp.plan;

import java.util.Map;
import java.util.Set;

import gc2.intent.reserve_pcp.module.ReservePCPTerm;
import gc2.intent.book_hsr.module.BookHSRTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

public class MatchReservePCPPlan extends AExecutionPlan {

	public MatchReservePCPPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		intentMap.put(IntentTerm.AskBack, "親子館預約服務您好");
		
		// Generate AskBack
		Set<String> keySet = extractMap.keySet();
		if(!keySet.contains(ReservePCPTerm.會員編號)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q會員編號);
		}else if(!keySet.contains(ReservePCPTerm.會員姓名)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q會員姓名);
		}else if(!keySet.contains(ReservePCPTerm.幼童姓名)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q幼童姓名);
		}else if(!keySet.contains(ReservePCPTerm.大人人數)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q大人人數);
		}else if(!keySet.contains(ReservePCPTerm.幼童人數)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q幼童人數);
		}else if(!keySet.contains(ReservePCPTerm.連絡電話)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q連絡電話);
		}else if(!keySet.contains(ReservePCPTerm.預約時間)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q預約時間);
		}else if(!keySet.contains(ReservePCPTerm.預約時段)){
			intentMap.put(IntentTerm.AskBack, ReservePCPTerm.Q預約時段);
		}
		
	}

}
