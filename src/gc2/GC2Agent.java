package gc2;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import gc2.ds.module.DSTerm;
import gc2.intent.book_hsr.module.model.HSRPrice;
import gc2.intent.book_hsr.module.model.HSRSchedule;
import gc2.intent.common.module.IntentPool;
import gc2.intent.common.module.IntentTerm;
import gc2.nlp.module.NLPPool;
import gc2.nlp.module.NLPTerm;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.Anima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.memory.Belief;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;
import tw.edu.ncu.csie.lab.core.ai.cells.util.BDILog;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.Lexicon;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.Notion;
import tw.edu.ncu.csie.lab.core.ai.kt.module.word.POS;

public class GC2Agent {
	
	public GC2Agent(IListenAndSay gui){
		initialData();
		// Create NLP Agent
		Anima nlp = new Anima();
		NLPPool nlpPool = new NLPPool(new LinkedList<String>(), new ConcurrentHashMap<String, Object>(), new ConcurrentHashMap<String, Object>());
		initialNLPAgent(nlp, nlpPool);
		// Create Intent Agent
		Anima intent = new Anima();
		IntentPool intentPool = new IntentPool(new LinkedList<String>(), new ConcurrentHashMap<String, Object>(), new ConcurrentHashMap<String, Object>());
		initialIntentAgent(intent, gui, intentPool);
		// Create DS Agent
		Anima ds = new Anima();
		initialDSAgent(ds, gui, nlpPool, intentPool);
		//
		Belief.getInstance().recall().put(DSTerm.DSAnima, ds);
		Belief.getInstance().recall().put(DSTerm.NLPAnima, nlp);
		Belief.getInstance().recall().put(DSTerm.IntentAnima, intent);
		// BDI Log Setting
		BDILog.setOpenPrintLog(true);
	}
	
	// Load CSV File
	private void initialData(){
		HSRSchedule.getInstance().enhance("resource/csv/1_高鐵訂票/高鐵北上車表.csv", "resource/csv/1_高鐵訂票/高鐵南下車表.csv");
		HSRPrice.getInstance().enhance("resource/csv/1_高鐵訂票/高鐵票價.csv");
	}
	
	private void initialNLPAgent(Anima nlp, InnerPool nlpPool){
		// Initial NLP Resource
		Lexicon.getInstance().load("./resource/nlp/lexicon/lexicon.txt");
		POS.getInstance().merge("./resource/nlp/pos/");
		Notion.getInstance().merge("./resource/nlp/notion/");
		// Add Cell
		nlp.addCell("gc2.nlp.cell.NLPSCell");
		nlp.addCell("gc2.nlp.cell.WABCell");
		nlp.addCell("gc2.nlp.cell.SABCell");
		nlp.addCell("gc2.nlp.cell.AABCell");
		nlp.addCell("gc2.nlp.cell.ResponseMCell");
		// Add Pool
		nlp.getBDI().getReceptors().put(NLPTerm.NLPPool, nlpPool);
		nlp.getBDI().getEffectors().put(NLPTerm.NLPPool, nlpPool);
		Thread nlpAgent = new Thread(nlp);
		nlpAgent.start();
	}
	
	private void initialIntentAgent(Anima intent, IListenAndSay listen_say, InnerPool intentPool){
		// Add Cell (Feedback & Extract)
		intent.addCell("gc2.intent.common.cell.IntentSCell");
		intent.addCell("gc2.intent.common.cell.ExtractBCell");
		intent.addCell("gc2.intent.common.cell.ExtractDateBCell");
		intent.addCell("gc2.intent.common.cell.ExtractNameBCell");
		intent.addCell("gc2.intent.common.cell.ExtractPIDBCell");
		intent.addCell("gc2.intent.common.cell.ExtractPlaceBCell");
		intent.addCell("gc2.intent.common.cell.ExtractReturnTicketBCell");
		intent.addCell("gc2.intent.common.cell.ExtractTicketCountBCell");
		intent.addCell("gc2.intent.common.cell.ExtractSeatTicketTypeBCell");
		intent.addCell("gc2.intent.common.cell.ExtractAgeTicketTypeBCell");
		intent.addCell("gc2.intent.common.cell.ExtractTimeBCell");
		intent.addCell("gc2.intent.common.cell.MatchingBCell");
		// Book HSR
		intent.addCell("gc2.intent.book_hsr.cell.MatchBookHSRBCell");
		intent.addCell("gc2.intent.book_hsr.cell.BookHSRAskBackMCell");
		// Exchange
		intent.addCell("gc2.intent.exchange.cell.MatchExchangeBCell");
		intent.addCell("gc2.intent.exchange.cell.ExchangeAskBackMCell");
		// Query IR
		intent.addCell("gc2.intent.query_ir.cell.MatchQueryIRBCell");
		intent.addCell("gc2.intent.query_ir.cell.QueryIRAskBackMCell");
		// Housing Loans
		intent.addCell("gc2.intent.housing_loans.cell.MatchHousingLoansBCell");
		intent.addCell("gc2.intent.housing_loans.cell.HousingLoansAskBackMCell");
		// Reserve PCP
		intent.addCell("gc2.intent.reserve_pcp.cell.MatchReservePCPBCell");
		intent.addCell("gc2.intent.reserve_pcp.cell.ReservePCPAskBackMCell");
		
		// Add Pool
		intent.getBDI().getReceptors().put(IntentTerm.IntentPool, intentPool);
		intent.getBDI().getEffectors().put(IntentTerm.IntentPool, intentPool);
		intent.getBDI().getEffectors().put(DSTerm.SimpleGUI, listen_say);
		Thread intentAgent = new Thread(intent);
		intentAgent.start();
	}
	
	private void initialDSAgent(Anima ds, IListenAndSay listen_say, InnerPool nlpPool, InnerPool intentPool){
		// Add DS Cell
		ds.addCell("gc2.ds.cell.DSSCell");
		ds.addCell("gc2.ds.cell.DesireBCell");
		ds.addCell("gc2.ds.cell.FindDesireMCell");
		ds.addCell("gc2.ds.cell.IntentionBCell");
		ds.addCell("gc2.ds.cell.IntentProcessMCell");
		ds.addCell("gc2.ds.cell.ResponseMCell");
		// Receptor & Effector
		
		ds.getBDI().getReceptors().put(DSTerm.SimpleGUI, listen_say);
		ds.getBDI().getEffectors().put(DSTerm.SimpleGUI, listen_say);
		ds.getBDI().getReceptors().put(NLPTerm.NLPPool, nlpPool);
		ds.getBDI().getEffectors().put(NLPTerm.NLPPool, nlpPool);
		ds.getBDI().getReceptors().put(IntentTerm.IntentPool, intentPool);
		ds.getBDI().getEffectors().put(IntentTerm.IntentPool, intentPool);
		// Related Setting
		listen_say.setUserTalk(true);
		// Start CellS
//		ds.setInterval(1);//default: 10
		Thread dsAgent = new Thread(ds);
		dsAgent.start();
	}

}






