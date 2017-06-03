package OperationSet;

import BitArea.Funct;
import BitArea.Shamt;
import BitArea.StdUnit;
import BitArea.opType;
import MIPSException.myParserException;
import Memory.Register;
import Parser.CodeParser;

/**
 * Created by baislsl on 17-4-21.
 */
public abstract class SecondRformatOperation extends RFormatOperation implements toAssemble, toBitString{

    protected SecondRformatOperation(){
        super(new opType(0));
    }

    protected SecondRformatOperation(Shamt shamt, Funct funct){
        super(new opType(0) , shamt, funct);
    }

    protected SecondRformatOperation(Register rd, Shamt shamt, Funct funct){
        super(new opType(0) , shamt, funct);
        this.rd = rd;
    }


    public String toAssembleLanguage(){
        return name() + " " + rs.toString();
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
            throw new myParserException("Not 3 register given!");
        this.rs = CodeParser.castRegister(registerLists[0]);
        this.rt = CodeParser.castRegister(registerLists[1]);
        this.rd = Register.ZERO;
    }

}
