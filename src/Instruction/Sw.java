package Instruction;

import BitArea.opType;
import Memory.Storage;
import OperationSet.SecondIFormatOperation;

/**
 * Created by baislsl on 17-4-20.
 */
public class Sw extends SecondIFormatOperation implements Conduct {
    public Sw(){
        super(new opType(0b101011));
    }

    public void conduct(Storage storage){
        int address = storage.readRegister(rs).getValue() + imm16.getValue();
        storage.writeMemory(address, storage.readRegister(rt));
        storage.addPC();
    }

    public String name(){
        return "sw";
    }

}
