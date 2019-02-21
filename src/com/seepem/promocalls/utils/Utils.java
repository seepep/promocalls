package com.seepem.promocalls.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.xspark.varyingdag.examples.calls.Call;


public class Utils
{
	  public static List<Integer> createRandomIntegerList(int size) {

		  List<Integer> res = new ArrayList<Integer>(size);
		  Random r = new Random();
		  while(size>0){
			  res.add(r.nextInt());
			  size--;
		  }

		  return res;
	  }

	  public static List<Call> createRandomCallForCaller(String callerId, int size){

		  List<Call> res = new ArrayList<Call>(size);
		  Random r = new Random();
		  while(size>0){
			  int length =  r.nextInt(1000);
			  res.add(new Call(callerId, UUID.randomUUID().toString(), length, length * 0.2));
			  size--;
		  }

		  return res;

	  }
}
