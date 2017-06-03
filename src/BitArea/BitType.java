package BitArea;

import Parser.CodeParser;

/**
 * Created by baislsl on 17-4-15.
 */
public abstract class BitType {
    int num;
    int length;

    BitType(){
        num = 0;
    }

    BitType(int num){
        this.num = num;
    }

    BitType(String num){
        this.num = CodeParser.myParserInt(num);
    }

    public void and(int o){
        num &= o;
    }

    public void and(BitType o){
        and(o.num);
    }

    public void or(int o){
        num |= o;
    }

    public void xor(int o){
        num ^= o;
    }

    public void inv(){
        num = ~num;
    }

    public void set(int num){
        this.num = num;
    }

    // begin -> end: 0->length [begin, end)
    public int catchNumber(int begin, int end){
        return (num>>(length - end)) & ((1<<(end - begin)) - 1);

    }

    public int catchNumber(int begin){
        return catchNumber(begin , this.length);
    }

    public int getValue(){
        if(this.length == 32)
            return this.num;
        else
            return this.num&(1<<this.length - 1);
    }

    @Override
    public String toString(){
        return toBitString(this.length);
    }

    public String toBitString(){
        return toBitString(this.length);
    }

    public String toBitString(int length){
        String ans = Integer.toBinaryString(num);
        if(ans.length() > length){
            ans = ans.substring(ans.length() - length);
        }else{
            for(int i=0, k =ans.length();i<length - k;i++)
                ans = "0" + ans;
        }
        return ans;
    }

    public String toHexString(int length){
        String ans = Integer.toHexString(num);
        for(int i=0, k=ans.length(); i < length - k;i++)
            ans = "0" + ans;
        return "0x" + ans;
    }

    public String toHexString(){
        return "0x" + Integer.toHexString(num);
    }

    public String toCharString(){
        String str = Integer.toHexString(num);
        if(str.length() % 2 == 1)
            str = "0" + str;
        StringBuilder ans = new StringBuilder();
        for(int begin = 0; begin < str.length() ;begin+=2){
            int cc = 16*charToInt(str.charAt(begin)) + charToInt(str.charAt(begin + 1));
            if(cc < ' ')
                cc = '.';
            ans.append((char)cc);
        }
        return ans.toString();
    }

    private int charToInt(char cc){
        if(cc >= '0' && cc <= '9')
            return cc - '0';
        else if(cc >= 'a' && cc <= 'f')
            return cc -'a' + 10;
        else if(cc >= 'A' && cc <= 'F')
            return cc - 'A'  + 10;
        else{
            return -1;
        }
    }


}
