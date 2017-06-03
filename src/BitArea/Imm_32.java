package BitArea;

/**
 * Created by baislsl on 17-4-15.
 */
public class Imm_32 extends BitType {
    public  Imm_32(){
        super();
        this.length = 32;
    }

    public Imm_32(int num){
        super(num);
        this.length = 32;
    }
}
