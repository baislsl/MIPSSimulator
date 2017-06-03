package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import Memory.Register;
import Memory.SpecialRegister;
import Memory.Storage;
import OperationSet.SecondRformatOperation;

/**
 * Created by baislsl on 17-4-21.
 */
public class Div extends SecondRformatOperation implements Conduct {
    public Div(){
        super(Register.ZERO, new Shamt(0b00000), new Funct(011010));
    }

    public void conduct(Storage storage){
        int rsValue = storage.readRegister(rs).getValue();
        int rtValue = storage.readRegister(rt).getValue();
        storage.writeSpecailRegister(SpecialRegister.LO, new StdUnit(rsValue/rtValue));
        storage.writeSpecailRegister(SpecialRegister.HI, new StdUnit(rsValue%rtValue));
    }

    public String name(){
        return "div";
    }

}
