package generic;

import java.io.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean simulationComplete;
	static int cycle = 0;
	static EventQueue eventQueue;

	public static void setupSimulation(String assemblyProgramFile, Processor p) throws IOException {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		eventQueue = new EventQueue();
		simulationComplete = false;
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
		try {
			InputStream inputFile = new FileInputStream(assemblyProgramFile);
			// making the input file here.

			DataInputStream dataInputFile = new DataInputStream(inputFile);
			// making the data input file here.

			int i = 0; // here i is address.

			// Storing the global data and instructions in the approporiate locations in the
			// mainMemory.

			// uncomment this
			processor.getRegisterFile().setProgramCounter(dataInputFile.readInt());
			while (dataInputFile.available() > 0) {
				processor.getMainMemory().setWord(i, dataInputFile.readInt());
				i++;
			}

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
			processor.getMainMemory().getContentsAsString(0, 10);
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);

			// closing the file
			dataInputFile.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void simulate() {
		while (simulationComplete == false) {
			// processor.getRWUnit().performRW();
			// processor.getMAUnit().performMA();
			// processor.getEXUnit().performEX();
			// processor.getOFUnit().performOF();
			// processor.getIFUnit().performIF();
			// Clock.incrementClock();
			// cycle++;
		  // incrementing no. of cycles

			Clock.incrementClock();
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			
			cycle++;
		}

		// TODO
		// set statistics
		Statistics.setNumberOfCycles(cycle);
		// Statistics is done in Instructionfetch.java file
	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
