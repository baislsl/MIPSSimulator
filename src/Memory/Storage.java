package Memory;

import BitArea.StdUnit;
import Disassembler.Disassembler;
import MIPSException.DisassembleException;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by baislsl on 17-4-14.
 */

/**
 * memory : 1 StdUnit = 4 bytes
 * User Text Segment [00400000]..[00440000] -> memory[0 -> 0x10000]
 * User data segment [10000000]..[10040000] -> memory[0x10000 -> 0x20000]
 * User Stack [7ffff5ec]..[80000000]        -> memory[0x20000 -> 0x21000]
 * */
public class Storage {
    private final static int memoryLength = 0x21000;
    public final static int dataAddress = 0x10000;
    public final static int textAddress = 0x0;
    public final static int stackAddress = 0x20000;
    public final static int testLength = 0x10000;
    public final static int dataLength = 0x10000;
    public final static int stackLength = 0x01000;
    private final static int DEFAULT_SHOW_LENGTH = 0x20;
    public final Scanner input = new Scanner(System.in);
    public final PrintWriter output = new PrintWriter(System.out);
    private StdUnit[] memory;  // 内存
    private StdUnit[] registers = new StdUnit[32];
    private StdUnit[] specialRegisters = new StdUnit[2];
    private int PC = 0x0;

    public Storage(){
        memory = new StdUnit[memoryLength];
        for(int i=0;i<memoryLength;i++){
            memory[i] = new StdUnit();
        }
        for(int i=0;i<32;i++){
            registers[i] = new StdUnit();
        }
        specialRegisters[0] = new StdUnit();
        specialRegisters[1] = new StdUnit();

    }

    public StdUnit readRegister(Register register){
        return registers[register.ordinal()];
    }

    public void writeRegister(Register register, StdUnit value){
        registers[register.ordinal()] = value;
    }

    public String showRegister(){
        String ans = "";
        for(int i=0;i<32;i++){
            ans += Register.of(i).toString() + " : " + registers[i].toHexString() +"\n";
        }
        return ans;
    }

    public void writeSpecailRegister(SpecialRegister register, StdUnit value){
        registers[register.ordinal()] = value;
    }

    public String showSpecailRegister(){
        String ans = "";
        for(int i=0;i<2;i++){
            ans += SpecialRegister.of(i).name() + " : " + specialRegisters[i].toBitString() +"\n";
        }
        return ans;
    }

    public StdUnit readMemory(int address) throws ArrayIndexOutOfBoundsException{
        return memory[address];
    }

    public void writeMemory(int address, StdUnit value)throws ArrayIndexOutOfBoundsException{
        memory[address]= value;
    }

    public void writeByteMemory(int address, int byt){
        int address_4 = address >> 2;
        int offset = (~(address & 3)) << 3;
        StdUnit mem = memory[address_4];
        int num = mem.getValue();
        int base = 0xff << offset;
        num = ((num ^ base)&num) | ((byt & 0xff)) << offset;
        memory[address_4] = new StdUnit(num);
    }

    public void setPC(int PC){
        this.PC = PC;
    }

    public void movePC(int offset){
        this.PC += offset;
    }

    public int getPC(){
        return PC;
    }

    public void addPC(){
        PC += 1;
    }

    public String showMemory(int from, int end){
        String ans = "";
        for(int i=from;i<end;i++){//+ " : " + memory[i].toCharString()
            ans += toMachineAddress(i) + " : " + memory[i].toHexString(8) + "\n";

        }
        return ans;
    }

    public String showMemory(){
        return showMemory(0, DEFAULT_SHOW_LENGTH);
    }

    public String showUserText(){
        return showUserText(DEFAULT_SHOW_LENGTH);
    }

    public String showUserData(){
        return showUserData(DEFAULT_SHOW_LENGTH);
    }

    public String showUserText(int length){
        String ans = "", operation;
        for(int i=textAddress;i<textAddress + length;i++){
            ans += toMachineAddress(i) + " : " + memory[i].toHexString(8);
            try {
                operation = Disassembler.disassembler(memory[i]).toAssembleLanguage();
            }catch (DisassembleException e){
                operation = "undefined";
            }
            ans += "    " + operation + "\n";
        }
        return ans;
    }

    public String showUserData(int length){
        String ans = "";
        for(int i=dataAddress;i<dataAddress + length;i++){
            ans += toMachineAddress(i) + " : " + memory[i].toHexString(8)
                    +" -- " + memory[i].toCharString() + "\n";

        }
        return ans;
    }

    public String toMachineAddress(int address){
        if(address < 0x10000){          // text address
            String ans = Integer.toHexString(address<<2);
            for(int i=0,k=ans.length(); i < 5 - k;i++)
                ans = "0" + ans;
            return "0x004" + ans;
        }else if(address < 0x20000){    //data address
            String ans = Integer.toHexString((address - 0x10000)<<2);
            for(int i=0,k=ans.length(); i < 5 - k;i++)
                ans = "0" + ans;
            return "0x1000" + ans;
        }else{
            return "not defined yet";
        }
    }
}


