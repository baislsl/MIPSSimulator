package Instruction;

import BitArea.opType;
import Memory.Storage;
import OperationSet.SecondIFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Lw extends SecondIFormatOperation implements Conduct{
    public Lw(){
        super(new opType(0b100011));
    }

    public void conduct(Storage storage){
        int address = imm16.getValue() + storage.readRegister(rs).getValue();
        storage.writeRegister(rt, storage.readMemory(address));
        storage.addPC();
    }

    public String name(){
        return "lw";
    }

}
