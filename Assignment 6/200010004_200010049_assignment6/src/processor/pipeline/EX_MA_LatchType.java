package processor.pipeline;

// import generic.Instruction;

public class EX_MA_LatchType {
	
	// Instruction instruction;
	// int Final_Result;
	int ALUResult;
	int op1;
	int rd;
	// int instruction;
	boolean MA_enable;
	boolean MA_busy;
	int opcode;
	int reg2;
	int remainder;

	// Integer alures=0, op2=0;
	// int alu_result;
	// boolean isld=false, isst=false, isrw=true, isend=false;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		MA_busy = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	// public void setop2(int operand){
	// 	op2 = operand;
	// }

	public void setMA_busy(boolean ma_busy){
		MA_busy=ma_busy;
	}

	public boolean isMA_busy() {
		return MA_busy;
	}
	
	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

	public int getALUResult() {
		return ALUResult;
	}
	
	public void setALUResult(int x) {
		this.ALUResult = x;
	}
	// public void setFinal_Result(int x){
	// 	Final_Result = x;
	// }

	public int getop1() {
		return op1;
	}

	public void setop1(int OP1) {
		this.op1 = OP1;
	}

	public int getrd(){
		return rd;
	}

	public void setrd(int r){
		this.rd = r;
	}

	public int getOpcode() {
		return opcode;
	}

	// public int getFinal_Result(){
	// 	return Final_Result;
	// }

	// public int getop2(){
	// 	return op2;
	// }

	// public int getInstruction() {
	// 	return instruction;
	// }

	// public void setInstruction(int instruction) {
	// 	this.instruction = instruction;
	// }
	
	public void setOpcode(int opc) {
		this.opcode = opc;
	}

	public int getreg2() {
		return reg2;
	}

	public void setreg2(int Reg2) {
		this.reg2 = Reg2;
	}

	public int getremainder() {
		return remainder;
	}

	public void setremainder(int rem) {
		this.remainder = rem;
	}

	// this is code of assign3 
	// public boolean getisld() {
	// 	return this.isld;
	// }
	// public boolean getisst() {
	// 	return this.isst;
	// }
	// public boolean getisrw() {
	// 	return this.isrw;
	// }
	// public int getalures() {
	// 	return this.alures;
	// }
	// public void setop2(Integer op2) {
	// 	this.op2=op2;
	// }
	// public boolean getisend() {
	// 	return this.isend;
	// }
	// public void setisend(boolean op2) {
	// 	this.isend=op2;
	// }

	// public Instruction getInstruction() {
	// 	return instruction;
	// }

	// public void setInstruction(Instruction inst) {
	// 	instruction = inst;
	// }

	// public void setalures(Integer a) {
	// 	this.alures=a;
	// }
	// public void setis(boolean isld,boolean isst,boolean isrw) {
	// 	this.isst=isst;
	// 	this.isld=isld;
	// 	this.isrw=isrw;
	// }
}