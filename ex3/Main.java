package ex3;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
	public enum ExitStatus {
		OK(0),
	    INVALID_ARG(1),
	    INVALID_SW(2),
	    INVALID_SWARG(3),
	    INVALID_ACT(4);
	    
	    private final int val;
	    ExitStatus(int val) { this.val = val; }
	    public int getValue() { return val; }
	}
	
	// -t
	public static int numOfThreads = 4;
	// -u
	public static int updRatioPercent = 100;
	// -i
	public static int initListSz = 100;
	// -d
	public static int execDuration = 3000;
	// -b
	public static String testScheme = "sequential";
	public static String[] availableScheme = { "sequential", "coarse", "hoh", "optimistic" };
	
	public static IntSet set;
	
	// for deciding actions, bound 100
	public static Random actionRnd = new Random(); // Thread Safe
	public static int updThresh;
	public static int insThresh;
	public static int itemBound;

	private static void readArguments(String[] args) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		boolean isParamOfSwitch = false;
		String switchName = stack[stack.length - 1].getClassName();

		for (String arg : args) {
			System.out.print(arg + " ");
			if (isParamOfSwitch) {
				switch (switchName) {
				case "t":
					numOfThreads = Integer.parseInt(arg);
					isParamOfSwitch = false;
					break;
				case "u":
					updRatioPercent = Integer.parseInt(arg);
					isParamOfSwitch = false;
					break;
				case "i":
					initListSz = Integer.parseInt(arg);
					isParamOfSwitch = false;
					break;
				case "d":
					execDuration = Integer.parseInt(arg);
					isParamOfSwitch = false;
					break;
				case "b":
					if (Arrays.asList(availableScheme).contains(arg))
						testScheme = arg;
					else {
						System.out.println("Invalid argument for switch \"" + switchName + "\"");
						System.exit(ExitStatus.INVALID_SWARG.getValue());
					}
					break;
				default:
					System.out.println("Invalid switch \"" + switchName + "\"");
					System.exit(ExitStatus.INVALID_SW.getValue());
					break;
				}
			} else {
				if (arg.charAt(0) == '-') {
					switchName = arg.substring(1);
					isParamOfSwitch = true;
				} else {
					System.out.println("Invalid argument for \"" + switchName + "\"");
					System.exit(ExitStatus.INVALID_ARG.getValue());
				}
			}
		}
		
		// Calculation for more param
		updThresh = updRatioPercent;
		insThresh = updThresh / 2;
		itemBound = initListSz * 2;
		System.out.println();
	}
	
	private static void initSet() throws InterruptedException {
		switch(testScheme) {
		case "sequential":
			set = new SeqSet();
			break;
		case "coarse":
			set = new CGSet();
			break;
		case "hoh":
			set = new HOHSet();
			break;
		case "optimistic":
			set = new OVSet();
			break;
		default:
			System.out.println("Invalid scheme \"" + testScheme + "\"");
			System.exit(ExitStatus.INVALID_SWARG.getValue());
			break;
		}
		
		Random rnd = new Random();
		boolean res;
		for(int i = 0;i < initListSz;i++) {
			res = set.insert(rnd.nextInt(itemBound));
			// ensure number of elements
			if(!res)
				i--;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Reading arguments...");
		readArguments(args);
		initSet();
		
		
		//Create threads
		System.out.println("Creating threads...");
		Thread [] threads = new Thread[numOfThreads];
		for(int i = 0;i < numOfThreads;i++)
			threads[i] = new Thread(new WorkerRunnable());
		
		System.out.println("Start benchmark...");
		//Run threads
		for(Thread thread: threads)
			thread.start();
		//Wait threads
		TimeUnit.MILLISECONDS.sleep(execDuration);
		//End threads
		for(Thread thread: threads)
			thread.interrupt();
		//Wait before printing result **important**
		for(Thread thread: threads)
			thread.join();
		System.out.println("End benchmark.");
		((SetList)set).printSet();
	}
}
