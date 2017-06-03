package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.Register;
import Memory.Storage;
import OperationSet.Third2IFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Bgez extends Third2IFormatOperation implements Conduct {
    public Bgez(){
        super(new opType(0b001111), Register.ZERO);
    }

    public void conduct(Storage storage){
        StdUnit rtValue = storage.readRegister(rt);
        if(rtValue.getValue() >= 0){
            storage.movePC(imm16.getValue());
        }
        storage.addPC();

    }

    public String name(){
        return "bgez";
    }
}
