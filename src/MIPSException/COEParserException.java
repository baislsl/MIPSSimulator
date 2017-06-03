package MIPSException;

/**
 * Created by baislsl on 17-5-14.
 */
public class COEParserException extends Exception {

    public COEParserException(){}

    public COEParserException(String info){
        super(info);
    }

    public COEParserException(String info, Throwable cause){
        super(info, cause);
    }

    public COEParserException(Throwable cause){
        super(cause);
    }

}
