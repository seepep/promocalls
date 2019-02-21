package com.seepep.promocalls.calls;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;


/*
 * The program simulates a daily routine of a telecommunication company. This company offers a promotion according
 * to how many -long calls- (e.g., greater than the 'threshold' parameter) a customer makes during a day.
 * It considers both local calls and calls outside the country.
 * - if the user makes more than 'minLocalLongCalls' local long calls or more than 'minAbroadLongCalls'
 * abroad long calls it will receive a 50% discount on all the calls made during the day
 * - if the user makes more than 'minLocalLongCalls' local long calls it will receive an additional discount of 5%
 * on all the calls of the current month and of 'pastMonths' in the past.
 * - if the users makes more 'minAbroadLongCalls' abroad long calls it will receive an additional discount of 10% on
 * all the calls of the current month
 *
 * Action 'collect' is used istead of action 'saveAsTextFile' for the sake of simplicity
 *
 */

@SuppressWarnings("resource")
public class PromoCalls {

  public void run(int threshold, int minLocalLongCalls, int minAbroadLongCalls, int pastMonths){

	  SparkConf conf = new SparkConf().setAppName("CallsExample").setMaster("local[4]");
	  JavaSparkContext sc = new JavaSparkContext(conf);

	  long localLongCalls = sc.parallelize(UserCallDB.getLast24HoursLocalCalls())
			  .filter(call -> call.getLength() > threshold).count();

	  long abroadLongCalls = sc.parallelize(UserCallDB.getLast24HoursAbroadCalls())
			  .filter(call -> call.getLength() > threshold).count();

	  if (localLongCalls > minLocalLongCalls || abroadLongCalls > minAbroadLongCalls){
		  sc.parallelize(UserCallDB.getLast24HoursLocalCalls()).map(call -> call.setCost(call.getCost()*0.5)).collect();
		  sc.parallelize(UserCallDB.getLast24HoursAbroadCalls()).map(call -> call.setCost(call.getCost()*0.5)).collect();
	  }

	  if (localLongCalls > minLocalLongCalls) {
		  sc.parallelize(UserCallDB.getCurrentMonthCalls()).map(call -> call.setCost(call.getCost()*0.95)).collect();
		  for (int i = 1; i <= pastMonths; i++)
			  sc.parallelize(UserCallDB.getCallsOfPastMonth(i)).map(call -> call.setCost(call.getCost()*0.95)).collect();
	  }

	  if (abroadLongCalls > minAbroadLongCalls){
		  sc.parallelize(UserCallDB.getCurrentMonthCalls()).map(call -> call.setCost(call.getCost()*0.9)).collect();
	  }

	  try {
			Thread.sleep(1000*60*60);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  sc.stop();

  }

  public static void main(String[] args) {
	  new PromoCalls().run(700, 10, 3, 2);
  }

}
