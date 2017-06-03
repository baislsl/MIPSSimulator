package Memory;

/**
 * Created by baislsl on 17-4-21.
 */
public enum SpecialRegister  {
    LO,
    HI;

    SpecialRegister(){}

    public static SpecialRegister of(int index){
        return values()[index];
    }

    @Override
    public String toString(){
        return "$" + name().toLowerCase();
    }

}
