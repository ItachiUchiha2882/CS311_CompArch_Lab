package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	// if_EnableLatchType if_EnableLatch;
	// if_of_LatchType if_of_Latch;
	// if_if_LatchType ex_if_Latch;
	static Statistics stats = new Statistics();
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void enableDisableLatches()
	{
		IF_EnableLatch.setIF_enable(false);
		IF_OF_Latch.setOF_enable(true);
		EX_IF_Latch.setIF_enable(false);
	}
	
	public void performCommonOperations(int currentPC)
	{
		int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
		IF_OF_Latch.setInstruction(newInstruction);
		containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
	}
	
	public void performIF()
	{
		// if(IF_EnableLatch.isIF_enable())
		// {
		// 	int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
		// 	int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
		// 	IF_OF_Latch.setInstruction(newInstruction);
		// 	containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
		// 	IF_EnableLatch.setIF_enable(false);
		// 	IF_OF_Latch.setOF_enable(true);
		// }

		if(IF_EnableLatch.isIF_enable()||EX_IF_Latch.isIF_enable())
		{	
			int ini=0,inc=0;
			ini = stats.getNumberOfInstructions();

			// gives us no. of instructions
			inc = Statistics.numberOfInstructions;
			stats.setNumberOfInstructions(ini+1);
			// stats.setNumberOfInstructions(ini);
			// stats.setNumberOfCycles(inc*5);
			// gives us no. of cycles
			stats.setNumberOfCycles(inc);
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			if(EX_IF_Latch.isbt==true)
			{
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter()+EX_IF_Latch.offset-1);
				currentPC = containingProcessor.getRegisterFile().getProgramCounter();
				performCommonOperations(currentPC);
			}
			else if(IF_EnableLatch.isIF_enable())
				performCommonOperations(currentPC);
			enableDisableLatches();
			
		}
	}

}
