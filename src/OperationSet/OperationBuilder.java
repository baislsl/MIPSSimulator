package OperationSet;


import Instruction.*;

/**
 * Created by baislsl on 17-4-18.
 */
public enum OperationBuilder {
    ADD(new Add()),
    ADDI(new Addi()),
    AND(new And()),
    ANDI(new Andi()),
    B(new B()),
    BEQ(new Beq()),
    BGEZ(new Bgez()),
    BNE(new Bne()),
    DIV(new Div()),
    J(new J()),
    JR(new Jr()),
    LUI(new Lui()),
    LW(new Lw()),
    NOR(new Nor()),
    OR(new Or()),
    ORI(new Ori()),
    MUL(new Mul()),
    SLT(new Slt()),
    SLTI(new Slti()),
    SYSCALL(new Syscall()),
    SUB(new Sub()),
    SW(new Sw()),
    XOR(new Xor()),
    XORI(new Xori());


    OperationBuilder(Operation operation){
        this.operation = operation;
    }

    private Operation operation;

    public Operation getBuilder(){
        return this.operation;
    }
}
