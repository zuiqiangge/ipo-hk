package com.pengyue.ipo.action;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String month = "2017-11-05".substring(5, 7);
		String day = "2017-01-05".substring(8, 10);
		if (month.startsWith("0")) {
			month = month.substring(1, 2) + "月";
		} else {
			month = month + "月";
		}
		
		if (day.startsWith("0")) {
			day = day.substring(1, 2) + "日";
		} else {
			day = day + "日";
		}
		System.out.println(month+day);
	}

}
