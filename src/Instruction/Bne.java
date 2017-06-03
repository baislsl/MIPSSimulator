package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Bne extends CommonIFormatOperation implements Conduct {
    public Bne(){
        super(new opType(0b001100));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);
        if(rsValue.getValue() != rtValue.getValue()){
            storage.movePC(imm16.getValue());
        }
        storage.addPC();

    }

    public String name(){
        return "bne";
    }
}
