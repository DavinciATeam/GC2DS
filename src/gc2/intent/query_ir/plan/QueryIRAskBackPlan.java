package gc2.intent.query_ir.plan;

import gc2.ds.module.DSTerm;
import gc2.intent.common.module.GoalTerm;
import gc2.intent.common.module.IntentTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.AExecutionPlan;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

import java.util.Map;

public class QueryIRAskBackPlan extends AExecutionPlan {
    public QueryIRAskBackPlan(IAnima anima, ILigand ligand) {
        super(anima, ligand);
    }

    @Override
    public void execute() throws Exception {
        String intent = (String) this.ligand.getReference().get(IntentTerm.Intent);
        Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
        Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
        boolean isAchieveGoal = (boolean) intentMap.get(IntentTerm.IsAchieveGoal);
        InnerPool pool = (InnerPool) ligand.getReference().get(IntentTerm.IntentPool);
        IListenAndSay gui = (IListenAndSay) ligand.getReference().get(DSTerm.SimpleGUI);
        // Add Information

        // Achieve Goal
        if(isAchieveGoal){
            String finishAskBack = this.generateFinishAskBack(extractMap);
            String exportForm = this.generateExportForm(extractMap);
            intentMap.put(IntentTerm.AskBack, finishAskBack);
            intentMap.put(IntentTerm.ExportForm, exportForm);
        }else{
            pool.getOutPool().put(intent, intentMap);
        }
    }

    private String generateFinishAskBack(Map<String, String> extractMap) {
        StringBuffer finishAskBack = new StringBuffer();
        finishAskBack.append("\n好的，已完成定存利率查詢。資訊如下：");
        finishAskBack.append("存款銀行"+"："+extractMap.get("存款銀行")+", ");
        finishAskBack.append("存款金額"+"："+extractMap.get("存款金額")+", ");
        finishAskBack.append("定存時間"+"："+extractMap.get("定存時間")+", ");
        finishAskBack.append("利率期別"+"："+extractMap.get("利率期別")+", ");
        finishAskBack.append("利率"+"："+extractMap.get("利率")+", ");
        finishAskBack.append("利息"+"："+extractMap.get("利息")+", ");
        finishAskBack.append("本利和"+"："+extractMap.get("本利和")+", ");
        finishAskBack.append("查詢分行"+"："+extractMap.get("查詢分行")+", ");
        finishAskBack.append("分行地址"+"："+extractMap.get("分行地址")+", ");
        return finishAskBack.toString();
    }

    private String generateExportForm(Map<String, String> extractMap){
        StringBuffer exportForm = new StringBuffer();
        exportForm.append("\n");
        exportForm.append("存款銀行"+"："+extractMap.get("存款銀行")+"\n");
        exportForm.append("存款金額"+"："+extractMap.get("存款金額")+"\n");
        exportForm.append("定存時間"+"："+extractMap.get("定存時間")+"\n");
        exportForm.append("利率期別"+"："+extractMap.get("利率期別")+"\n");
        exportForm.append("利率"+"："+extractMap.get("利率")+"\n");
        exportForm.append("利息"+"："+extractMap.get("利息")+"\n");
        exportForm.append("本利和"+"："+extractMap.get("本利和")+"\n");
        exportForm.append("查詢分行"+"："+extractMap.get("查詢分行")+"\n");
        exportForm.append("分行地址"+"："+extractMap.get("分行地址")+"\n");
        return exportForm.toString();
    }

}
