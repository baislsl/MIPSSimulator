package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.ALU;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Andi extends CommonIFormatOperation implements Conduct{
    public Andi(){
        super(new opType(0b001100));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit imm16_32 = new StdUnit(imm16.getValue());
        storage.writeRegister(rt, ALU.and(rsValue, imm16_32));
        storage.addPC();
    }

    public String name(){
        return "andi";
    }
}
