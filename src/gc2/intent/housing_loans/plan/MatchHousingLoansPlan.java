package gc2.intent.housing_loans.plan;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.housing_loans.module.HousingLoansTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

import java.util.Map;
import java.util.Set;

public class MatchHousingLoansPlan extends AExecutionPlan {
    public MatchHousingLoansPlan(IAnima anima, ILigand ligand) {
        super(anima, ligand);
    }

    @Override
    public void execute() throws Exception {
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        Set<String> keySet = extractMap.keySet();
        // Generate Ask Back
        if(!keySet.contains(HousingLoansTerm.姓名)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.請問您的姓名);
        }else if(!keySet.contains(HousingLoansTerm.職業)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.請問你的職業是);
        }else if(!keySet.contains(HousingLoansTerm.年齡)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.請問你的年齡是);
        }else if(!keySet.contains(HousingLoansTerm.月收入)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.請問您月收入);
        }else if(!keySet.contains(HousingLoansTerm.房屋型態)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.您的房屋型態是);
        }else if(!keySet.contains(HousingLoansTerm.電梯)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.有無電梯呢);
        }else if(!keySet.contains(HousingLoansTerm.地址)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.請問您的房屋所在地址是);
        }else if(!keySet.contains(HousingLoansTerm.坪數)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.房屋坪數為幾坪);
        }else if(!keySet.contains(HousingLoansTerm.房齡)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.房齡多少年);
        }else if(!keySet.contains(HousingLoansTerm.幾樓)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.所在房屋為第幾樓);
        }else if(!keySet.contains(HousingLoansTerm.總價)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.房屋總價為多少);
        }else if(!keySet.contains(HousingLoansTerm.首付款)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.請問自備多少首付款);
        }else if(!keySet.contains(HousingLoansTerm.預期貸款)){
            intentMap.put(IntentTerm.AskBack, HousingLoansTerm.預期貸款多少金額);
        }else{
            intentMap.put(IntentTerm.IsAchieveGoal, true);
        }
    }
}
