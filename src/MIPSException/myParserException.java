package MIPSException;

/**
 * Created by baislsl on 17-4-14.
 */
public class myParserException extends Exception {

    public myParserException(){}

    public myParserException(String info){
        super(info);
    }

    public myParserException(String info, Throwable cause){
        super(info, cause);
    }

    public myParserException(Throwable cause){
        super(cause);
    }

}
