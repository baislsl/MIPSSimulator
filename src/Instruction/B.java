package Instruction;

import BitArea.opType;
import Memory.Register;
import Memory.Storage;
import OperationSet.CommonIFormatOperation;
import OperationSet.FourthIFormatOperation;
import OperationSet.JFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class B extends FourthIFormatOperation implements Conduct {
    public B(){
        super(new opType(0b000100), Register.ZERO, Register.ZERO);
    }

    public void conduct(Storage storage){
        storage.movePC(imm16.getValue());
        storage.addPC();
    }

    public String name(){
        return "b";
    }
}
