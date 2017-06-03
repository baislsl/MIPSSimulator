package Instruction;

import BitArea.StdUnit;
import Memory.Register;
import Memory.Storage;
import OperationSet.SpecailOperationn;

/**
 * Created by baislsl on 17-4-20.
 */
public class Syscall extends SpecailOperationn implements Conduct {
    public Syscall(){
        super(new StdUnit(0x0000000c));
    }

    public void conduct(Storage storage){
        StdUnit v0Value = storage.readRegister(Register.V0);
        switch(v0Value.getValue()){
            case 1:
                storage.output.print(storage.readRegister(Register.A0).getValue());
                break;
            case 4:
                default:
        }
    }

    public String name(){
        return "syscall";
    }
}
