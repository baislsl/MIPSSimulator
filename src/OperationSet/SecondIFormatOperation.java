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
//LW rt, offset(base)
// design for "lw, $t0, (imm16)$t1" such kind of instruction
public abstract class SecondIFormatOperation extends IFormatOperation implements toAssemble, toBitString {

    protected SecondIFormatOperation(){super();}
    protected SecondIFormatOperation(opType op){
        super(op);
    }

    public String toAssembleLanguage(){
        return name() + " " + rt.toString() + ", " + imm16.toHexString()  + "(" + rs.toString() + ")" ;
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
        String[] div = CodeParser.parenthesisDivider(registerLists[1]);
        if(div == null)
            throw new myParserException();
        try{
            imm16 = new Imm_16(CodeParser.myParserInt(div[0]));
        }catch (NumberFormatException e){
            throw new myParserException(e);
        }
        this.rs = CodeParser.castRegister(div[1]);
    }

}