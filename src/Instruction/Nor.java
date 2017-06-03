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
public class Nor extends SpecialRFormatOperation implements Conduct {
    public Nor(){
        super(new Shamt(0b00000), new Funct(0b100111));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);

        storage.writeRegister(rd, ALU.nor(rsValue, rtValue));
        storage.addPC();
    }

    public String name(){
        return "nor";
    }
}
