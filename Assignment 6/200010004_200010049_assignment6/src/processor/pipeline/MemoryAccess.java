package processor.pipeline;

import processor.Clock;
import processor.Processor;
import configuration.Configuration;
import generic.Element;
import generic.Event;
import generic.Event.EventType;
import generic.ExecutionCompleteEvent;
import generic.MemoryReadEvent;
import generic.MemoryResponseEvent;
import generic.MemoryWriteEvent;
import generic.Simulator;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	public EX_MA_LatchType EX_MA_Latch;
	public MA_RW_LatchType MA_RW_Latch;
	// ControlUnit cu = new ControlUnit();
	// boolean isEND = false;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch) {
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	// public void performOperations() {
	// 	cu.opcode = "";
	// 	cu.rs1 = "";
	// 	cu.rs2 = "";
	// 	cu.rd = "";
	// 	cu.Imm = "";
	// }

	// public void setEnableDisable() {
	// 	MA_RW_Latch.setRW_enable(true);
	// 	EX_MA_Latch.setMA_enable(false);
	// 	// MA_RW_Latch.setrd(EX_MA_Latch.getrd());
	// }

	public void performMA()
	{
		//TODO
		if (EX_MA_Latch.isMA_enable()) {
			if (EX_MA_Latch.isMA_busy()) {
				return;
			}
			int opcode = EX_MA_Latch.getOpcode();
			int rd = EX_MA_Latch.getrd();
			int ALUResult = EX_MA_Latch.getALUResult();
			int op1 = EX_MA_Latch.getop1();
			int reg2 = EX_MA_Latch.getreg2();
			int ldResult = -1;
			int remainder = EX_MA_Latch.getremainder();
			
			// if (cu.opcode.equals("10110")){ // Load
			// int idResult = containingProcessor.getMainMemory().getWord(address); // get
			// value from address form memory
			// MA_RW_Latch.setIdResult(idResult);
			// } else if(cu.opcode.equals("10111")){ // Store
			// int value_rs1 = containingProcessor.getRegisterFile().getValue(rs1); //
			// getting value in rs1
			// containingProcessor.getMainMemory().setWord(address, value_rs1); // setting
			// value in memory
			// MA_RW_Latch.setIdResult(0);
			// }

			// comment this
			// if (cu.isSt()) {
			// 	containingProcessor.getMainMemory().setWord(Final_result, op2);
			// } else if (cu.opcode.equals("10110")) {
			// 	load_result = containingProcessor.getMainMemory().getWord(Final_result);
			// 	MA_RW_Latch.setLoadResult(load_result);
			// } else {
			// 	MA_RW_Latch.setFinal_result(Final_result);
			// }

			// Load:
			if (opcode == 22) {
				Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), ALUResult));
				EX_MA_Latch.setMA_busy(true);
			}
			// Store:
			else if (opcode == 23) {
				Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, containingProcessor.getMainMemory(), ALUResult, op1));
				EX_MA_Latch.setMA_busy(true);
			}
			
			MA_RW_Latch.setALUResult(ALUResult);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setreg2(reg2);
			MA_RW_Latch.setrd(rd);
			MA_RW_Latch.setremainder(remainder);
			
			if (!EX_MA_Latch.isMA_busy()) {
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
		}
	}	

	@Override
	public void handleEvent(Event e) {
		// TODO Auto-generated method stub
		if (MA_RW_Latch.isRW_busy()) {
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else if (e.getEventType() == EventType.MemoryResponse){
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			MA_RW_Latch.setldResult(event.getValue());
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_busy(false);
			EX_MA_Latch.setMA_enable(false);
			containingProcessor.getIF_OF().setOF_busy(false);
			containingProcessor.getIF_OF().setOF_enable(true);
			containingProcessor.getIF_OF().setRdRW(containingProcessor.getIF_OF().getRdMA());
			containingProcessor.getIF_OF().setx31RW(containingProcessor.getIF_OF().getx31MA());
			if(!containingProcessor.getOF_EX().isEX_busy())
			{
				containingProcessor.getIF_OF().setRdMA(containingProcessor.getIF_OF().getRdEX());
				containingProcessor.getIF_OF().setx31MA(containingProcessor.getIF_OF().getx31EX());
				containingProcessor.getIF_OF().setRdEX(-2);
				containingProcessor.getIF_OF().setx31EX(-2);
			}
			else
			{
				containingProcessor.getIF_OF().setRdMA(-2);
				containingProcessor.getIF_OF().setx31MA(-2);
			}
			containingProcessor.getOF_EX().setEX_busy(false);
			containingProcessor.getOF_EX().setEX_enable(true);
		}
		else if (e.getEventType() == EventType.ExecutionComplete){
			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
			MA_RW_Latch.setRW_enable(true);
			EX_MA_Latch.setMA_busy(false);
			EX_MA_Latch.setMA_enable(false);
			containingProcessor.getOF_EX().setEX_busy(false);
			containingProcessor.getIF_OF().setOF_busy(false);
			containingProcessor.getIF_OF().setOF_enable(true);
			containingProcessor.getOF_EX().setEX_enable(true);
		}
		
	}
}
