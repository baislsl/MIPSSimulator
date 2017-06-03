package BitArea;

import OperationSet.Operation;

/**
 * Created by baislsl on 17-4-15.
 */
public class StdUnit extends BitType {

    public StdUnit(){
        super();
        this.length = 32;
    }

    public StdUnit(int num) {
        super(num);
        this.length = 32;
    }

    public StdUnit(String string){
        super(string);
    }

}
