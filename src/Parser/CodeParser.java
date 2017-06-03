package Parser;

import MIPSException.myParserException;
import Memory.Register;
import OperationSet.Operation;
import OperationSet.OperationBuilder;

import java.util.regex.Pattern;

/**
 * Created by baislsl on 17-4-14.
 */

public class CodeParser {

    public static Operation parser(String code) throws myParserException {
        String op = parserInstruction(code).toUpperCase();
        Operation res = Enum.valueOf(OperationBuilder.class, op).getBuilder();
        res.build(code);
        return res;
    }

    // return the substring before the first blank
    public static String parserInstruction(String code) throws myParserException {
        code = code.trim();
        int blankIndex;
        for (blankIndex = 0; blankIndex < code.length(); blankIndex++) {
            if (code.charAt(blankIndex) <= ' ')
                break;
            else if (!Character.isLowerCase(code.charAt(blankIndex)))
                throw new myParserException("Instruction : " + code + "contain not lower case");
        }
        if (blankIndex == code.length())
            return code;
        return code.substring(0, blankIndex);
    }

    public static Register castRegister(String register) throws myParserException {
        if (register.charAt(0) != '$') throw new myParserException();
        for (int i = 1; i < register.length(); i++) {
            if (!Character.isLowerCase(register.charAt(i)) && !Character.isDigit(register.charAt(i)))
                throw new myParserException();
        }

        return Enum.valueOf(Register.class, register.substring(1).toUpperCase());
    }

    public static String[] DivideList(String code) {
        Pattern pattern = Pattern.compile("(\\s)*,(\\s)*");
        return pattern.split(code);
    }


    // test "20($v0)" form, if yes return {"20","$v0"} , else return null
    public static String[] parenthesisDivider(String string) {
        int left = 0, right = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '(')
                left = i;
            else if (string.charAt(i) == ')')
                right = i;
        }
        if (left != 0 && right == string.length() - 1 && left < right - 1)
            return new String[]{string.substring(0, left), string.substring(left + 1, right)};
        else
            return null;
    }

    public static int signExtend(int value, int length) {
        length = length - 1;
        if ((value & (1 << length)) != 0) { // negative value
            return value | ((~(1 << length)) + 1);
        } else {
            return value;
        }
    }

    // 0b00101, 0x234, 123...
    public static int myParserInt(String str) throws NumberFormatException {
        str = str.trim();
        if (str.length() <= 2)
            return Integer.parseInt(str);

        switch (str.substring(0, 2)) {
            case "0b":
            case "0B":
                if (str.charAt(3) == ' ')
                    throw new NumberFormatException("ilegal format of \'-\' added");
                return Integer.parseUnsignedInt(str.substring(2), 2);
            case "0x":
            case "0X":
                return Integer.parseUnsignedInt(str.substring(2), 16);
            default:
                if (str.charAt(0) == '0')
                    return Integer.parseUnsignedInt(str.substring(1), 8);
                else
                    return Integer.parseUnsignedInt(str);
        }
    }

}
