package generic;

import java.io.*;
import java.util.*;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import generic.Instruction.OperationType;
import generic.Operand.OperandType;

public class Simulator {

	static FileInputStream inputcodeStream = null;

	// here code_instruction method is implemented.
	public static int code_instruction(OperationType in) {
		ArrayList<OperationType> instruct = new ArrayList<OperationType>();
		for (OperationType o : OperationType.values()) {
			instruct.add(o);
		}
		for (int i = 0; i < instruct.size(); i++) {
			if (instruct.get(i) == in) {
				return i;
			}
		}
		return -1;
	}

	// setupSimulation program starts from here.
	public static void setupSimulation(String assemblyProgramFile) {
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	// assemble program starts from here.
	public static void assemble(String objectProgramFile) {
		FileOutputStream fos;
		DataOutputStream dos;

		// instruction method importing for making new cur_ins variable to use it later.
		Instruction cur_ins = new Instruction();
		try {
			// TODO your assembler code
			// 1. open the objectProgramFile in binary mode

			fos = new FileOutputStream(objectProgramFile);
			dos = new DataOutputStream(fos);

			// 2. write the firstCodeAddress to the file
			// we can also paste the address of main as starting address.
			byte[] addressCode = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			dos.write(addressCode);

			// dos.writeInt(ParsedProgram.symtab.get("main"));

			// 3. write the data to the file
			// for (Instruction value : ParsedProgram.data) {
			// byte[] dataValue = ByteBuffer.allocate(4).putInt(value).array();
			// dos.write(dataValue);
			// }

			// writing data to the file imported earlier.
			for (int i = 0; i < ParsedProgram.data.size(); i++) {
				dos.writeInt(ParsedProgram.data.get(i));
			}
			// 4. assemble one command at a time, and write to the file
			for (int i = ParsedProgram.symtab.get("main"); i < ParsedProgram.code.size()
					+ ParsedProgram.symtab.get("main"); i++) {
				String command = Integer
						.toBinaryString(code_instruction(ParsedProgram.getInstructionAt(i).getOperationType()));
				// we get command/instruction by converting to binary string at getting info
				// about getInstructionAt.
				command = String.format("%5s", command).replaceAll(" ", "0");
				// here command means instruction.

				cur_ins = ParsedProgram.getInstructionAt(i);
				// made a new var for easy use of ParsedProgram.getInstructionAt(i) later
				// onwards.
				OperationType match = cur_ins.getOperationType();
				// made a new var for easy use of
				// ParsedProgram.getInstructionAt(i).getOperationType() later onwards.

				// OperationType getDestinOperand = cur_ins.getDestinationOperand();

				// switch (cur_ins.getOperationType()) {
				switch (match) {
					// R3I type
					// various instructions like add, sub which needs rd, rs1, rs2 are compared
					// here.
					case add:
					case sub:
					case mul:
					case div:
					case and:
					case or:
					case xor:
					case slt:
					case sll:
					case srl:
					case sra:

					// if(match == add || match == sub || match == mul || match == div || match ==
					// and
					// || match == or || match == xor || match == slt || match == sll || match ==
					// srl || match == sra)
					{

						String opcode = Integer.toBinaryString(cur_ins.getSourceOperand1().getValue());
						// matching opcodes according to instructions
						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rs1 will be 5 bits
						for (int k = 0; k < opcode.length(); k++) {
							if (opcode.length() == 5 || k == 32) {
								// System.out.println(opcode);
							}
						}
						command = command + opcode;
						// for rs1 bits here.

						// opcode = Integer.toBinaryString(cur_ins.operationType.ordinal());
						// while(opcode.length() != 5) opcode = "0" + opcode;
						// rs1 = Integer.toBinaryString(cur_ins.sourceOperand1.value);
						// while(rs1.length() != 5) rs1 = "0" + rs1;

						opcode = Integer.toBinaryString(cur_ins.getSourceOperand2().getValue());
						// matching opcode for rs2
						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rs2 will be 5 bits
						command = command + opcode; // rs2 bits
						for (int k = 0; k < opcode.length(); k++) {
							if (opcode.length() == 5 || k == 32) {
								// System.out.println(opcode);
							}
						}

						opcode = Integer.toBinaryString(cur_ins.getDestinationOperand().getValue());
						// matching for rd
						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rd will be 5 bits
						command = command + opcode; // rd bits

						command += "000000000000"; // unused bits in machine code is set of all zeros :)
						break;
					}
					// R2I type
					// various instructions like addi, subi which needs rd, rs1 and immediate are
					// compared here.
					case addi:
					case subi:
					case muli:
					case divi:
					case andi:
					case ori:
					case xori:
					case slti:
					case slli:
					case srli:
					case srai:
					case load:
					case store:

					// else if(match == addi || match == subi || match == muli || match == divi ||
					// match == andi || match == ori || match == xori || match == slti || match ==
					// slli || match == srli || match == srai || match == load || match == store)
					{
						String opcode = Integer.toBinaryString(cur_ins.getSourceOperand1().getValue());
						// matching for rs1
						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rs1 will be 5 bits
						command = command + opcode; // rs1 bits

						// opcode = Integer.toBinaryString(cur_ins.operationType.ordinal());
						// while(opcode.length() != 5) opcode = "0" + opcode;
						// rs1 = Integer.toBinaryString(cur_ins.sourceOperand1.value);
						// while(rs1.length() != 5) rs1 = "0" + rs1;

						opcode = Integer.toBinaryString(cur_ins.getDestinationOperand().getValue());
						// matching for rs2

						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rd will be 5 bits
						command = command + opcode; // rd bits
						for (int k = 0; k < opcode.length(); k++) {
							if (opcode.length() == 5 || k == 32) {
								// System.out.println(opcode);
							}
						}

						if (cur_ins.getSourceOperand2().operandType == OperandType.Immediate) { // in immediate.
							opcode = Integer.toBinaryString(cur_ins.getSourceOperand2().getValue());
							opcode = String.format("%17s", opcode).replaceAll(" ", "0");
							command = command + opcode; // absolute immediate
						} else { // not in immediate.
							opcode = Integer.toBinaryString(
									ParsedProgram.symtab.get(cur_ins.getSourceOperand2().getLabelValue()));
							opcode = String.format("%17s", opcode).replaceAll(" ", "0");
							command = command + opcode;
							for (int k = 0; k < opcode.length(); k++) {
								if (opcode.length() == 5 || k == 32) {
									// System.out.println(opcode);
								}
							}
						}
						break;
					}

					case beq:
					case bne:
					case blt:
					case bgt:
					// else if(match == beq || match == bne || match == blt || match == bgt)
					{
						String opcode = Integer.toBinaryString(cur_ins.getSourceOperand1().getValue());
						// matching for rs1
						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rs1 will be 5 bits
						command = command + opcode; // rs1 bits

						// opcode = Integer.toBinaryString(cur_ins.operationType.ordinal());
						// while(opcode.length() != 5) opcode = "0" + opcode;
						// rs1 = Integer.toBinaryString(cur_ins.sourceOperand1.value);
						// while(rs1.length() != 5) rs1 = "0" + rs1;

						opcode = Integer.toBinaryString(cur_ins.getSourceOperand2().getValue());
						// matching opcode for rd
						opcode = String.format("%5s", opcode).replaceAll(" ", "0");
						// rd will be 5 bits
						command = command + opcode; // rd bits
						for (int k = 0; k < opcode.length(); k++) {
							if (opcode.length() == 5 || k == 32) {
								// System.out.println(opcode);
							}
						}

						if (cur_ins.getDestinationOperand().operandType == OperandType.Immediate) {
							// In immediate
							opcode = Integer.toBinaryString(cur_ins.getDestinationOperand().getValue());
							if (opcode.length() > 17) {
								opcode = opcode.substring(opcode.length() - 17);
							} else
								opcode = String.format("%17s", opcode).replaceAll(" ", "0");
							command = command + opcode; // absolute immediate
							System.out.println(opcode);
						} else {
							// Not in immedidate
							opcode = Integer
									.toBinaryString(-cur_ins.getProgramCounter() + ParsedProgram.symtab
											.get(cur_ins.getDestinationOperand().getLabelValue()));
							if (opcode.length() > 17) {
								opcode = opcode.substring(opcode.length() - 17);
							} else
								opcode = String.format("%17s", opcode).replaceAll(" ", "0");
							command = command + opcode; // label
						}

						break;
					}
					// type RI -->
					case jmp:
					// else if(match == jmp)
					{
						if (cur_ins.getDestinationOperand().operandType == OperandType.Register) {
							String opcode = Integer
									.toBinaryString(cur_ins.getDestinationOperand().getValue());
							// matching opcodes according to rd
							opcode = String.format("%5s", opcode).replaceAll(" ", "0");
							// rd will be 5 bits
							command = command + opcode; // rd bits
							for (int k = 0; k < opcode.length(); k++) {
								if (opcode.length() == 5 || k == 32) {
									// System.out.println(opcode);
								}
							}

							command += "0000000000000000000000";
						} else if (cur_ins.getDestinationOperand().operandType == OperandType.Label) {
							command += "00000";
							String opcode = Integer
									.toBinaryString(-cur_ins.getProgramCounter() + ParsedProgram.symtab
											.get(cur_ins.getDestinationOperand().getLabelValue()));
							if (opcode.length() > 22) {
								opcode = opcode.substring(opcode.length() - 22);
							} else
								opcode = String.format("%22s", opcode).replaceAll(" ", "0");
							command = command + opcode; // imm
						} else if (cur_ins
								.getDestinationOperand().operandType == OperandType.Immediate) {
							command += "00000";

							String opcode = Integer
									.toBinaryString(cur_ins.getDestinationOperand().getValue());
							if (opcode.length() > 22) {
								opcode = opcode.substring(opcode.length() - 22);
							} else
								opcode = String.format("%22s", opcode).replaceAll(" ", "0");
							command = command + opcode; // label
							for (int k = 0; k < opcode.length(); k++) {
								if (opcode.length() == 5 || k == 32) {
									// System.out.println(opcode);
								}
							}
						}
						break;
					}

					case end:
					// else if(match == end)
					{
						command += "000000000000000000000000000";
						break;
					}
				}

				int foo = new BigInteger(command, 2).intValue();
				System.out.println(command);
				dos.writeInt(foo);
			}
			// 5. close the file
			dos.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}