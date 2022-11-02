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
	boolean isEND = false;

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

	public void setEnableDisable(Event e) {
		MemoryResponseEvent event = (MemoryResponseEvent) e;
		IF_OF_Latch.setInstruction(event.getValue());
		IF_OF_Latch.setOF_enable(true);
		IF_EnableLatch.setIF_busy(false);
	}

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

	public void performIF() {

		if (IF_EnableLatch.isIF_enable() && !isEND) {
			if (IF_EnableLatch.isIF_busy()) {
				return;
			}
			if (EX_IF_Latch.IF_enable) {
				if (EX_IF_Latch.isbt) {
					containingProcessor.getRegisterFile().setProgramCounter(EX_IF_Latch.branchTarget);
				}
				EX_IF_Latch.IF_enable = false;
				// IF_EnableLatch.setIF_enable(false);
				// IF_OF_Latch.setOF_enable(true);
			}
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			// uncomment this
			Simulator.getEventQueue()
					.addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this,
							containingProcessor.getMainMemory(),
							containingProcessor.getRegisterFile().getProgramCounter()));
			IF_EnableLatch.setIF_busy(true);
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			Statistics.setNumberOfInstructions(Statistics.numberOfInstructions+1);
			
			// comment this
			// int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			// String instructionString = Integer.toBinaryString(newInstruction);
			// int n = instructionString.length();
			// String todo = "";
			// for (int i = 0; i < 32 - n; i++) {
			// 	todo = todo + "0";
			// }
			// instructionString = todo + instructionString;

			// if(EX_IF_Latch.isbt == true){
			// containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter()+EX_IF_Latch.offset-1);
			// currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			// performCommonOperations(currentPC);
			// } else if(IF_EnableLatch.isIF_enable())
			// performCommonOperations(currentPC);
			// enableDisableLatches();

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
	}

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (IF_OF_Latch.OF_busy) {
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		} else {
			setEnableDisable(e);
		}

	}

}
