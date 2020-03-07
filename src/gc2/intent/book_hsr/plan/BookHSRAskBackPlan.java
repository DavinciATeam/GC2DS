package gc2.intent.book_hsr.plan;

import java.util.List;
import java.util.Map;
import java.util.Set;

import gc2.ds.module.DSTerm;
import gc2.intent.book_hsr.module.BookHSRTerm;
import gc2.intent.book_hsr.module.model.HSROneWaySchedule;
import gc2.intent.book_hsr.module.model.HSRPrice;
import gc2.intent.book_hsr.module.model.HSRSchedule;
import gc2.intent.common.module.AskBackTerm;
import gc2.intent.common.module.IntentTerm;
import gc2.intent.common.plan.AAskBackPlan;
import gc2.nlp.module.Sentence;
import tw.edu.ncu.csie.lab.core.ai.cells.bdi.IAnima;
import tw.edu.ncu.csie.lab.core.ai.cells.communication.InnerPool;
import tw.edu.ncu.csie.lab.core.ai.cells.ligand.ILigand;
import tw.edu.ncu.csie.lab.core.ai.cells.re.IListenAndSay;

public class BookHSRAskBackPlan extends AAskBackPlan {

	public BookHSRAskBackPlan(IAnima anima, ILigand ligand) {
		super(anima, ligand);
	}

	@Override
	public void execute() throws Exception {
		// Get Information
		Map<String, String> thisRunMap = (Map<String, String>) ligand.getReference().get(IntentTerm.ThisRunExtractMap);
		String intent = (String) this.ligand.getReference().get(IntentTerm.Intent);
		Map<String, Object> intentMap = (Map<String, Object>) this.ligand.getReference().get(IntentTerm.IntentMap);
		Map<String, String> extractMap = (Map<String, String>) intentMap.get(IntentTerm.ExtractMap);
		List<Sentence> sentences = (List<Sentence>) intentMap.get(IntentTerm.Sentences);
		boolean isAchieveGoal = (boolean) intentMap.get(IntentTerm.IsAchieveGoal);
		InnerPool pool = (InnerPool) ligand.getReference().get(IntentTerm.IntentPool);
		IListenAndSay gui = (IListenAndSay) ligand.getReference().get(DSTerm.SimpleGUI);
		// Add Information
		if(extractMap.containsKey(BookHSRTerm.去回程)){
			this.addInfo(extractMap, thisRunMap, gui);
		}
		// Achieve Goal
		if(isAchieveGoal){
			String finishAskBack = this.generateFinishAskBack("好的，已完成訂購。您的乘車資訊如下：", extractMap);
			intentMap.put(IntentTerm.AskBack, finishAskBack);
		}
		// Export Form
		if(this.isExport(sentences)){
			String exportForm = this.generateExportForm(extractMap);
			intentMap.put(IntentTerm.ExportForm, exportForm);
		}
		pool.getOutPool().put(intent, intentMap);
	}
	
	@Override
	public void addInfo(Map<String, String> extractMap, Map<String, String> thisRunMap, IListenAndSay gui){
		if(extractMap.get(BookHSRTerm.去回程).equals("來回票")){ 
			this.addGBInfo(extractMap, thisRunMap, gui);
		}else if(extractMap.get(BookHSRTerm.去回程).equals("單程票")){ 
			this.addOneWayInfo(extractMap, thisRunMap, gui);
		}
	}
	
