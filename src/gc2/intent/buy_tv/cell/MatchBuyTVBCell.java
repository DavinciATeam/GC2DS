package gc2.intent.buy_tv.cell;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.buy_tv.plan.MatchBuyTVPlan;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

import java.util.Map;

public class MatchBuyTVBCell extends BCell {

    private static final long serialVersionUID = 1L;

    public MatchBuyTVBCell(Anima anima) {
        super(anima);
    }

    @Override
    public boolean isTrigger() throws Exception {
        ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
        if(ligand!=null){
            String intent = (String) ligand.getReference().get(IntentTerm.Intent);
            if(intent.equals(NLPTerm.IntentBuyTV)){
                return true;
            }
        }
        return false;
    }

    @Override
    public IPlan selectPlan() {
        ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
        return new MatchBuyTVPlan(anima, ligand);
    }
}
