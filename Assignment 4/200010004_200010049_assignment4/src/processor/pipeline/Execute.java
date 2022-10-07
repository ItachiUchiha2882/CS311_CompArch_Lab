package processor.pipeline;

import generic.Statistics;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	ControlUnit cu = new ControlUnit();
	boolean isEND = false;
	int operand1;
	int operand2;

	public void setop1(int op1) {
		operand1 = op1;
	}

	public void setop2(int op2) {
		operand2 = op2;
	}

	public int eval(String opcode) {
		int ALU_result = 0;

		switch (Integer.parseInt(opcode, 2)) {
			case 0: { // for add
				ALU_result = operand1 + operand2;
				break;
			}
			case 1: { // for addi
				ALU_result = operand1 + operand2;
				break;
			}
			case 2: { // for sub
				ALU_result = operand1 - operand2;
				break;
			}
			case 3: { // for subi
				ALU_result = operand1 - operand2;
				break;
			}
			case 4: { // for mul
				ALU_result = operand1 * operand2;
				break;
			}
			case 5: { // for muli
				ALU_result = operand1 * operand2;
				break;
			}
			case 6: { // for div
				ALU_result = operand1 / operand2;
				break;
			}
			case 7: { // for divi
				ALU_result = operand1 / operand2;
				break;
			}
			case 8: { // for and
				ALU_result = operand1 & operand2;
				break;
			}
			case 9: { // for andi
				ALU_result = operand1 & operand2;
				break;
			}
			case 10: { // for or
				ALU_result = operand1 | operand2;
				break;
			}
			case 11: { // for ori
				ALU_result = operand1 | operand2;
				break;
			}
			case 12: { // for xor
				ALU_result = operand1 ^ operand2;
				break;
			}
			case 13: { // for xori
				ALU_result = operand1 ^ operand2;
				break;
			}
			case 14: { // for slt
				if (operand1 < operand2) {
					ALU_result = 1;
				} else {
					ALU_result = 0;
				}
				break;
			}
			case 15: { // for slti
				if (operand1 < operand2) {
					ALU_result = 1;
				} else {
					ALU_result = 0;
				}
				break;
			}
			case 16: { // for sll
				ALU_result = operand1 << operand2;
				break;
			}
			case 17: { // for slli
				ALU_result = operand1 << operand2;
				break;
			}
			case 18: { // for srl
				ALU_result = operand1 >>> operand2;
				break;
			}
			case 19: { // for srli
				ALU_result = operand1 >>> operand2;
				break;
			}
			case 20: { // for sra
				ALU_result = operand1 >> operand2;
				break;
			}
			case 21: { // for srai
				ALU_result = operand1 >> operand2;
				break;
			}
			case 22: {
				ALU_result = operand1 + operand2;
				break;
			}
			case 23: {
				ALU_result = operand1 + operand2;
				break;
			}
		}

		return ALU_result;
	}

	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch,
			EX_IF_LatchType eX_IF_Latch) {
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}

	public void performEX() {
		if (OF_EX_Latch.isEX_enable() && !isEND) {
			int isbranchtaken = 0, branchPC = 0;
			int instruction = OF_EX_Latch.getInstruction();
			cu.setInstruction(instruction);
			EX_MA_Latch.setInstruction(instruction);

			int op1 = OF_EX_Latch.getoperand1();
			int op2 = OF_EX_Latch.getoperand2();

			String opcode = cu.opcode;
			int imm = OF_EX_Latch.getimmx();

			int Final_result = 0;
			if (cu.isimm() || opcode.equals("11101")) {
				setop1(op1);
				setop2(imm);
				Final_result = eval(opcode);
				if (opcode.equals("00111"))
					containingProcessor.getRegisterFile().setValue(31, op1 % imm);
				EX_MA_Latch.setop2(op2);
				EX_MA_Latch.setFinal_Result(Final_result);
				EX_MA_Latch.setMA_enable(true);
			} else if (!opcode.equals("11000") && !opcode.equals("11001") && !opcode.equals("11010")
					&& !opcode.equals("11011") && !opcode.equals("11100") && !opcode.equals("11101")) {
				setop1(op1);
				setop2(op2);
				Final_result = eval(opcode);
				if (opcode.equals("00110"))
					containingProcessor.getRegisterFile().setValue(31, op1 % op2);
				EX_MA_Latch.setFinal_Result(Final_result);
				EX_MA_Latch.setMA_enable(true);
			} else {
				switch (Integer.parseInt(cu.opcode, 2)) {
					case 24: {
						isbranchtaken = 1;
						branchPC = OF_EX_Latch.getbranchtarget();
						break;
					}
					case 25: {
						if (op1 == op2) {
							isbranchtaken = 1;
							branchPC = OF_EX_Latch.getbranchtarget();
						}
						break;
					}
					case 26: {
						if (op1 != op2) {
							isbranchtaken = 1;
							branchPC = OF_EX_Latch.getbranchtarget();
						}
						break;
					}
					case 27: {
						if (op1 < op2) {
							isbranchtaken = 1;
							branchPC = OF_EX_Latch.getbranchtarget();
						}
						break;
					}
					case 28: {
						if (op1 > op2) {
							isbranchtaken = 1;
							branchPC = OF_EX_Latch.getbranchtarget();
						}
						break;
					}
					case 29: {
						isEND = true;
						EX_MA_Latch.setMA_enable(true);
					}
				}
			}
			switch (isbranchtaken) {
				case 1:
					if (containingProcessor.getIFUnit().isEND == true) {
						Statistics.controlHazard += 1;
					} else {
						Statistics.controlHazard += 2;
					}
					EX_IF_Latch.setisbranchtaken();
					EX_IF_Latch.setbranchtarget(branchPC);
					EX_IF_Latch.setIF_enable(true);
					containingProcessor.getOFUnit().IF_OF_Latch.OF_enable = false;
					containingProcessor.getOFUnit().isEND = false;
					containingProcessor.getIFUnit().IF_EnableLatch.IF_enable = false;
					containingProcessor.getIFUnit().isEND = false;
					break;
				default:
					EX_MA_Latch.setrd(OF_EX_Latch.getrd());
					break;
			}
			OF_EX_Latch.setEX_enable(false);
		} else {
			cu.opcode = "";
			cu.rs1 = "";
			cu.rs2 = "";
			cu.rd = "";
			cu.Imm = "";
		}
	}
}