    @Override
	public String generateExportForm(Map<String, String> extractMap){
		StringBuffer exportForm = new StringBuffer();
		exportForm.append("\n");
		if(extractMap.containsKey(BookHSRTerm.姓名)){exportForm.append(BookHSRTerm.姓名+"："+extractMap.get(BookHSRTerm.姓名)+"\n");}
		if(extractMap.containsKey(BookHSRTerm.身分證)){exportForm.append(BookHSRTerm.身分證+"："+extractMap.get(BookHSRTerm.身分證)+"\n");}
		if(extractMap.containsKey(BookHSRTerm.出發地)){exportForm.append(BookHSRTerm.出發地+"："+extractMap.get(BookHSRTerm.出發地)+"\n");}
		if(extractMap.containsKey(BookHSRTerm.目的地)){exportForm.append(BookHSRTerm.目的地+"："+extractMap.get(BookHSRTerm.目的地)+"\n");}
		if(extractMap.containsKey(BookHSRTerm.去回程)){exportForm.append(BookHSRTerm.去回程+"："+extractMap.get(BookHSRTerm.去回程)+"\n");}
		if(extractMap.get(BookHSRTerm.去回程).equals("來回票")){
			if(extractMap.containsKey(BookHSRTerm.去程日期)){exportForm.append(BookHSRTerm.去程日期+"："+extractMap.get(BookHSRTerm.去程日期)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.去程車次)){exportForm.append(BookHSRTerm.去程車次+"："+extractMap.get(BookHSRTerm.去程車次)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.去程出發時間)){exportForm.append(BookHSRTerm.去程出發時間+"："+extractMap.get(BookHSRTerm.去程出發時間)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.去程抵達時間)){exportForm.append(BookHSRTerm.去程抵達時間+"："+extractMap.get(BookHSRTerm.去程抵達時間)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.回程日期)){exportForm.append(BookHSRTerm.回程日期+"："+extractMap.get(BookHSRTerm.回程日期)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.回程車次)){exportForm.append(BookHSRTerm.回程車次+"："+extractMap.get(BookHSRTerm.回程車次)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.回程出發時間)){exportForm.append(BookHSRTerm.回程出發時間+"："+extractMap.get(BookHSRTerm.回程出發時間)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.回程抵達時間)){exportForm.append(BookHSRTerm.回程抵達時間+"："+extractMap.get(BookHSRTerm.回程抵達時間)+"\n");}
		}else if(extractMap.get(BookHSRTerm.去回程).equals("單程票")){
			if(extractMap.containsKey(BookHSRTerm.日期)){exportForm.append(BookHSRTerm.日期+"："+extractMap.get(BookHSRTerm.日期)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.車次)){exportForm.append(BookHSRTerm.車次+"："+extractMap.get(BookHSRTerm.車次)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.出發時間)){exportForm.append(BookHSRTerm.出發時間+"："+extractMap.get(BookHSRTerm.出發時間)+"\n");}
			if(extractMap.containsKey(BookHSRTerm.抵達時間)){exportForm.append(BookHSRTerm.抵達時間+"："+extractMap.get(BookHSRTerm.抵達時間)+"\n");}
		}
		if(extractMap.containsKey(BookHSRTerm.票種)){exportForm.append(BookHSRTerm.票種+"："+extractMap.get(BookHSRTerm.票種)+"\n");}
		if(extractMap.containsKey(BookHSRTerm.總票價)){exportForm.append(BookHSRTerm.總票價+"："+extractMap.get(BookHSRTerm.總票價));}
		return exportForm.toString();
	}

	private String getOrderByAskBack(HSROneWaySchedule schedule, List<String> orderList, int orderIndex, String departure, String destination, IListenAndSay gui, String gc){
		int departureIndex = schedule.getStationIndex(departure);
		int destinationIndex = schedule.getStationIndex(destination);
		
		
		
		
		String order = orderList.get(orderIndex);
		String startTime = HSRSchedule.getInstance().getStartTime(order, departure);
		String ask = "目前"+gc+"的班次時間是"+startTime+"，"+AskBackTerm.請問需要更早班次或更晚班次嗎;
		String response = gui.getAnswer(ask);
		int closestEarlyIndex =HSRSchedule.getInstance().earlyNonNullIndex(schedule, orderIndex, departureIndex, destinationIndex);
		int closestLateIndex =HSRSchedule.getInstance().lateNonNullIndex(schedule, orderIndex, departureIndex, destinationIndex);
		if(response.contains("早")){
			if(orderIndex==closestEarlyIndex){
				if(gui.getAnswerTrueFalse(AskBackTerm.這是最早的班次需要晚一點的嗎)){
					return this.getOrderByAskBack(schedule, orderList, closestLateIndex, departure, destination, gui, gc);
				}else{
					return order;
				}
			}else{
				return this.getOrderByAskBack(schedule, orderList, closestEarlyIndex, departure, destination, gui, gc);
			}
		}else if(response.contains("晚")){
			int closestIndex =HSRSchedule.getInstance().lateNonNullIndex(schedule, orderIndex, departureIndex, destinationIndex);
			if(orderIndex==closestIndex){
				if(gui.getAnswerTrueFalse(AskBackTerm.這是最晚的班次需要早一點的嗎)){
					return this.getOrderByAskBack(schedule, orderList, closestEarlyIndex, departure, destination, gui, gc);
				}else{
					return order;
				}
			}else{
				return this.getOrderByAskBack(schedule, orderList, closestLateIndex, departure, destination, gui, gc);
			}
		}else{
			return order;
		}
	}
	
