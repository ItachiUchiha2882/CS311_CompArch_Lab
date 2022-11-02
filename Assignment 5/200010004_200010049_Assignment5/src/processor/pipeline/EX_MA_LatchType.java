package processor.pipeline;

// import generic.Instruction;

public class EX_MA_LatchType {
	
	// Instruction instruction;
	int Final_Result;
	int op2;
	int rd;
	int instruction;
	boolean MA_enable;
	boolean MA_busy;

	// Integer alures=0, op2=0;
	// int alu_result;
	// boolean isld=false, isst=false, isrw=true, isend=false;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setop2(int operand){
		op2 = operand;
	}

	public void setMA_busy(boolean ma_busy){
		MA_busy=ma_busy;
	}

	public boolean isMA_busy() {
		return MA_busy;
	}
	
	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	
	public void setFinal_Result(int x){
		Final_Result = x;
	}

	public void setrd(int r){
		this.rd = r;
	}

	public int getrd(){
		return this.rd;
	}

	public int getFinal_Result(){
		return Final_Result;
	}

	public int getop2(){
		return op2;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
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