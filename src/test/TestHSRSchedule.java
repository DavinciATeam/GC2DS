package test;

import gc2.intent.book_hsr.module.model.HSRSchedule;

public class TestHSRSchedule {

	public static void main(String[] args) {
		HSRSchedule.getInstance().enhance("resource/csv/1_高鐵訂票/高鐵北上車表.csv", "resource/csv/1_高鐵訂票/高鐵南下車表.csv");
		String order = HSRSchedule.getInstance().findOrder("台北","台中","9:01");
		System.out.println(order);
	}

}
