package generic;

import java.io.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean simulationComplete;

	public static void setupSimulation(String assemblyProgramFile, Processor p) {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		simulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile) {
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

			int i = 0;
		
			// Storing the global data and instructions in the approporiate locations in the mainMemory.

			while (dataInputFile.available() > 0) {

				int j = dataInputFile.readInt();
				if (i != 0) {
					processor.getMainMemory().setWord(i - 1, j);

				} else {
					processor.getRegisterFile().setProgramCounter(j);
				}
				i++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		// setting values for registers.

		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);

	}

	public static void simulate() {
		while (simulationComplete == false) {
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
		}

		// TODO
		// set statistics

		// Statistics is done in Instructionfetch.java file
	}

	public static void setSimulationComplete(boolean value) {
		simulationComplete = value;
	}
}
