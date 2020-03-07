package gc2.intent.common.module;

import gc2.nlp.module.NLPTerm;

public class IntentUtil {
	
	public static boolean isIntent(String intent){
		if(intent.equals(NLPTerm.IntentBookHSR)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBuyTV)){
			return true;
		}else if(intent.equals(NLPTerm.IntentOrderDrinks)){
			return true;
		}else if(intent.equals(NLPTerm.IntentRecommendTA)){
			return true;
		}else if(intent.equals(NLPTerm.IntentExchange)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookTR)){
			return true;
		}else if(intent.equals(NLPTerm.IntentQueryIR)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookRestaurant)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookKTV)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookBGT)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBuyTI)){
			return true;
		}else if(intent.equals(NLPTerm.IntentRecommendRestaurant)){
			return true;
		}else if(intent.equals(NLPTerm.IntentRentCar)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookAirplane)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookHotel)){
			return true;
		}else if(intent.equals(NLPTerm.IntentOrderIn)){
			return true;
		}else if(intent.equals(NLPTerm.IntentReserveHospital)){
			return true;
		}else if(intent.equals(NLPTerm.IntentHousingLoans)){
			return true;
		}else if(intent.equals(NLPTerm.IntentBookMovie)){
			return true;
		}else if(intent.equals(NLPTerm.IntentReservePCP)){
			return true;
		}
		return false;
	}
}
