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
public class Xor extends SpecialRFormatOperation implements Conduct {
    public Xor(){
        super(new Shamt(0b0000), new Funct(0b100110));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs),
                rtValue = storage.readRegister(rt);
        storage.writeRegister(rd, ALU.xor(rsValue, rtValue));
        storage.addPC();
    }

    public String name(){
        return "xor";
    }
}
