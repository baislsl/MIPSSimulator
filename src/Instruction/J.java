package Instruction;

import BitArea.opType;
import Memory.Storage;
import OperationSet.JFormatOperation;

/**
 * Created by baislsl on 17-4-19.
 */
public class J extends JFormatOperation implements Conduct{
    public J(){
        super(new opType(0b000010));
    }

    public void conduct(Storage storage){
        storage.setPC(imm32.getValue());
    }

    public String name(){
        return "j";
    }

}
