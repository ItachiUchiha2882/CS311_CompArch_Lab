package processor.pipeline;

public class EX_IF_LatchType {
	public
	boolean IF_enable;
	boolean isbt;
	// boolean isBranchTaken;
	
	Integer offset=0;
	// int branchPC;
	
	public EX_IF_LatchType()
	{
		IF_enable=false;
		// isBranchTaken=false;
	}

	// public int getBranchPC() {
	// 	return this.branchPC;
	// }

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean if_enable) {
		IF_enable = if_enable;
	}
	public void setbt(boolean isbrt,Integer bt) {
		this.isbt=isbrt;
		if(isbrt==true) {
			offset=bt;
		}
		else
		{	this.isbt=false;
			offset=0;
		}
	}

	// public void setBranchPC(int branchPC) {
	// 	this.branchPC = branchPC;
	// }

	// public boolean getIsBranchTaken() {
	// 	return this.isBranchTaken;
	// }

	// public void setIsBranchTaken(boolean isBranchTaken) {
	// 	this.isBranchTaken = isBranchTaken;
	// }


}