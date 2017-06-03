package OperationSet;

import BitArea.StdUnit;
import MIPSException.myParserException;
import Memory.Storage;

/**
 * Created by baislsl on 17-4-20.
 */
// syscall
public abstract class SpecailOperationn extends Operation {
    private StdUnit stdUnit;
    protected SpecailOperationn(StdUnit stdUnit){
        super();
        this.stdUnit = stdUnit;
    }

    public abstract void conduct(Storage storage);
    public String toAssembleLanguage(){
        return name();
    }
    public String toBitString(){
        return stdUnit.toBitString();
    }
    public void build(String code) throws myParserException{
        // this.stdUnit = new StdUnit(Integer.parseInt(code));
    }
    public void build(StdUnit bitset) throws myParserException{
        this.stdUnit = bitset;
    }
}
