package MIPSException;

/**
 * Created by baislsl on 17-4-26.
 */

public class DisassembleException extends Exception {

    public DisassembleException(){}

    public DisassembleException(String info){
        super(info);
    }

    public DisassembleException(String info, Throwable cause){
        super(info, cause);
    }

    public DisassembleException(Throwable cause){
        super(cause);
    }

}
