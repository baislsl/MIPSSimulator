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
// design for "B offset" such kind of I operation
public abstract class FourthIFormatOperation extends IFormatOperation implements toAssemble, toBitString {
    protected FourthIFormatOperation(){super();}
    protected FourthIFormatOperation(opType op){
        super(op);
    }
    protected FourthIFormatOperation(opType op, Register rs, Register rt){
        super(op);
        this.rs = rs;
        this.rt = rt;
    }

    public String toAssembleLanguage(){
        return name() + " 0x" + imm16.toHexString();
    }

    public void build(String code) throws myParserException {
        code = code.trim();
        int blankIndex;
        for(blankIndex=0;blankIndex<code.length();blankIndex++){
            if(code.charAt(blankIndex) == ' ')
                break;
        }
        String[] registerLists = CodeParser.DivideList(code.substring(blankIndex).trim());
        if(registerLists.length != 1)
            throw new myParserException();
        try{
            imm16 = new Imm_16(CodeParser.myParserInt(registerLists[0]));
        }catch (NumberFormatException e){
            throw new myParserException(e);
        }
    }

}
