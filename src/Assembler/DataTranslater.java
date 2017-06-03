package Assembler;

import BitArea.StdUnit;
import Instruction.Syscall;
import MIPSException.myParserException;
import Memory.Storage;
import Parser.CodeParser;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by baislsl on 17-4-21.
 */
public class DataTranslater {

    public static int analysisLine(Storage storage, Map<String, Integer> labelMap, String code, int dataIndex) throws myParserException{
        int end;
        for(end=0;end<code.length();end++){
            if(code.charAt(end) == '#')
                break;
        }
        code = code.substring(0,end).trim();
        if(code.length() == 0)
            return 0;
        for(int i=0;i<code.length();i++){
            if(code.charAt(i) == ':'){
                buildLabel(labelMap, code.substring(0, i), dataIndex);
                return analysisLine(storage, labelMap, code.substring(i+1), dataIndex);
            }else if(code.charAt(i) == '\"'){
                while(++i < code.length() && code.charAt(i) != '\"');
            }else if(code.charAt(i) == '\''){
                while(++i < code.length() && code.charAt(i) != '\'');
            }
        }

        if(code.charAt(0) != '.')
            throw new myParserException("Not well define data type !");
        int blankIndex = code.indexOf(' ');
        if(blankIndex == -1)
            throw new myParserException("No data lists given");
        String dataType = code.substring(1, blankIndex);
        String[] paraList;

        /// can not use CodeParser.DividerList directly because in the string may contain ','
        if(dataType.equals("asciiz") || dataType.equals("ascii") || dataType.equals("byte"))
            paraList = divideList(code.substring(blankIndex + 1).trim());
        else
            paraList = CodeParser.DivideList(code.substring(blankIndex + 1).trim());
        return writeIntoMem(dataType, paraList, storage, dataIndex);
    }

    private static String[] divideList(String code) throws myParserException{
        if(code == "")
            return new String[0];
        ArrayList<String> result = new ArrayList<>();
        int begin = 0, cur = 0;
        while(true){
            char cc = code.charAt(cur);
            if(cc == ','){
                result.add(code.substring(begin, cur).trim());
                begin = cur + 1;
            }else if(cc == '\"'){
                while(++cur < code.length() && code.charAt(cur) != '\"');
                if(cur == code.length())
                    throw new myParserException("Error parameter lists given!\n");
            }else if(cc == '\''){
                while(++cur < code.length() && code.charAt(cur) != '\'');
                if(cur == code.length())
                    throw new myParserException("Error parameter lists given!\n");
            }
            if(++cur >= code.length()){
                if(cur != begin){
                    result.add(code.substring(begin, cur).trim());
                    break;
                }
            }
        }

        return result.toArray(new String[0]);
    }

    private  static int writeIntoMem(String dataType, String[] paraList, Storage storage, int dataIndex)
            throws myParserException{
        int writeInLength = 0; boolean stringEndNull = false;
        switch (dataType){
            case "word":
                for(String para : paraList){
                    int value = CodeParser.myParserInt(para);
                    storage.writeMemory((dataIndex + writeInLength)>>2, new StdUnit(value));
                    writeInLength += 4;
                }
                return writeInLength;
            case "byte":
                for(String para : paraList){
                    int value;
                    if(para.length() == 3 && para.charAt(0) == '\''
                            && para.charAt(2) == '\'')
                        value = para.charAt(1);
                    else
                        value = CodeParser.myParserInt(para);

                    storage.writeByteMemory(dataIndex + writeInLength++, value);
                }
                return writeInLength;
            case "space":
                if(paraList.length != 1)
                    throw new myParserException("space can only contain 1 parameter");
                int num = CodeParser.myParserInt(paraList[0]);
                while(num-- != 0){
                    storage.writeByteMemory(dataIndex + writeInLength++, ' ');
                }
                return writeInLength;
            case "asciiz":
                stringEndNull = true;
            case "ascii":
                for(String para : paraList){
                    if(para.charAt(0) != '\"' || para.charAt(para.length() - 1) != '\"')
                        throw new myParserException("Illegal string format !");
                    para = para.substring(1, para.length() - 1);
                    for(int i=0;i<para.length();i++){
                        storage.writeByteMemory(dataIndex + writeInLength++ , para.charAt(i));
                    }
                    if(stringEndNull){
                        storage.writeByteMemory(dataIndex + writeInLength++ , 0);
                    }
                }

                return writeInLength;
            default:
                throw new myParserException("Illegal data type : " + dataType);
        }
    }



    private static void buildLabel(Map<String, Integer> labelMap , String label, int address) throws myParserException {
        label = label.trim();
        if(!isLegalLabel(label))
            throw new myParserException("\"" + label + "\"" + "is not a legal variable!\n");
        else if(labelMap.containsKey(label))
            throw new myParserException("The program exist two or more same labels!\n");
        // System.out.println("build index of label : " + label + " in address : 0x" + Integer.toHexString(address));
        labelMap.put(label, address);
    }

    private static boolean isLegalLabel(String code){
        if(code.length() == 0)
            return false;
        if(Character.isDigit(code.charAt(0)))
            return false;
        for(int i=0;i<code.length();i++){
            char cc = code.charAt(i);
            if(!Character.isDigit(cc) && !Character.isLetter(cc) && cc != '_')
                return false;
        }
        return true;
    }
}
