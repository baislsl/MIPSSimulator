package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import BitArea.opType;
import Memory.ALU;
import Memory.Storage;
import OperationSet.SpecialRFormatOperation;

/**
 * Created by baislsl on 17-4-21.
 */
public class Mul extends SpecialRFormatOperation implements Conduct {
    public Mul(){
        super(new opType(0b011100),new Shamt(0b00000), new Funct(0b100000));
    }

    public void conduct(Storage storage){
        StdUnit rsValue = storage.readRegister(rs);
        StdUnit rtValue = storage.readRegister(rt);
        storage.writeRegister(rd, new StdUnit(rsValue.getValue()*rtValue.getValue()));
        storage.addPC();
    }

    public String name(){
        return "mul";
    }
}
