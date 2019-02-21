package com.seepem.promocalls.calls;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.xspark.varyingdag.examples.utils.Utils;


public class UserCallDB
{

	private static String customer = UUID.randomUUID().toString();

	public static List<Call> getCurrentMonthCalls(){
		return getCallsOfPastMonth(0);
	}

	public static List<Call> getCallsOfPastMonth(int monthInThePast){
		List<Call> calls = new ArrayList<Call>();
		calls.addAll(Utils.createRandomCallForCaller(customer, 3500));
		return calls;
	}

	public static List<Call> getLast24HoursLocalCalls(){
		List<Call> calls = new ArrayList<Call>();
		calls.addAll(Utils.createRandomCallForCaller(customer, 100));
		return calls;
	}

	public static List<Call> getLast24HoursAbroadCalls(){
		List<Call> calls = new ArrayList<Call>();
		calls.addAll(Utils.createRandomCallForCaller(customer, 30));
		return calls;
	}


}
