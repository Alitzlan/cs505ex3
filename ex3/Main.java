package ex3;

import java.util.Arrays;

public class Main {
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
	public static String [] availableScheme = {"sequential", "coarse", "hoh", "optimistic"};

	private static void readArguments(String[] args) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		boolean isParamOfSwitch = false;
		String switchName = stack[stack.length - 1].getClassName();

		for (String arg : args) {
			System.out.print(arg+" ");
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
					if(Arrays.asList(availableScheme).contains(arg))
						testScheme = arg;
					else {
						System.out.println("Invalid argument for switch \"" + switchName + "\"");
						System.exit(1);
					}
					break;
				default:
					System.out.println("Invalid switch \"" + switchName + "\"");
					System.exit(1);
					break;
				}
			} else {
				if (arg.charAt(0) == '-') {
					switchName = arg.substring(1);
					isParamOfSwitch = true;
				}
				else {
					System.out.println("Invalid argument for \"" + switchName + "\"");
					System.exit(1);
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("Reading arguments...");
		readArguments(args);
	}
}
