package generic;

// import java.io.*;
// import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean simulationComplete;
	// static int cycle = 0;
	static EventQueue eventQueue;

	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
		eventQueue = new EventQueue();
	}

	public static EventQueue getEventQueue() {
		return eventQueue;
	}

	
	static void loadProgram(String assemblyProgramFile) throws IOException {
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 * in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 * x0 = 0
		 * x1 = 65535
		 * x2 = 65535
		 */
		ArrayList <String> list = new ArrayList<String>();
		try{
			InputStream inputFile = new FileInputStream(assemblyProgramFile);
			// making the input file here.

			// DataInputStream dataInputFile = new DataInputStream(inputFile);
			// making the data input file here.

			// int i = 0; // here i is address.
			int i, data;
			data = 0;
			String istring = "";
			while ((i = inputFile.read()) != -1) {
				String s = Integer.toHexString(i);
				if (s.length() == 2) {
					istring = istring + s;
				}
				else if (s.length() == 1) {
					istring = istring + "0" + s;
				}
				if (data == 3) {
					data = -1;
					list.add(istring);
					istring = "";
				}
				data = data + 1;
			}

			// Storing the global data and instructions in the approporiate locations in the
			// mainMemory.

			// uncomment this
			// processor.getRegisterFile().setProgramCounter(dataInputFile.readInt());
			// while (dataInputFile.available() > 0) {
			// 	processor.getMainMemory().setWord(i, dataInputFile.readInt());
			// 	i++;
			// }

			// comment this
			// while (dataInputFile.available() > 0) {
			// 	int j = dataInputFile.readInt();

			// 	// working code
			// 	if (i != 0) {
			// 		processor.getMainMemory().setWord(i - 1, j);
			// 	} else {
			// 		processor.getRegisterFile().setProgramCounter(j);
			// 	}
			// 	// another approach (not working)
			// 	// if(i == -1)
			// 	// 		processor.getRegisterFile().setProgramCounter(j);
			// 	// else
			// 	// 		processor.getMainMemory().setWord(i, j);
			// 	i++;
			// }

			// setting values for registers.
			// processor.getMainMemory().getContentsAsString(0, 10);
			// processor.getRegisterFile().setValue(0, 0);
			// processor.getRegisterFile().setValue(1, 65535);
			// processor.getRegisterFile().setValue(2, 65535);

			// // closing the file
			// dataInputFile.close();

			}
		catch (Exception e) {
			System.out.println(e);
		}

		// Step 1: Loading into Main Memory
		for (int i = 0; i < list.size() - 1; i++) {
			int l = Integer.parseUnsignedInt(list.get(i + 1), 16);
			processor.getMainMemory().setWord(i, l);
		}
		
		// Step 2: Setting PC
		processor.getRegisterFile().setProgramCounter(Integer.parseUnsignedInt(list.get(0), 16));
		
		// Step 3: Setting x0, x1, x2
		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);
	}

	

	public static void simulate() {
		int count = 30;
		long Instructions = 0;
		boolean flag = true;
		while (simulationComplete == false) {
			// processor.getRWUnit().performRW();
			// processor.getMAUnit().performMA();
			// processor.getEXUnit().performEX();
			// processor.getOFUnit().performOF();
			// processor.getIFUnit().performIF();
			// Clock.incrementClock();
			// cycle++;
		  // incrementing no. of cycles

			eventQueue.processEvents();
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			if (processor.getIF_Flag()) {
				processor.getIF_Enable().setIF_enable(true);
			}
			if ((processor.getMA_RW().isRW_enable()) && (flag == true)) {
				Instructions = Instructions + 1;
			}
			if (processor.getMA_RW().getOpcode() == 29) {
				flag = false;
			}
			count -= 1;
			Clock.incrementClock();
			
			// cycle++;
		}

		// TODO
		// set statistics
		// Statistics.setNumberOfCycles(cycle);
		long Cycles = Clock.getCurrentTime();
		Statistics.setNumberOfCycles(Cycles);
		Statistics.setNumberOfInstructions(Instructions); 
		// Statistics.setCPI();
		// Statistics is done in Instructionfetch.java file
	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
