package OperationSet;

import BitArea.Imm_16;
import BitArea.StdUnit;
import BitArea.opType;
import MIPSException.myParserException;
import Memory.Register;
import Parser.CodeParser;

/**
 * Created by baislsl on 17-4-19.
 */
public abstract class IFormatOperation extends Operation implements toBitString {
    protected Register rs, rt;
    protected Imm_16 imm16;

    IFormatOperation(){super();}
    IFormatOperation(opType op){
        super(op);
    }

    public String toBitString(){
        return op.toBitString() + rs.toBitString() + rt.toBitString()
                + imm16.toBitString();
    }
    public void build(StdUnit bitSet) throws myParserException {
        Register[] list = Register.values();
        this.rs = list[bitSet.catchNumber(6, 11)];
        this.rt = list[bitSet.catchNumber(11,16)];
        this.imm16 = new Imm_16(CodeParser.signExtend(bitSet.catchNumber(16,32), 16));
    }
}
