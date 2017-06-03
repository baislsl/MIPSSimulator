package OperationSet;

import MIPSException.myParserException;
import Memory.Storage;
import BitArea.*;

/**
 * Created by baislsl on 17-4-17.
 */
public abstract class Operation {
    opType op;
    Operation(){}
    Operation(opType op){
        this();
        this.op = op;
    }

    public abstract void conduct(Storage storage);
    public abstract String toAssembleLanguage();
    public abstract String toBitString();
    public abstract String name();
    public abstract void build(String code) throws myParserException;
    public abstract void build(StdUnit bitset) throws myParserException;



}
