package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	public static int numberOfInstructions = 0;
	// static int numberOfCycles;
	public static int numberOfCycles = 0;
	public static int dataHazard=0;
	public static int controlHazard=0;
	
	public static void printStatistics(String statisticFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statisticFile);
			
			// TODO add code here to print statistics in the output file
			// writer.println("Number of instructions executed = " + numberOfInstructions);
			
			// writer.println("Number of cycles taken = " + numberOfCycles);
			
			// writer.println("Number of times the OF stage needed to stall because of a dataHazardard = " + dataHazard);
			
			// writer.println("Number of times an instruction on a wrong branch path entered the pipeline = " + controlHazard);

			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Throughput = " + (float)numberOfInstructions/numberOfCycles );
			writer.println("Number of times the OF stage needed to stall because of a datahazard = " + dataHazard);
			writer.println("Number of times an instruction on a wrong branch path entered the pipeline = " + controlHazard);
			
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

	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}
	
	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
	
	public int getNumberOfInstructions() {
		return Statistics.numberOfInstructions;
	}
	public int getNumberOfCycles() {
		return Statistics.numberOfCycles;
	}
}
