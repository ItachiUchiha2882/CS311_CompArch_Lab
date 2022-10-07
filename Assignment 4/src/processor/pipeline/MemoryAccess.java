package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	ControlUnit cu = new ControlUnit();
	boolean isEND = false;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch) {
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}

	public void performOperations() {
		cu.opcode = "";
		cu.rs1 = "";
		cu.rs2 = "";
		cu.rd = "";
		cu.Imm = "";
	}

	public void setEnableDisable() {
		MA_RW_Latch.setRW_enable(true);
		EX_MA_Latch.setMA_enable(false);
		MA_RW_Latch.setrd(EX_MA_Latch.getrd());
	}

	public void performMA() {
		if (EX_MA_Latch.isMA_enable() && !isEND) {
			// System.out.println("MA");
			int op2 = EX_MA_Latch.getop2();
			int Final_result = EX_MA_Latch.getFinal_Result();
			int load_result = 0;

			int instruction = EX_MA_Latch.getInstruction();
			cu.setInstruction(instruction);
			MA_RW_Latch.setInstruction(instruction);

			if (cu.opcode.equals("11101")) {
				isEND = true;
			}

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

			if (cu.isSt()) {
				containingProcessor.getMainMemory().setWord(Final_result, op2);
			} else if (cu.opcode.equals("10110")) {
				load_result = containingProcessor.getMainMemory().getWord(Final_result);
				MA_RW_Latch.setLoadResult(load_result);
			} else {
				MA_RW_Latch.setFinal_result(Final_result);
			}
			setEnableDisable();

		} else
			performOperations();

	}

}