	private void addOneWayInfo(Map<String, String> extractMap, Map<String, String> thisRunMap, IListenAndSay gui){ 
		Set<String> thisRunSet = thisRunMap.keySet();
		Set<String> set = extractMap.keySet();
		if((!set.contains(BookHSRTerm.車次) || thisRunSet.contains(BookHSRTerm.出發地) || thisRunSet.contains(BookHSRTerm.目的地) || thisRunSet.contains(BookHSRTerm.出發時間))
			&& set.contains(BookHSRTerm.出發地) && set.contains(BookHSRTerm.目的地) && set.contains(BookHSRTerm.出發時間)
		){
			String departure = extractMap.get(BookHSRTerm.出發地);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String startTime = extractMap.get(BookHSRTerm.出發時間);
			// Find Order
			Map<String, Object> orderMap = HSRSchedule.getInstance().findOrderMap(departure, destination, startTime);
			int orderIndex = (int) orderMap.get("OrderIndex");
			List<String> orderList = (List<String>) orderMap.get("OrderList");
			HSROneWaySchedule schedule = (HSROneWaySchedule) orderMap.get("Schedule");
			String order = this.getOrderByAskBack(schedule, orderList, orderIndex, departure, destination, gui, "");
			// Find Start Time
			startTime = HSRSchedule.getInstance().findArrivedTime(order, departure);
			extractMap.put(BookHSRTerm.出發時間, startTime);
			extractMap.put(BookHSRTerm.車次, order);
		}
		if((!set.contains(BookHSRTerm.抵達時間) || thisRunSet.contains(BookHSRTerm.車次) || thisRunSet.contains(BookHSRTerm.目的地))
			&& set.contains(BookHSRTerm.車次) && set.contains(BookHSRTerm.目的地)
		){
			String order = extractMap.get(BookHSRTerm.車次);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String arrivedTime = HSRSchedule.getInstance().findArrivedTime(order, destination);
			extractMap.put(BookHSRTerm.抵達時間, arrivedTime);
		}
		if((!set.contains(BookHSRTerm.總票價) || thisRunSet.contains(BookHSRTerm.去回程) || thisRunSet.contains(BookHSRTerm.出發地) || thisRunSet.contains(BookHSRTerm.目的地) 
			|| thisRunSet.contains(BookHSRTerm.座位票種) || thisRunSet.contains(BookHSRTerm.年齡票種) || thisRunSet.contains(BookHSRTerm.票數))
			&& set.contains(BookHSRTerm.出發地) && set.contains(BookHSRTerm.目的地) 
			&& set.contains(BookHSRTerm.座位票種) && set.contains(BookHSRTerm.年齡票種) && set.contains(BookHSRTerm.票數)
		){
			String departure = extractMap.get(BookHSRTerm.出發地);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String ageTicketType = extractMap.get(BookHSRTerm.年齡票種);
			String seatTicketType = extractMap.get(BookHSRTerm.座位票種);
			int ticketCount = Integer.valueOf(extractMap.get(BookHSRTerm.票數));
			int price = HSRPrice.getInstance().getPrice(seatTicketType+ageTicketType, departure, destination);
			int total = ticketCount * price;
			extractMap.put(BookHSRTerm.總票價, String.valueOf(total));
		}
		if((!set.contains(BookHSRTerm.票種) || thisRunSet.contains(BookHSRTerm.年齡票種) || thisRunSet.contains(BookHSRTerm.票數))
			&& set.contains(BookHSRTerm.年齡票種) && set.contains(BookHSRTerm.票數)
		){
			String ageTicketType = extractMap.get(BookHSRTerm.年齡票種);
			String seatTicketType = extractMap.get(BookHSRTerm.座位票種);
			String ticketCount = extractMap.get(BookHSRTerm.票數);
			String ticketType = seatTicketType+ageTicketType+"*"+ticketCount;
			extractMap.put(BookHSRTerm.票種, ticketType);
		}
	}
	
