package Disassembler;

import BitArea.StdUnit;
import MIPSException.*;
import OperationSet.Operation;
import OperationSet.OperationBuilder;

/**
 * Created by baislsl on 17-4-26.
 */
public class Disassembler {

    public static Operation disassembler(StdUnit bits) throws DisassembleException {
        Operation result;
        int op = bits.catchNumber(0,6);
        String name = codeToOperation[op];

        if(name == null){
            if(op == 0){
                int func = bits.catchNumber(26);
                name = functToOperation[func];
                if(name == null)
                    throw new DisassembleException("undefined funct code of : 0x" + Integer.toHexString(func));
            }else{
                throw new DisassembleException("unfinished part!");
            }
        }

        try{
            result = Enum.valueOf(OperationBuilder.class, name.toUpperCase()).getBuilder();
        }catch (IllegalArgumentException e){
            throw new DisassembleException(e);
        }
        try{
            result.build(bits);
        }catch (myParserException e){
            throw new DisassembleException(e);
        }
        return result;
    }

    private static String[] codeToOperation = { //op[31:26]
            null,   //x00
            null,
            "j",
            "jal",
            "beq",
            "bne",
            "blez",
            "bgtz",
            "addi",
            "addiu",
            "slti",
            "sltiu",
            "andi",
            "ori",
            "xori",
            "lui",
            null,   //x10
            null,
            null,
            null,
            "beql",
            "bnel",
            "blezl",
            "bgtzl",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "lb",   //x20
            "lh",
            "lwl",
            "lw",
            "lbu",
            "lhu",
            "lwr",
            null,
            "sb",
            "sh",
            "swl",
            "sw",
            null,
            null,
            "swr",
            "cache",
            "ll",    //0x30
            "lwc1",
            "lwc2",
            "pref",
            null,
            "ldc1",
            "ldc2",
            null,
            "sc",
            "swc1",
            "swc2",
            null,
            null,
            "sdc1",
            "sdc2",
            null
    };

    private static String[] functToOperation = { // funct[5:0]
            "sll",  //0x00
            null,
            "srl",
            "sra",
            "sllv",
            null,
            "srlv",
            "srav",
            "jr",
            "jalr",
            "movz",
            "movn",
            "syscall",
            "break",
            null,
            "sync",
            "mfhi", //0x10
            "mthi",
            "mflo",
            "mtlo",
            null,
            null,
            null,
            null,
            "mult",
            "multu",
            "div",
            "divu",
            null,
            null,
            null,
            null,
            "add",  //0x20
            "addu",
            "sub",
            "subu",
            "and",
            "or",
            "xor",
            "nor",
            null,
            null,
            "slt",
            "sltu",
            null,
            null,
            null,
            null,
            "tge",  //0x30
            "tgeu",
            "tlt",
            "tltu",
            "teq",
            null,
            "tne",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
    };

    private static String[] funct2ToOperation = { //funct[5:0]
            "add.f",    //0x00
            "sub.f",
            "mul.f",
            "div.f",
            "sqrt.f",
            "abs.f",
            "mov.f",
            "neg.f",
            null,
            null,
            null,
            null,
            "round.w.f",
            "trunc.w.f",
            "cell.w.f",
            "floor.w.f",
            null,       //0x10
            null,
            "movz.f",
            "movn.f",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "cvt.s.f",  //0x20
            "cvt.d.f",
            null,
            null,
            "cvt.w.f",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "c.f.f",    //0x30
            "c.un.f",
            "c.eq.f",
            "c.ueq.f",
            "c.olt.f",
            "c.ult.f",
            "c.ole.f",
            "c.ule.f",
            "c.sf.f",
            "c.ngle.f",
            "c.seq.f",
            "c.ngl.f",
            "c.lt.f",
            "c.nge.f",
            "c.le.f",
            "c.ngt.f"
    };

}
