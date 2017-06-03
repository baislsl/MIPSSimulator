package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import Memory.ALU;
import Memory.Storage;
import OperationSet.SpecialRFormatOperation;

/**
 * Created by baislsl on 17-4-20.
 */
public class Or extends SpecialRFormatOperation implements Conduct {
    public Or(){
        super(new Shamt(0b00000), new Funct(0b100101));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);

        storage.writeRegister(rd, ALU.or(rsValue, rtValue));
        storage.addPC();
    }

    public String name(){
        return "or";
    }

}
