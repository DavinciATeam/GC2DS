package gc2.intent.common.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gc2.nlp.module.Sentence;

public class DialogHistory {
	private List<Integer> order = new ArrayList<Integer>();
	private Map<Integer, String> agentSay = new HashMap<Integer, String>();
	private Map<Integer, List<Sentence>> userResponse = new HashMap<Integer, List<Sentence>>();
	
	public void addDialog(String agent, List<Sentence> user){
		int timestamp = this.order.size();
		this.order.add(timestamp);
		this.agentSay.put(timestamp, agent);
		this.userResponse.put(timestamp, user);
	}
	
	public int getLastTimestamp(){
		int lastTimestamp = this.order.size()-1;
		return lastTimestamp;
	}
	
	public String getLastAgentSay(){
		int lastTimestamp = this.order.size()-1;
		return this.agentSay.get(lastTimestamp);
	}
	
	public List<Sentence> getLastUserResponse(){
		int lastTimestamp = this.order.size()-1;
		return this.userResponse.get(lastTimestamp);
	}
	
	public String getAgentSayByTimestamp(int timestamp){
		if(timestamp<this.order.size() && timestamp>-1){
			return this.agentSay.get(timestamp);
		}else{
			return "";
		}
	}
	
	public List<Sentence> getUserResponseByTimestamp(int timestamp){
		if(timestamp<this.order.size() && timestamp>-1){
			return this.userResponse.get(timestamp);
		}else{
			return new ArrayList<Sentence>();
		}
	}
}
