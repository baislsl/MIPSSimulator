package test;

import Assembler.Translater;
import BitArea.StdUnit;
import Disassembler.CoeHandler;
import GUI.MainFrame;
import Instruction.Syscall;
import MIPSException.myParserException;
import Memory.Register;
import Memory.Storage;
import Instruction.And;
import OperationSet.Operation;
import Parser.CodeParser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by baislsl on 17-4-14.
 */
public class test {
    public static void main(String[] args){
        COETest();
    }

    public static void COETest(){
        try{
            InputStream in = CoeHandler.coeReader(new FileInputStream("./src/test/SCPU.coe"));
            Scanner input = new Scanner(in);
            while(input.hasNextLine())
                System.out.println(input.nextLine());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testParseInt(){
        System.out.println(CodeParser.myParserInt("0b001"));
        System.out.println(CodeParser.signExtend(0xffffffff, 32));
        System.out.println(Integer.parseInt("ffffffff", 2));
    }

    public static void frameTest(){
        MainFrame frame = new MainFrame();
        frame.start();
    }

    public static void translaterTest(){
        Storage storage = new Storage();
        Translater translater = new Translater(storage);
        try{
            translater.translate(Paths.get("./src/test/test.s"));
        }catch (myParserException e){
            e.printStackTrace();
        }
        // System.out.println(storage.showUserData(0x20));
        System.out.println(storage.showUserText());
    }

    public static void InstructionTest(){
        try{
            // And add = new And();
            // add.build(new OperationSet.StdUnit(0b00000001010010110001100000100100));
            // add.build("and  $v1  , $t2,$t3");
            // System.out.print(add.toAssembleLanguage() + ":" + add.toBitString());

            Operation op1 = CodeParser.parser("and  $v1  , $t2,$t3");
            Operation op2 = CodeParser.parser("add  $t5 ,$zero,$a0");
            Operation op3 = CodeParser.parser("addi $t5, $t4, 0xFa");
            Operation op4 = CodeParser.parser("lw $t5, 0b1111($t4)");
            System.out.println(op1.toAssembleLanguage() + ":" + op1.toBitString());
            System.out.println(op2.toAssembleLanguage() + ":" + op2.toBitString());
            System.out.println(op3.toAssembleLanguage() + ":" + op3.toBitString());
            System.out.println(op4.toAssembleLanguage() + ":" + op4.toBitString());

        }catch (myParserException e){
            e.printStackTrace();
        }catch(EnumConstantNotPresentException e){
            e.printStackTrace();
        }
    }

    public static void storageTest(){
        Storage storage = new Storage();
        // System.out.println(storage.showMemory(0, 0xfff));
        System.out.println(storage.showRegister());
        Scanner input = new Scanner(System.in);
        input.next();

    }


    public static void patternTest(){
        Pattern pattern  = Pattern.compile("\\s*,\\s*");

        String[] sp = pattern.split("add, xcixi , &p");

        for(String string : sp){
            System.out.println(string);
        }
    }

    public static void StdUnitTest(){
        StdUnit p = new StdUnit(0b01111111111111111111111111111111);
        StdUnit q = new StdUnit(0b111111111111111);
        //p.or(0b010101001);
    }


    public static void registerTest(){
        Register S0;
        System.out.println(Register.of(10));
    }
}
