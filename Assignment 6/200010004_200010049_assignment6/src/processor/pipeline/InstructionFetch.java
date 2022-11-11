package processor.pipeline;

import generic.Statistics;
import processor.Processor;

// import java.util.Arrays;
// import generic.Instruction;
// import generic.Instruction.OperationType;
// import generic.Operand;
// import generic.Operand.OperandType;

import configuration.Configuration;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.Simulator;
import processor.Clock;
import generic.Element;
import generic.Event;

public class InstructionFetch implements Element{

	Processor containingProcessor;
	public IF_EnableLatchType IF_EnableLatch;
	public IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;

	// if_EnableLatchType if_EnableLatch;
	// if_of_LatchType if_of_Latch;
	// if_if_LatchType ex_if_Latch;

	// boolean conflict = false;
	// boolean isEND = false;

	// static Statistics stats = new Statistics();

	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch,
			EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	// public void setEnableDisable() {
	// 	IF_EnableLatch.setIF_enable(false);
	// 	IF_OF_Latch.setOF_enable(true);
	// }

	// public void setEnableDisable(Event e) {
	// 	MemoryResponseEvent event = (MemoryResponseEvent) e;
	// 	IF_OF_Latch.setInstruction(event.getValue());
	// 	IF_OF_Latch.setOF_enable(true);
	// 	IF_EnableLatch.setIF_busy(false);
	// }

	// assign3 code
	// public void enableDisableLatches()
	// {
	// IF_EnableLatch.setIF_enable(false);
	// IF_OF_Latch.setOF_enable(true);
	// EX_IF_Latch.setIF_enable(false);
	// }

	// public void performCommonOperations(int currentPC)
	// {
	// int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
	// IF_OF_Latch.setInstruction(newInstruction);
	// containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
	// }

	public void performIF() 
	{
		if(IF_EnableLatch.isIF_enable())
		{
			if (IF_EnableLatch.isIF_busy()) {
				return;
			}
			// if(EX_IF_Latch.isbt == true){
			// containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter()+EX_IF_Latch.offset-1);
			// currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			// performCommonOperations(currentPC);
			// } else if(IF_EnableLatch.isIF_enable())
			// performCommonOperations(currentPC);
			// enableDisableLatches();

			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			if(containingProcessor.getControlHazardFlag())
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.L1i_latency, this, containingProcessor.getL1iCache(), currentPC));
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_EnableLatch.setIF_busy(true);
			IF_OF_Latch.setOF_enable(false);
		}
			// comment this
			// int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			// String instructionString = Integer.toBinaryString(newInstruction);
			// int n = instructionString.length();
			// String todo = "";
			// for (int i = 0; i < 32 - n; i++) {
			// 	todo = todo + "0";
			// }
			// instructionString = todo + instructionString;


			// boolean x = !conflict;
			// switch (String.valueOf(x)) {
			// 	case "true":
			// 		if (instructionString.substring(0, 5).equals("11101")) {
			// 			isEND = true;
			// 		}
			// 		// gives us no. of instructions
			// 		Statistics.setNumberOfInstructions(Statistics.numberOfInstructions + 1);
			// 		IF_OF_Latch.setInstruction(newInstruction);
			// 		containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			// }
			// setEnableDisable();
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (IF_OF_Latch.isOF_busy()) { 
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else {
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			if ((containingProcessor.getControlHazardFlag() == false) || (Configuration.mainMemoryLatency <= 1)) { //
				if (containingProcessor.getControlHazardFlag() == false) {
					IF_OF_Latch.setInstruction(event.getValue());
					IF_OF_Latch.setOF_enable(true);
				}
				else {
					if (event.getValue() != -402653184) {
						IF_OF_Latch.setInstruction(event.getValue());
						IF_OF_Latch.setOF_enable(true);
					}
					containingProcessor.setControlHazardFlag(false);
				}
			}
			else {
				IF_OF_Latch.setInstruction(-1);
				containingProcessor.setControlHazardFlag(false);
			}
			IF_EnableLatch.setIF_busy(false);
		}
	}

}
