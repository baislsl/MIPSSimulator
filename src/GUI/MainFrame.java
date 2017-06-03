package GUI;
import Assembler.Translater;
import Debug.Debuger;
import MIPSException.myParserException;
import Memory.Storage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by baislsl on 17-4-23.
 */
public class MainFrame {
    private final static int HEIGHT = 700;
    private Display display;
    private Shell shell;
    private Storage storage;
    private Translater translater;
    private Text codeText, console, userTextSegment, userDataSegment, registerText;
    private String url = "./src/test/test2.s";

    public MainFrame(){
        storage = new Storage();
        translater = new Translater(storage);
        display = new Display();
        shell = new Shell();
        init();
    }

    private void init(){
        initMenu();
        shell.setLayout(new GridLayout(10, true));

        Group codeTextGroup = new Group(shell, SWT.NONE);
        codeTextGroup.setText("code");
        codeTextGroup.setLayout(new FillLayout());
        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
        codeTextGroup.setLayoutData(data);
        data.heightHint = 500;
        codeText = new Text(codeTextGroup, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        codeText.setBackground(new Color(display, 55,55,55));
        codeText.setForeground(new Color(display, 255, 255, 255));
        codeText.setFont(new Font(display, "ubuntu", 12, SWT.NONE));
        data = new GridData(SWT.FILL, SWT.CENTER, true, false, 3,1);
        data.heightHint = 500;
        Group textSegmentGroup = new Group(shell, SWT.NONE);
        textSegmentGroup.setLayoutData(data);
        textSegmentGroup.setLayout(new FillLayout());
        textSegmentGroup.setText("text segment");
        userTextSegment = new Text(textSegmentGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        userTextSegment.setEditable(false);

        data = new GridData(SWT.FILL, SWT.CENTER, true, false, 2,1);
        data.heightHint = 500;
        Group dataSegmentGroup = new Group(shell, SWT.NONE);
        dataSegmentGroup.setLayoutData(data);
        dataSegmentGroup.setLayout(new FillLayout());
        dataSegmentGroup.setText("data segment");
        userDataSegment = new Text(dataSegmentGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        userDataSegment.setEditable(false);

        data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1,1);
        data.heightHint = 500;
        Group registerTextGroup = new Group(shell, SWT.NONE);
        registerTextGroup.setLayoutData(data);
        registerTextGroup.setLayout(new FillLayout());
        registerTextGroup.setText("register");
        registerText = new Text(registerTextGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        registerText.setEditable(false);

        Group consoleGroup = new Group(shell, SWT.NONE);
        data = new GridData(SWT.FILL, SWT.FILL, true,false, 10,1);
        data.heightHint = 150;
        consoleGroup.setLayoutData(data);
        consoleGroup.setText("console");
        consoleGroup.setLayout(new FillLayout());
        console = new Text(consoleGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        console.setText("console");
        console.setEditable(false);
        openFile(url);
        updateSegment();
    }

    private void initMenu(){
        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);

        MenuItem file = new MenuItem(menu, SWT.CASCADE);
        file.setText("File");
        Menu fileList = new Menu(shell, SWT.DROP_DOWN);
        file.setMenu(fileList);
        MenuItem open = new MenuItem(fileList, SWT.PUSH);
        open.setText("Open");
        open.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                super.widgetSelected(selectionEvent);
                open();
            }
        });
        MenuItem save = new MenuItem(fileList, SWT.PUSH);
        save.setText("save");
        save.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                super.widgetSelected(selectionEvent);
                onSave();
            }
        });

        MenuItem saveAs = new MenuItem(fileList, SWT.PUSH);
        saveAs.setText("save as");
        saveAs.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                super.widgetSelected(selectionEvent);
                onSaveAs();
            }
        });

        MenuItem close = new MenuItem(fileList, SWT.PUSH);
        close.setText("Close");
        MenuItem exit = new MenuItem(fileList, SWT.PUSH);
        exit.setText("Exit");
        exit.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                System.exit(0);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent selectionEvent) {

            }
        });

        MenuItem stimulator = new MenuItem(menu, SWT.CASCADE);
        stimulator.setText("Stimulator");
        Menu stimulatorList = new Menu(shell, SWT.DROP_DOWN);
        stimulator.setMenu(stimulatorList);
        MenuItem run = new MenuItem(stimulatorList, SWT.PUSH);
        run.setText("Run");
        run.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                super.widgetSelected(selectionEvent);
                run();
            }
        });
        MenuItem load = new MenuItem(stimulatorList, SWT.PUSH);
        load.setText("Load");
        load.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                super.widgetSelected(selectionEvent);
                onLoad();
            }
        });
        MenuItem clear = new MenuItem(stimulatorList, SWT.PUSH);
        clear.setText("Clear");
        clear.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                super.widgetSelected(selectionEvent);
                clear();
            }
        });
    }

    private void onSave(){
        onSave(url);
    }

    private void onSave(String url){
        try{
            saveFile(url);
            MessageBox messageBox = new MessageBox(shell, SWT.YES);
            messageBox.setMessage("save successfully!");
            messageBox.open();
        }catch (IOException  e){
            e.printStackTrace();
            MessageBox messageBox = new MessageBox(shell, SWT.YES);
            messageBox.setMessage("error for some reasons!");
            messageBox.open();
        }
    }

    private void onSaveAs(){
        FileDialog dialog = new FileDialog(shell, SWT.OPEN);
        dialog.setFilterExtensions(new String[]{
                ".s",
                ".coe"
        });
        String filePath = dialog.open();
        if(filePath == null) return;
        onSave(filePath);
    }

    private void saveFile(String url) throws IOException {
        PrintWriter out = new PrintWriter(url);
        out.write(codeText.getText());
        out.close();
    }

    private void clear(){
        storage = new Storage();
        translater = new Translater(storage);
        updateSegment();
    }

    private void open(){
        FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
        fileDialog.setFilterExtensions(new String[]{
                "*.s;*.asm",
                "*.txt",
                "*.*"
        });
        String filePath = fileDialog.open();
        if (filePath == null) {
            return;
        }
        url = filePath;
        openFile();
    }

    private void openFile(){
        openFile(url);
    }

    private void openFile(String path){
        try{
            Scanner codeScanner = new Scanner(new File(path));
            String code = "";
            while(codeScanner.hasNextLine()){
                code += codeScanner.nextLine() + "\n";
            }
            codeText.setText(code);
        }catch (FileNotFoundException e){
            console.setText("can not find file in url");
        }

    }

    private void run(){
        onLoad();
        new Debuger(storage, this).run();
    }

    private void onLoad(){
        try{
            translater.translate(Paths.get(url));
        }catch (myParserException e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            console.setText(sw.toString());
            e.printStackTrace();
        }finally {
            updateSegment();
        }
    }

    public void updateSegment(){
        updateRegisterText();
        updateUserDataSegment();
        updateUserTextSegment();
    }

    private void updateRegisterText(){
        registerText.setText(storage.showRegister());
    }
    private void updateUserTextSegment(){
        userTextSegment.setText(storage.showUserText());
    }
    private void updateUserDataSegment(){
        userDataSegment.setText(storage.showUserData());
    }

    public void start(){
        shell.open();
        while(!shell.isDisposed()){
            if(!display.readAndDispatch()){
                display.sleep();
            }
        }
        display.dispose();
    }

}
