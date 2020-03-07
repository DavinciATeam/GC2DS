package gc2.intent.rent_car.plan;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.rent_car.module.RentCarTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

import java.util.Map;
import java.util.Set;

public class MatchRentCarPlan extends AExecutionPlan {
    public MatchRentCarPlan(IAnima anima, ILigand ligand) {
        super(anima, ligand);
    }

    @Override
    public void execute() throws Exception {
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        Set<String> keySet = extractMap.keySet();
        // Generate Ask Back
        if(!keySet.contains(RentCarTerm.取車時間)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問幾點取車);
        }else if(!keySet.contains(RentCarTerm.姓名)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問你的姓名);
        }else if(!keySet.contains(RentCarTerm.分店)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.要在哪一間分店取車);
        }else if(!keySet.contains(RentCarTerm.車款)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問您要哪一個車款);
        }else if(!keySet.contains(RentCarTerm.還車時間)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問還車的時間);
        }else if(!keySet.contains(RentCarTerm.還車地點)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問還車的地點);
        }else if(!keySet.contains(RentCarTerm.取車時間)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問取車的時間);
        }else if(!keySet.contains(RentCarTerm.取車地點)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問取車的地點);
        }else if(!keySet.contains(RentCarTerm.電話)){
            intentMap.put(IntentTerm.AskBack, RentCarTerm.請問你的電話是);
        }else{
            intentMap.put(IntentTerm.IsAchieveGoal, true);
        }
    }
}
