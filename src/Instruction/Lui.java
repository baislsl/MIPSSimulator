package Instruction;

import BitArea.StdUnit;
import BitArea.opType;
import Memory.Register;
import Memory.Storage;
import OperationSet.ThirdIFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Lui extends ThirdIFormatOperation implements Conduct {
    public Lui(){
        super(new opType(0b001111), Register.ZERO);
    }

    public void conduct(Storage storage){
        int value = imm16.getValue() << 16;
        storage.writeRegister(rt, new StdUnit(value));
        storage.addPC();
    }

    public String name(){
        return "lui";
    }
}
