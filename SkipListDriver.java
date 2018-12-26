package cxz173430;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

//Driver program for skip list implementation.

public class SkipListDriver {
    public static void main(String[] args) throws FileNotFoundException {
	Scanner sc;
	if (args.length > 5) {
	    File file = new File(args[0]);
	    System.out.println(args[0]);
	    sc = new Scanner(file);
	} else {
	    sc = new Scanner(System.in);
	}
	sc = new Scanner(new File("p2-in13.txt"));
	String operation = "";
	long operand = 0;
	long result = 0;
	Long returnValue = null;
	SkipList1<Long> skipList = new SkipList1<>();
	// Initialize the timer
	Timer timer = new Timer();
	
	while (!((operation = sc.next()).equals("End"))) {
	    returnValue = null;
	    switch (operation) {
	    case "Add":
		operand = sc.nextLong();
		if(skipList.add(operand)) {
		    returnValue = 1L;
		}
		break;
	    case "Ceiling":
		operand = sc.nextLong();
		returnValue = skipList.ceiling(operand);
	
		break;
	    case "First":
		returnValue = skipList.first();

		break;
	    case "Get":
		int intOperand = sc.nextInt();
		returnValue = skipList.get(intOperand);
	
		break;
	    case "Last":
		returnValue = skipList.last();
		
		break;
	    case "Floor":
		operand = sc.nextLong();
		returnValue = skipList.floor(operand);
	
		break;
	    case "Remove":
		operand = sc.nextLong();
		if (skipList.remove(operand) != null) {
		    returnValue = 1L;
		}
		
		break;
	    case "Contains":
		operand = sc.nextLong();
		if (skipList.contains(operand)) {
		    returnValue = 1L;
		   
		}
		/*
		    case "Ceiling":
		operand = sc.nextLong();
		returnValue = skipList.ceiling(operand);
		System.out.println("The ceiling of " + operand + " is " + returnValue);
		break;
	    case "First":
		returnValue = skipList.first();
		System.out.println("The First of the list is " + returnValue);
		break;
	    case "Get":
		int intOperand = sc.nextInt();
		returnValue = skipList.get(intOperand);
		System.out.println("The value in index: " + operand + " is " + returnValue);
		break;
	    case "Last":
		returnValue = skipList.last();
		System.out.println("The last of the list is " + returnValue);
		break;
	    case "Floor":
		operand = sc.nextLong();
		returnValue = skipList.floor(operand);
		System.out.println("The floor of " + operand + " is " + returnValue);
		break;
	    case "Remove":
		operand = sc.nextLong();
		if (skipList.remove(operand) != null) {
		    returnValue = 1L;
		}
		System.out.println("Remove " + operand);
		break;
	    case "Contains":
		operand = sc.nextLong();
		if (skipList.contains(operand)) {
		    returnValue = 1L;
		    System.out.println("The list contain " + operand + " is true.");
		}
		 */
		break;
	    }
	    if (returnValue != null) {
		result += returnValue;
		
	    }
	}
	//System.out.println(skipList);
	int x = 0;
	Iterator<Long> ite = skipList.iterator();
	System.out.print("Size:[" + skipList.size() + "]: ");
	while(ite.hasNext())
	{
		System.out.print(ite.next() + " ");
		x++;
	}
	System.out.println("\n" + x);
	// End Time
	timer.end();
	
	

	System.out.println(result);
	System.out.println(timer);
    }
    
    /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
	long startTime, endTime, elapsedTime, memAvailable, memUsed;

	public Timer() {
	    startTime = System.currentTimeMillis();
	}

	public void start() {
	    startTime = System.currentTimeMillis();
	}

	public Timer end() {
	    endTime = System.currentTimeMillis();
	    elapsedTime = endTime-startTime;
	    memAvailable = Runtime.getRuntime().totalMemory();
	    memUsed = memAvailable - Runtime.getRuntime().freeMemory();
	    return this;
	}

	public String toString() {
	    return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
	}
    }


}