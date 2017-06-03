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

// design for "addi, $t0, $t1, imm16 " such kind of instruction
public abstract class CommonIFormatOperation extends IFormatOperation implements toAssemble, toBitString {

    protected CommonIFormatOperation(){super();}
    protected CommonIFormatOperation(opType op){
        super(op);
    }

    public String toAssembleLanguage(){
        return name() + " " + rt.toString() + ", " + rs.toString() + ", " + imm16.toHexString();
    }

    public void build(String code) throws myParserException {
        code = code.trim();
        int blankIndex;
        for(blankIndex=0;blankIndex<code.length();blankIndex++){
            if(code.charAt(blankIndex) == ' ')
                break;
        }
        String[] registerLists = CodeParser.DivideList(code.substring(blankIndex).trim());
        if(registerLists.length != 3)
            throw new myParserException(code + " : not 3 register given!");
        this.rt = CodeParser.castRegister(registerLists[0]);
        this.rs = CodeParser.castRegister(registerLists[1]);
        try{
            this.imm16 = new Imm_16(CodeParser.myParserInt(registerLists[2]));
        }catch (NumberFormatException e){
            throw new myParserException(e);
        }
    }



}
