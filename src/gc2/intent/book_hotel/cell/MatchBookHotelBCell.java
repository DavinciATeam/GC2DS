package gc2.intent.book_hotel.cell;

import gc2.intent.common.module.IntentTerm;
import gc2.intent.book_hotel.plan.MatchBookHotelPlan;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.BCell;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.plan.IPlan;

import java.util.Map;

public class MatchBookHotelBCell extends BCell {

    private static final long serialVersionUID = 1L;

    public MatchBookHotelBCell(Anima anima) {
        super(anima);
    }

    @Override
    public boolean isTrigger() throws Exception {
        ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
        if(ligand!=null){
            String intent = (String) ligand.getReference().get(IntentTerm.Intent);
            if(intent.equals(NLPTerm.IntentBookHotel)){
                return true;
            }
        }
        return false;
    }

    @Override
    public IPlan selectPlan() {
        ILigand ligand = (ILigand) anima.getBDI().getBelief(IntentTerm.ExtractLigand);
        return new MatchBookHotelPlan(anima, ligand);
    }
}
