package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.ALU;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;

/**
 * Created by baislsl on 17-4-20.
 */
public class Xori extends CommonIFormatOperation implements Conduct {
    public Xori(){
        super(new opType(0b001110));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit imm16_32 = new StdUnit(imm16.getValue());
        storage.writeRegister(rt, ALU.xor(rsValue, imm16_32));
        storage.addPC();
    }

    public String name(){
        return "xori";
    }
}
