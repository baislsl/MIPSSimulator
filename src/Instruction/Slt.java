package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import Memory.Storage;
import OperationSet.SpecialRFormatOperation;

/**
 * Created by baislsl on 17-4-20.
 */
public class Slt extends SpecialRFormatOperation implements Conduct {
    public Slt(){
        super(new Shamt(0b0000), new Funct(0b101010));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs),
                rtValue = storage.readRegister(rt);
        if(rsValue.getValue() < rtValue.getValue())
            storage.writeRegister(rd, new StdUnit(1));
        else
            storage.writeRegister(rd, new StdUnit(0));
        storage.addPC();
    }

    public String name(){
        return "slt";
    }
}
