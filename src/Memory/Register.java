package Memory;

/**
 * Created by baislsl on 17-4-14.
 */
public enum Register {
    //  page : A-24
    ZERO,
    AT,
    V0,
    V1,
    A0,
    A1,
    A2,
    A3,
    T0,
    T1,
    T2,
    T3,
    T4,
    T5,
    T6,
    T7,
    S0,
    S1,
    S2,
    S3,
    S4,
    S5,
    S6,
    S7,
    T8,
    T9,
    K0,
    K1,
    GP,
    SP,
    FP,
    RA;

    Register(){}

    public static Register of(int index){
        return values()[index];
    }

    @Override
    public String toString(){
        return "$" + name().toLowerCase();
    }


    public String toBitString(){
        String ans = Integer.toBinaryString(this.ordinal());
        for(int i=0,k=ans.length();i< 5 - k;i++){
            ans = "0" + ans;
        }
        return ans;
    }
}
