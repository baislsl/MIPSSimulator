package OperationSet;

import BitArea.Imm_16;
import BitArea.StdUnit;
import BitArea.opType;
import MIPSException.myParserException;
import Memory.Register;
import Parser.CodeParser;

/**
 * Created by baislsl on 17-4-19.
 */

// design for "lui rt, immediate" such kind of instruction
public abstract class ThirdIFormatOperation extends IFormatOperation implements toAssemble, toBitString {
    protected ThirdIFormatOperation(){super();}
    protected ThirdIFormatOperation(opType op){
        super(op);
    }
    protected ThirdIFormatOperation(opType op, Register rs){
        super(op);
        this.rs = rs;
    }

    public String toAssembleLanguage(){
        return name() + " " + rt.toString() + ", " + imm16.toHexString();
    }

    public void build(String code) throws myParserException {
        code = code.trim();
        int blankIndex;
        for(blankIndex=0;blankIndex<code.length();blankIndex++){
            if(code.charAt(blankIndex) == ' ')
                break;
        }
        String[] registerLists = CodeParser.DivideList(code.substring(blankIndex).trim());
        if(registerLists.length != 2)
            throw new myParserException();

        this.rt = CodeParser.castRegister(registerLists[0]);
        // this.rs = Register.ZERO;
        try{
            imm16 = new Imm_16(CodeParser.myParserInt(registerLists[1]));
        }catch (NumberFormatException e){
            throw new myParserException(e);
        }
    }

}
