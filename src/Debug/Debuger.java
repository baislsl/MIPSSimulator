package Debug;

import BitArea.StdUnit;
import Disassembler.Disassembler;
import GUI.MainFrame;
import MIPSException.DisassembleException;
import Memory.Storage;
import OperationSet.Operation;

import java.util.Scanner;

/**
 * Created by baislsl on 17-5-14.
 */
public class Debuger {
    private final Storage storage;
    private final MainFrame mainFrame;

    public Debuger(Storage storage, MainFrame mainFrame){
        this.storage = storage;
        this.mainFrame = mainFrame;
    }

    public void run(){
        Scanner input = new Scanner(System.in);
        try{
            while (true){
                int PC = storage.getPC();
                StdUnit data = storage.readMemory(PC);
                System.out.println("PC = " + PC + "\n" + data.toBitString());
                Operation operation = Disassembler.disassembler(data);
                operation.conduct(storage);
                mainFrame.updateSegment();
                // input.nextLine();
            }
        }catch (DisassembleException e){
            e.printStackTrace();
        }finally {

        }
    }
}
