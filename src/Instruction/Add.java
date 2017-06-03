package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import Memory.ALU;
import Memory.Storage;
import OperationSet.SpecialRFormatOperation;

/**
 * Created by baislsl on 17-4-18.
 */
public class Add extends SpecialRFormatOperation implements Conduct{
    public Add(){
        super(new Shamt(0), new Funct(0b100000));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);
        storage.writeRegister(rd, ALU.add(rsValue, rtValue));
        storage.addPC();
    }

    public String name(){
        return "add";
    }
}
