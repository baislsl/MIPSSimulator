package OperationSet;

import MIPSException.myParserException;
import Memory.Register;
import BitArea.*;

/**
 * Created by baislsl on 17-4-17.
 */
public abstract class RFormatOperation extends Operation implements toBitString{
    protected Register rs, rt, rd;
    protected Shamt shamt;
    protected Funct funct;

    RFormatOperation(){
        super();
    }
    RFormatOperation(opType op){
        super(op);
    }
    RFormatOperation(opType op, Shamt shamt, Funct funct){
        super(op);
        this.shamt = shamt;
        this.funct = funct;
    }

    public String toBitString(){
        return op.toBitString() + rs.toBitString() + rt.toBitString() + rd.toBitString()
                + shamt.toBitString() + funct.toBitString();
    }

    public void build(StdUnit bitSet) throws myParserException {
        Register[] list = Register.values();
        this.rs = list[bitSet.catchNumber(6, 11)];
        this.rt = list[bitSet.catchNumber(11,16)];
        this.rd = list[bitSet.catchNumber(16,21)];
    }

}
