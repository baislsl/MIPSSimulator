package Memory;

import BitArea.Imm_16;
import BitArea.StdUnit;

/**
 * Created by baislsl on 17-4-19.
 */
public class ALU {
    public static StdUnit and(StdUnit a, StdUnit b){
        return new StdUnit(a.getValue() & b.getValue());
    }

    public static StdUnit add(StdUnit a, StdUnit b){
        return new StdUnit (a.getValue() + b.getValue());
    }

    public static StdUnit sub(StdUnit a, StdUnit b){
        return new StdUnit(a.getValue() - b.getValue());
    }

    public static StdUnit nor(StdUnit a, StdUnit b){
        return new StdUnit(~(a.getValue() | b.getValue()));
    }

    public static StdUnit or(StdUnit a, StdUnit b){
        return new StdUnit(a.getValue() | b.getValue());
    }

    public static StdUnit xor(StdUnit a, StdUnit b){
        return new StdUnit(a.getValue() ^ b.getValue());
    }

}
