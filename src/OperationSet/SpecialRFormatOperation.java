package OperationSet;

import MIPSException.myParserException;
import Memory.Register;
import Parser.CodeParser;
import BitArea.*;

/**
 * Created by baislsl on 17-4-17.
 */
// design for "add  $t5 ,$zero,$a0" kind
public abstract class SpecialRFormatOperation extends RFormatOperation implements toAssemble, toBitString {

    protected SpecialRFormatOperation(){
        super(new opType(0));
    }

    protected SpecialRFormatOperation(Shamt shamt, Funct funct){
        super(new opType(0) , shamt, funct);
    }

    protected SpecialRFormatOperation(opType op, Shamt shamt, Funct funct){
        super(op, shamt, funct);
    }

    protected SpecialRFormatOperation(Register rt, Register rd, Shamt shamt, Funct funct){
        super(new opType(0) , shamt, funct);
        this.rt = rt;
        this.rd = rd;
    }


    public String toAssembleLanguage(){
        return name() + " " + rd.toString() + ", " + rs.toString() + ", " + rt.toString();
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
            throw new myParserException("Not 3 register given!");
        this.rd = CodeParser.castRegister(registerLists[0]);
        this.rs = CodeParser.castRegister(registerLists[1]);
        this.rt = CodeParser.castRegister(registerLists[2]);
    }

}
