package gc2.intent.query_ir.plan;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.query_ir.module.QueryIRTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;
import java.util.Map;
import java.util.Set;

public class MatchQueryIRPlan extends AExecutionPlan {
    public MatchQueryIRPlan(IAnima anima, ILigand ligand) {
        super(anima, ligand);
    }

    @Override
    public void execute() throws Exception {
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        Set<String> keySet = extractMap.keySet();
        // Generate Ask Back
        if(!keySet.contains(QueryIRTerm.存多久)){
            intentMap.put(IntentTerm.AskBack, QueryIRTerm.請問您要存多久);
        }else if(!keySet.contains(QueryIRTerm.存入多少)){
            intentMap.put(IntentTerm.AskBack, QueryIRTerm.請問您要存入多少錢);
        }else if(!keySet.contains(QueryIRTerm.分行)){
            intentMap.put(IntentTerm.AskBack, QueryIRTerm.需要幫您查詢分行嗎);
        }
    }
}
