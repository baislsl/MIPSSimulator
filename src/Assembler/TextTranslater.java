package Assembler;

import MIPSException.myParserException;
import Parser.CodeParser;

import java.util.Map;

/**
 * Created by baislsl on 17-4-21.
 */
public class TextTranslater {

    // if it is a legal code, return the instruction name, else return null
    public static String analysisLine(Map<String, Integer> labelMap ,String code, int codeNumbers) throws myParserException {
        int end;
        for(end=0;end<code.length();end++){   //move the comment in the code
            if(code.charAt(end) == '#')
                break;
        }
        code = code.substring(0,end).trim();
        if(code.length() == 0)
            return null;
        for(int i=0;i<code.length();i++){
            if(code.charAt(i) == ':'){
                buildLabel(labelMap, code.substring(0, i), codeNumbers);
                return analysisLine(labelMap, code.substring(i+1), codeNumbers);
            }
        }
        return code;
    }

    private static void buildLabel(Map<String, Integer> labelMap , String label, int address) throws myParserException{
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


