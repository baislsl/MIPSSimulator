package Instruction;

import BitArea.Funct;
import BitArea.Shamt;
import Memory.Register;
import Memory.Storage;
import OperationSet.FirstRFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class Jr extends FirstRFormatOperation implements Conduct {
    public Jr(){
        super(Register.ZERO, Register.ZERO, new Shamt(0), new Funct(0b001000));
    }

    public void conduct(Storage storage){
        storage.setPC(storage.readRegister(rs).getValue());
    }

    public String name(){
        return "jr";
    }
}
