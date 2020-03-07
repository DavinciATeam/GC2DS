package gc2.ds.plan;

import gc2.ds.module.DSTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.AFeedback;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class DSFeedback extends AFeedback {
	
	public static boolean isLock = false;

	public DSFeedback(ILigand currentLigand, ILigand nextLigand) {
		super(currentLigand, nextLigand);
	}

	@Override
	public boolean sense() {
		IListenAndSay gui = (IListenAndSay) this.getLigand().getReference().get(DSTerm.SimpleGUI);
		if(gui.getSentencePool().size()>0 && isLock==false){
			String text = gui.getSentencePool().poll();
			text.replace("兩點", "二點");
			this.getLigand().getCollection().put(DSTerm.Text, text);
			isLock = true;
			return true;
		}
		return false;
	}

}