	private void addGBInfo(Map<String, String> extractMap, Map<String, String> thisRunMap, IListenAndSay gui){ 
		Set<String> thisRunSet = thisRunMap.keySet();
		Set<String> set = extractMap.keySet();
		// 去程
		if((!set.contains(BookHSRTerm.去程車次) || thisRunSet.contains(BookHSRTerm.出發地) || thisRunSet.contains(BookHSRTerm.目的地) || thisRunSet.contains(BookHSRTerm.去程出發時間))
			&& set.contains(BookHSRTerm.出發地) && set.contains(BookHSRTerm.目的地) && set.contains(BookHSRTerm.去程出發時間)
		){
			String departure = extractMap.get(BookHSRTerm.出發地);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String startTime = extractMap.get(BookHSRTerm.去程出發時間);
			// Find Order
			Map<String, Object> orderMap = HSRSchedule.getInstance().findOrderMap(departure, destination, startTime);
			int orderIndex = (int) orderMap.get("OrderIndex");
			List<String> orderList = (List<String>) orderMap.get("OrderList");
			HSROneWaySchedule schedule = (HSROneWaySchedule) orderMap.get("Schedule");
			String order = this.getOrderByAskBack(schedule, orderList, orderIndex, departure, destination, gui, "去程");
			startTime = HSRSchedule.getInstance().findArrivedTime(order, departure);
			extractMap.put(BookHSRTerm.去程出發時間, startTime);
			extractMap.put(BookHSRTerm.去程車次, order);
		}
		if((!set.contains(BookHSRTerm.去程抵達時間) || thisRunSet.contains(BookHSRTerm.去程車次) || thisRunSet.contains(BookHSRTerm.目的地))
			&& set.contains(BookHSRTerm.去程車次) && set.contains(BookHSRTerm.目的地)
		){
			String order = extractMap.get(BookHSRTerm.去程車次);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String arrivedTime = HSRSchedule.getInstance().findArrivedTime(order, destination);
			extractMap.put(BookHSRTerm.去程抵達時間, arrivedTime);
		}
		// 回程
		if((!set.contains(BookHSRTerm.回程車次) || thisRunSet.contains(BookHSRTerm.出發地) || thisRunSet.contains(BookHSRTerm.目的地) || thisRunSet.contains(BookHSRTerm.回程出發時間))
			&& set.contains(BookHSRTerm.出發地) && set.contains(BookHSRTerm.目的地) && set.contains(BookHSRTerm.回程出發時間)
		){
			String departure = extractMap.get(BookHSRTerm.出發地);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String startTime = extractMap.get(BookHSRTerm.回程出發時間);
			// Find Order
			Map<String, Object> orderMap = HSRSchedule.getInstance().findOrderMap(destination, departure, startTime);
			int orderIndex = (int) orderMap.get("OrderIndex");
			List<String> orderList = (List<String>) orderMap.get("OrderList");	
			HSROneWaySchedule schedule = (HSROneWaySchedule) orderMap.get("Schedule");
			String order = this.getOrderByAskBack(schedule, orderList, orderIndex, destination, departure, gui, "回程");
			startTime = HSRSchedule.getInstance().findArrivedTime(order, destination);
			extractMap.put(BookHSRTerm.回程出發時間, startTime);
			extractMap.put(BookHSRTerm.回程車次, order);
		}
		if((!set.contains(BookHSRTerm.回程抵達時間) || thisRunSet.contains(BookHSRTerm.回程車次) || thisRunSet.contains(BookHSRTerm.出發地))
			&& set.contains(BookHSRTerm.回程車次) && set.contains(BookHSRTerm.出發地)
		){
			String order = extractMap.get(BookHSRTerm.回程車次);
			String destination = extractMap.get(BookHSRTerm.出發地);
			String arrivedTime = HSRSchedule.getInstance().findArrivedTime(order, destination);
			extractMap.put(BookHSRTerm.回程抵達時間, arrivedTime);
		}
		//
		if((!set.contains(BookHSRTerm.總票價) || thisRunSet.contains(BookHSRTerm.去回程) || thisRunSet.contains(BookHSRTerm.出發地) || thisRunSet.contains(BookHSRTerm.目的地) 
			|| thisRunSet.contains(BookHSRTerm.年齡票種) || thisRunSet.contains(BookHSRTerm.座位票種) || thisRunSet.contains(BookHSRTerm.票數))
		    && set.contains(BookHSRTerm.出發地) && set.contains(BookHSRTerm.目的地) 
		    && set.contains(BookHSRTerm.年齡票種) && set.contains(BookHSRTerm.座位票種) && set.contains(BookHSRTerm.票數)
		){
			String departure = extractMap.get(BookHSRTerm.出發地);
			String destination = extractMap.get(BookHSRTerm.目的地);
			String seatTicketType = extractMap.get(BookHSRTerm.座位票種);
			String ageTicketType = extractMap.get(BookHSRTerm.年齡票種);
			int ticketCount = Integer.valueOf(extractMap.get(BookHSRTerm.票數));
			int goPrice = HSRPrice.getInstance().getPrice(seatTicketType+ageTicketType, departure, destination);
			int comePrice = HSRPrice.getInstance().getPrice(seatTicketType+ageTicketType, destination, departure);
			int total = ticketCount * (goPrice + comePrice);
			extractMap.put(BookHSRTerm.總票價, String.valueOf(total));
		}
		if((!set.contains(BookHSRTerm.票種) || thisRunSet.contains(BookHSRTerm.年齡票種) || thisRunSet.contains(BookHSRTerm.座位票種) || thisRunSet.contains(BookHSRTerm.票數))
			&& set.contains(BookHSRTerm.年齡票種) && set.contains(BookHSRTerm.座位票種) && set.contains(BookHSRTerm.票數)
		){
			String seatTicketType = extractMap.get(BookHSRTerm.座位票種);
			String ageTicketType = extractMap.get(BookHSRTerm.年齡票種);
			String ticketCount = extractMap.get(BookHSRTerm.票數);
			String ticketType = seatTicketType+ageTicketType+"*"+ticketCount;
			extractMap.put(BookHSRTerm.票種, ticketType);
		}
	}
}
