package Assembler;

import BitArea.StdUnit;
import MIPSException.myParserException;
import Memory.Storage;
import OperationSet.Operation;
import Parser.CodeParser;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by baislsl on 17-4-19.
 */
public class Translater {
    private Storage storage;
    private int textAddress;
    private int dataAddress;
    private Map<String, Integer> labelMap = new HashMap<>();
    private ArrayList<Integer>  laList = new ArrayList<>();
    private final static String midPath = "./src/test/tmp.txt", mid2Path = "./src/test/tmp2.txt";

    public Translater(Storage storage){
        this.storage = storage;
        this.textAddress = storage.textAddress;
        this.dataAddress = storage.dataAddress;
    }

    public void translate(Path path) throws myParserException{
        buildMap(path, midPath); // write data in, and build a map ,
                                // finished the data area, new file do not contain the data text
        castLabel(midPath, mid2Path); // put the map into the code
        readInstruction(mid2Path);  // write the code into memory only the text area
    }

    // detect all true address of "xxx:" label and build a map
    private void buildMap(Path path, String midPath) throws myParserException{
        PrintWriter midfile;
        Scanner input;
        int textIndex = textAddress << 2;
        int dataIndex = dataAddress << 2; // !! data address may can not be divide by 4
        try{
            midfile = new PrintWriter(midPath);
            input = new Scanner(path);
        }catch (IOException e){
            throw new myParserException(e);
        }
        String line;
        boolean isData = false, isText = false;
        while(input.hasNextLine()){
            line = input.nextLine();
            line = line .trim();
            if(line.length() >= 5 && line.substring(0,5).equals(".data")){
                if(!canBeIgnored(line.substring(5)))
                    throw new myParserException(line);
                isData = true;
                isText = false;
            }else if(line.length() >= 5 && line.substring(0,5).equals(".text")){
                if(!canBeIgnored(line.substring(5)))
                    throw new myParserException(line);
                isText = true;
                isData = false;
            }else{
                if(isText){
                    String result = TextTranslater.analysisLine(labelMap, line, textIndex);
                    if(result != null){
                        String instruction = CodeParser.parserInstruction(result);
                        if(instruction.equals("li")){
                            int delete = 0;
                            while(result.charAt(delete) > ' ') delete++;
                            result = result.substring(delete + 1).trim();
                            String[] paraList = CodeParser.DivideList(result);
                            if(paraList.length != 2)
                                throw new myParserException("li instruction parameter lists error!");
                            int imm = CodeParser.myParserInt(paraList[1]);
                            midfile.write("lui " +  "$at, " + "0x" + Integer.toHexString(imm>>16) + "\n");
                            midfile.write("ori " + paraList[0] + ", $at, " + "0x" + Integer.toHexString(imm & 0x0000ffff) + "\n");
                            textIndex += 8;
                        }else if(instruction.equals("la")){
                            int delete = 0;
                            while(result.charAt(delete) > ' ') delete++;
                            result = result.substring(delete + 1).trim();
                            String[] paraList = CodeParser.DivideList(result);
                            if(paraList.length != 2)
                                throw new myParserException("li instruction parameter lists error!");
                            String[] div = CodeParser.parenthesisDivider(paraList[1]);
                            if(div == null){            // "la $a0, address" such type
                                midfile.write("lui " + "$at, " + paraList[1] + "\n");
                                midfile.write("ori " + paraList[0] + ", $at, " + paraList[1] + "\n");//直接换为地址即可, 解析时会自动去后4位
                                laList.add(textIndex);  // textIndex point to the ori instruction,
                                                        // whose imm need to shift right by 4
                                textIndex += 8;
                            }else{                      // "la $a0, 1($a1)" such type
                                midfile.write("addi " + paraList[0] + ", " + div[1] + ", " + div[0] + "\n");
                            }
                        }else{
                            midfile.write(result + "\n");
                            textIndex+=4;
                        }
                    }

                }else if(isData){
                    dataIndex += DataTranslater.analysisLine(storage, labelMap, line, dataIndex);
                }
            }

        }
        midfile.close(); input.close();
    }

    private void castLabel(String from , String to) throws myParserException {
        Scanner input;
        PrintWriter output;
        try{
            input = new Scanner(new File(from));
            output = new PrintWriter(new File(to));
        }catch (FileNotFoundException e){
            throw new myParserException(e);
        }
        int textIndex = textAddress << 2;
        while(input.hasNextLine()){
            StringBuilder line = new StringBuilder(input.nextLine());
            for(String key : labelMap.keySet()){
                int begin = -1;
                while(true){
                    begin = line.indexOf(key, begin + 1);
                    if(begin == -1)
                        break;
                    int end = begin + key.length() - 1;
                    if(begin >= 1 && (line.charAt(begin - 1) <= ' ' || line.charAt(begin - 1) ==',')
                            && (end < line.length() || line.charAt(end + 1) <= ' ' || line.charAt(end + 1) == ',')){
                        if(laList.indexOf(textIndex) == -1)
                            line.replace(begin, end+1, "0x" + Integer.toHexString(labelMap.get(key)));
                        else
                            line.replace(begin, end+1, "0x" + Integer.toHexString(labelMap.get(key) >> 16));
                    }

                }
            }
            output.write(line + "\n");
        }
        input.close(); output.close();
    }

    private void readInstruction(String mid2Path)throws myParserException{
        Scanner input;
        try {
            input = new Scanner(new File(mid2Path));
        }catch (FileNotFoundException e){
            throw new myParserException(e);
        }
        String code;
        int address = textAddress;
        while(input.hasNextLine()){
            code = input.nextLine();
            // System.out.println(code);
            Operation operation = CodeParser.parser(code);
            storage.writeMemory(address++ , new StdUnit("0b" + operation.toBitString()));
            // System.out.println(operation.toAssembleLanguage() + ":" + operation.toBitString());

        }
    }

    private static boolean canBeIgnored(String code){
        if(code == null) return true;
        for(int i=0;i<code.length();i++){
            if(code.charAt(i) == '#')
                return true;
            else if(code.charAt(i) > ' ')
                return false;
        }
        return true;
    }



}
