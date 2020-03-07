package gc2.intent.book_airplane.module.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.edu.ncu.csie.lab.util.FileIO;

public class AirplanePrice {
	private List<String> ticketTypes = new ArrayList<String>();
	private Map<String, List<Integer>> airplanePriceMap = new HashMap<String, List<Integer>>();
	//Singleton
	private AirplanePrice(){}
	private static AirplanePrice airplanePrice = new AirplanePrice();
	public static AirplanePrice getInstance(){
		if(airplanePrice==null){
			airplanePrice = new AirplanePrice();
		}
		return airplanePrice;
	}
	
	public int getTicketTypeIndex(String ticketType){
		int index = -1;
		for(int i=0; i<ticketTypes.size(); i++){
			if(ticketTypes.get(i).equals(ticketType)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public void enhance(String path){
		List<String> lines = FileIO.readFile(path, "UTF-8");
		// Ticket Type
		String[] types = lines.get(0).split(",");
		for(int i=2; i<types.length; i++){ ticketTypes.add(types[i]);}
		//
		for(int i=1; i<lines.size(); i++){
			String[] items = lines.get(i).split(",");
			String departure = items[0];
			String destination = items[1];
			List<Integer> priceList = new ArrayList<Integer>();
			for(int j=2; j<items.length; j++){
				priceList.add(Integer.valueOf(items[j]));
			}
			airplanePriceMap.put(departure+"_"+destination, priceList);
		}
	}
	
	public int getPrice(String ticketType, String departure, String destination){
		int ticketTypeIndex = this.getTicketTypeIndex(ticketType);
		int price = airplanePriceMap.get(departure+"_"+destination).get(ticketTypeIndex);
		return price;
	}

	public List<String> getTicketTypes() {
		return ticketTypes;
	}

	public Map<String, List<Integer>> getHsrPriceMap() {
		return airplanePriceMap;
	}
}
