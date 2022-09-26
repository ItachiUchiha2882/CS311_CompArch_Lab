package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	public static int numberOfInstructions = 0;
	public static int numberOfCycles = 0;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	
	// TODO write functions to update statistics
	public void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}
	
	public void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
	
	public int getNumberOfInstructions() {
		return Statistics.numberOfInstructions;
	}
	public int getNumberOfCycles() {
		return Statistics.numberOfCycles;
	}
}
