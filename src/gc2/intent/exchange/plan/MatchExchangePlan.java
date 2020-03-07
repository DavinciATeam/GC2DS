package gc2.intent.exchange.plan;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.exchange.module.ExchangeTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

import java.util.Map;
import java.util.Set;

public class MatchExchangePlan extends AExecutionPlan {

    public MatchExchangePlan(IAnima anima, ILigand ligand) { super(anima, ligand); }

    @Override
    public void execute() throws Exception {
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        Set<String> keySet = extractMap.keySet();
        // Generate Ask Back
        if(!keySet.contains(ExchangeTerm.幣別)){
            intentMap.put(IntentTerm.AskBack, ExchangeTerm.請問您要換哪種貨幣);
        }else if(!keySet.contains(ExchangeTerm.金額)){
            intentMap.put(IntentTerm.AskBack, ExchangeTerm.請問您想換多少);
        }else if(!keySet.contains(ExchangeTerm.即期)&&!keySet.contains(ExchangeTerm.現鈔)){
            intentMap.put(IntentTerm.AskBack, ExchangeTerm.請問您要換即期還是現鈔);
        }else if(!keySet.contains(ExchangeTerm.姓名)){
            intentMap.put(IntentTerm.AskBack, ExchangeTerm.請問您的姓名);
        }
    }
}
