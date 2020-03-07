package gc2.intent.book_hotel.plan;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.book_hotel.module.BookHotelTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;

import java.util.Map;
import java.util.Set;

public class MatchBookHotelPlan extends AExecutionPlan {
    public MatchBookHotelPlan(IAnima anima, ILigand ligand) {
        super(anima, ligand);
    }

    @Override
    public void execute() throws Exception {
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        Set<String> keySet = extractMap.keySet();
        // Generate Ask Back
        if(!keySet.contains(BookHotelTerm.日期)){
            intentMap.put(IntentTerm.AskBack, BookHotelTerm.您想要訂什麼時候);
        }else if(!keySet.contains(BookHotelTerm.人數)){
            intentMap.put(IntentTerm.AskBack, BookHotelTerm.您想訂幾人房);
        }else if(!keySet.contains(BookHotelTerm.姓名)){
            intentMap.put(IntentTerm.AskBack, BookHotelTerm.請問你的姓名);
        }else if(!keySet.contains(BookHotelTerm.電話)){
            intentMap.put(IntentTerm.AskBack, BookHotelTerm.請問你的電話);
        }else if(!keySet.contains(BookHotelTerm.多加床位數)){
            intentMap.put(IntentTerm.AskBack, BookHotelTerm.請問需要加床位);
        }else{
            intentMap.put(IntentTerm.IsAchieveGoal, true);
        }
    }
}
