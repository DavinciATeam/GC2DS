package gc2.intent.buy_tv.plan;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.buy_tv.module.BuyTVTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

import java.util.Map;
import java.util.Set;

public class MatchBuyTVPlan extends AExecutionPlan {
    public MatchBuyTVPlan(IAnima anima, ILigand ligand) {
        super(anima, ligand);
    }

    @Override
    public void execute() throws Exception {
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        Set<String> keySet = extractMap.keySet();
        // Generate Ask Back
        if(!keySet.contains(BuyTVTerm.尺寸)){
            intentMap.put(IntentTerm.AskBack, BuyTVTerm.請問你需要幾吋);
        }else if(!keySet.contains(BuyTVTerm.姓名)){
            intentMap.put(IntentTerm.AskBack, BuyTVTerm.請問你的姓名);
        }else if(!keySet.contains(BuyTVTerm.電話)){
            intentMap.put(IntentTerm.AskBack, BuyTVTerm.請問你的電話);
        }else{
            intentMap.put(IntentTerm.IsAchieveGoal, true);
        }
    }
}
