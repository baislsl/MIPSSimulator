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
public class Sub extends SpecialRFormatOperation implements Conduct {
    public Sub(){
        super(new Shamt(0b00000), new Funct(0b0100010));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs),
                rtValue = storage.readRegister(rt);
        storage.writeRegister(rd, ALU.sub(rsValue, rtValue));
        storage.addPC();
    }

    public String name(){
        return "sub";
    }
}
