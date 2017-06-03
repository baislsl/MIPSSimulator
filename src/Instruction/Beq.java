package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.ALU;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Beq extends CommonIFormatOperation implements Conduct {
    public Beq(){
        super(new opType(0b000100));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);
        if(rsValue.getValue() == rtValue.getValue()){
            storage.movePC(imm16.getValue());
        }
        storage.addPC();

    }

    public String name(){
        return "beq";
    }
}
