package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here

	// make them 0 at start, if needed
	public static long numberOfInstructions;
	// static int numberOfCycles;
	public static long numberOfCycles;
	// public static int dataHazard=0;
	// public static int controlHazard=0;
	
	public static void printStatistics(String statisticFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statisticFile);
			
			// TODO add code here to print statistics in the output file

			// writer.println("Number of instructions executed = " + numberOfInstructions);
			// writer.println("Number of cycles taken = " + numberOfCycles);
			// writer.println("Throughput = " + (float)numberOfInstructions/numberOfCycles );
			// writer.println("Number of times the OF stage needed to stall because of a datahazard = " + dataHazard);
			// writer.println("Number of times an instruction on a wrong branch path entered the pipeline = " + controlHazard);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Throughput(IPC) = " + (float)numberOfInstructions/numberOfCycles);
			
			System.out.println("Number of instructions executed = " + numberOfInstructions);
			System.out.println("Number of cycles taken = " + numberOfCycles);
			System.out.println("Throughput(IPC) = " + (float)numberOfInstructions/numberOfCycles);

			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	// public void setNumberOfInstructions(int numberOfInstructions) {
	// 	Statistics.numberOfInstructions = numberOfInstructions;
	// }

	public static void setNumberOfInstructions(long numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}
	
	public static void setNumberOfCycles(long numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
	
	// public int getNumberOfInstructions() {
	// 	return Statistics.numberOfInstructions;
	// }
	// public int getNumberOfCycles() {
	// 	return Statistics.numberOfCycles;
	// }
}
