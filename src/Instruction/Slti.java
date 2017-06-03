package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;

/**
 * Created by baislsl on 17-4-20.
 */
public class Slti extends CommonIFormatOperation implements Conduct {
    public Slti(){
        super(new opType(0b001010));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        if(rsValue.getValue() < imm16.getValue())
            storage.writeRegister(rt, new StdUnit(1));
        else
            storage.writeRegister(rt, new StdUnit(0));
        storage.addPC();
    }

    public String name(){
        return "slti";
    }
}
