package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.ALU;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;

/**
 * Created by baislsl on 17-4-20.
 */
public class Ori extends CommonIFormatOperation implements Conduct {
    public Ori(){
        super(new opType(0b001101));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit imm16_32 = new StdUnit(imm16.getValue());
        storage.writeRegister(rt, ALU.or(rsValue, imm16_32));
        storage.addPC();
    }

    public String name(){
        return "ori";
    }
}
