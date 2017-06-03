package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import Memory.ALU;
import Memory.Storage;
import OperationSet.SpecialRFormatOperation;

/**
 * Created by baislsl on 17-4-17.
 */
public class And extends SpecialRFormatOperation implements Conduct {

    public And(){
        super(new Shamt(0), new Funct(0x24));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);
        storage.writeRegister(rd, ALU.and(rsValue, rtValue));
        storage.addPC();

    }

    public String name(){
        return "and";
    }



}
