package OperationSet;

import BitArea.Imm_32;
import BitArea.StdUnit;
import BitArea.opType;
import MIPSException.myParserException;
import Parser.CodeParser;

/**
 * Created by baislsl on 17-4-19.
 */
public abstract class JFormatOperation extends Operation implements toAssemble, toBitString {
    protected Imm_32 imm32;
    protected JFormatOperation(opType op){
        super(op);
    }

    protected JFormatOperation(opType op, Imm_32 imm32){
        super(op);
        this.imm32 = imm32;
    }

    public void build(String code) throws myParserException{
        code = code.trim();
        int blankIndex;
        for(blankIndex=0;blankIndex<code.length();blankIndex++){
            if(code.charAt(blankIndex) == ' ')
                break;
        }
        try{
            int imm = CodeParser.myParserInt(code.substring(blankIndex + 1));
            imm32 = new Imm_32(CodeParser.signExtend(imm, 26));
        }catch (NumberFormatException e){
            throw new myParserException(e);
        }
    }

    public void build(StdUnit bitSet) throws myParserException {
        imm32 = new Imm_32(bitSet.catchNumber(6));
    }

    public String toBitString() {
        return op.toBitString() + imm32.toBitString(26);
    }

    public String toAssembleLanguage(){
        return name() + " " + imm32.toHexString();
    }
}
