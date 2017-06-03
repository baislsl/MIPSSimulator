package Disassembler;

import BitArea.StdUnit;
import MIPSException.COEParserException;
import MIPSException.DisassembleException;
import OperationSet.Operation;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by baislsl on 17-5-14.
 */
public class CoeHandler {
    private final static String ignore = "\\s*;(.*)";
    private final static String radix = "(\\s*)memory_initialization_radix(\\s*)=(\\s*)(\\d+);(.*)";
    private final static String vector = "(\\s*)memory_initialization_vector(\\s*)=(\\s*)(.*)";
    private final static String div = "(\\s*),(\\s*)";
    private final static Pattern ignorePtn = Pattern.compile(ignore, Pattern.CASE_INSENSITIVE);
    private final static Pattern radixPtn = Pattern.compile(radix, Pattern.CASE_INSENSITIVE);
    private final static Pattern vectorPtn = Pattern.compile(vector, Pattern.CASE_INSENSITIVE);
    private final static Pattern divPtn = Pattern.compile(div);

    private static String readNumber(String number, int radix){
        StringBuilder ans = new StringBuilder();
        int num = Integer.parseUnsignedInt(number, radix);
        StdUnit machineCode = new StdUnit(num);
        try{
            Operation operation = Disassembler.disassembler(machineCode);
            ans.append(operation.toAssembleLanguage());
        }catch (DisassembleException e){
            ans.append(".word ");
            ans.append(machineCode.toHexString());
        }
        ans.append("\n");
        return ans.toString();
    }

    public static InputStream coeReader(InputStream input) throws COEParserException{
        Scanner in = new Scanner(input);
        int flag = 0, radix = 10;
        StringBuilder ans = new StringBuilder();
        while(in.hasNextLine()){
            String line = in.nextLine().trim();
            Matcher ignoreMatcher = ignorePtn.matcher(line);
            if(ignoreMatcher.matches()) continue;
            switch (flag){
                case 0 :
                    Matcher radixMatcher = radixPtn.matcher(line);
                    if(radixMatcher.matches()){
                        flag = 1;
                        int begin = line.indexOf('=') + 1;
                        int end = begin + 1;
                        while(end < line.length() && line.charAt(end) > ' ' && line.charAt(end) != ';')
                            end++;
                        radix = Integer.parseInt(line.substring(begin, end));
                    }else{
                        throw new COEParserException(line);
                    }
                    break;
                case 1:
                    Matcher vecMatcher = vectorPtn.matcher(line);
                    if(vecMatcher.matches()) {
                       flag = 2;
                    }else{
                        throw new COEParserException(line);
                    }
                    break;
                case 2:    // flag = 2;
                    String[] numbers = divPtn.split(line);
                    for(int i=0;i<numbers.length - 1;i++){
                       ans.append(readNumber(numbers[i], radix));
                    }
                    String last = numbers[numbers.length - 1];
                    if(last.charAt(last.length() - 1) == ';'){
                        flag = 3;
                        ans.append(readNumber(last.substring(0, last.length() - 1), radix));
                    }else{
                        ans.append(readNumber(last, radix));
                    }
                    break;
                default:    //flag = 3
                    throw new COEParserException(line);

            }
        }
        return new ByteArrayInputStream(ans.toString().getBytes());
    }
}
