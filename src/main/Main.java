package src.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main{

    private static final int selMenu = 0;
    private static final int selModify = 1;

    private JFrame frame = new JFrame("Unknown File");
    private JPanel panel = new JPanel();
    private JFrame expFrame = new JFrame();
    private JColorChooser colorChooser = new JColorChooser();
    private JMenu wordCount;
    public boolean darkmode;
    private boolean autoSave;
    public Editor editor;
    private MenuBar menuBar;
    private String programmName = "Sonit";
    private Text text = new Text();
    private final String[] fonts;
    private ArrayList<JMenu> menus = new ArrayList<>();
    private JMenu menu = new JMenu("File");
    private JMenu modify = new JMenu("Modify");
    private JMenu test = new JMenu("Test");
    private JMenu fontFamily = new JMenu("Font");
    private JMenu changeStyle = new JMenu("Change Text Style");
    private JMenuItem settings = new JMenuItem("Settings");
    private JMenuItem saveAs = new JMenuItem("Save As");
    private JMenuItem color = new JMenuItem("Change Color");
    private JMenuItem open = new JMenuItem("Open");
    private JMenuItem close = new JMenuItem("Close");
    private JMenuItem save = new JMenuItem("Save");
    private JMenuItem size = new JMenuItem("Size");
    private JMenuItem italic = new JMenuItem("Italic");
    private JMenuItem bold = new JMenuItem("Bold");
    private JMenuItem underline = new JMenuItem("Underline");
    private JMenuItem saveToPrint = new JMenuItem("Save to Print");
    private JMenu spaceBelow = new JMenu("Space Below Text");


    private Settings settings1 = new Settings();
    private final String resFolder = "SonitRes";

    private ActionListener settingsListener;
    private ActionListener saveAsListener;
    private ActionListener colorListener;
    private ActionListener openListener;
    private ActionListener closeListener;
    private ActionListener saveListener;
    private ActionListener sizeListener;
    private ActionListener italicListener;
    private ActionListener boldListener;
    private ActionListener underlineListener;
    private ActionListener saveToPrintListener;
    private ActionListener spaceBelowListener;
    private boolean allDeleted;

    public static void main(String[] args) {
        Main main = new Main();
    }

    Main() {
        long time = System.currentTimeMillis();
        menuBar = new MenuBar();
        System.out.println("Main got run at " + (System.currentTimeMillis()-time) + "ms");
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        frame.setTitle("Unknown File" + " - " + programmName);
        System.out.println("Fonts got loaded at " + (System.currentTimeMillis()-time) + "ms");


        System.out.println("Menu got loaded at " + (System.currentTimeMillis()-time) + "ms");
        //getting all the positions of the JMenus

        System.out.println("Rectangles got set at " + (System.currentTimeMillis()-time) + "ms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor = new Editor();
        editor.getText().setPreferredSize(new Dimension(frame.getWidth()-10,frame.getHeight()));
        System.out.println("Editor got set at "  + (System.currentTimeMillis()-time) + "ms");
        text.setSuffix("");
        settings1.check();
        darkmode = settings1.isDarkmode();
        if(darkmode){
            frame.setBackground(Color.darkGray);

            //editor.getText().setForeground(Color.GRAY);
            //editor.getText().setBackground(Color.GRAY);
            // editor.setBackground(Color.BLACK);
            menuBar.getMenuBar().setBackground(Color.lightGray);
        }
        autoSave = settings1.isAutoSave();
        if(autoSave){
            editor.getText().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    super.keyReleased(e);
                    try {
                        editor.save();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
        System.out.println("Settings got loaded at " + (System.currentTimeMillis()-time) + "ms");
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                editor.getText().setPreferredSize(new Dimension(frame.getWidth() -40,frame.getHeight()));

            }
        });

        Image icon = null;


        String picRes = "https://image.flaticon.com/icons/svg/196/196308.svg";
        File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + resFolder + System.getProperty("file.separator") + "th.jpg");

        try {
            icon = ImageIO.read(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setIconImage(icon);
        System.out.println("Pb image got loaded at " + (System.currentTimeMillis()-time) + "ms");

        wordCount = new JMenu("Words: " + editor.getText().getText().split(" ").length + " Chars: " + editor.getText().getText().toCharArray().length);
        editor.getText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                wordCount.setText("Words: " + editor.getText().getText().split(" ").length + " Chars: " + editor.getText().getText().toCharArray().length);
                super.keyTyped(e);
            }
        });
        System.out.println("Word count got set at " + (System.currentTimeMillis()-time) + "ms");
        panel.add(editor.getText(), Component.BOTTOM_ALIGNMENT);
        menuBar.getMenuBar().add(wordCount);
        wordCount.setLocation(menuBar.getMenuBar().getX()+ menuBar.getMenuBar().getWidth()-wordCount.getWidth(),menuBar.getMenuBar().getY());
        menu();
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        //add the mouseListener to frame so that it works
        settingsListener = e -> {
            ArrayList<Integer> sizes = new ArrayList<>();
            for(JMenu jMenu : menus){
                sizes.add(jMenu.getX());
            }

            settings1.start();

        };
        saveAsListener = e -> saveAs();
        colorListener = e -> changeColor();
        boldListener = e -> editor.setStyle(Font.BOLD);
        italicListener = e -> editor.setStyle(Font.ITALIC);
        underlineListener = e -> editor.setStyle(3);
        saveListener = e -> {
            if (editor.isLoad()) {
                save();
            } else {
                saveAs();
            }
        };
        spaceBelowListener = e -> spaceBelowInterface();
        closeListener = e -> close();
        sizeListener = e -> modifySize();
        openListener = e -> explorer();
        saveToPrintListener = e -> saveToPrint();





        System.out.println("Finished at " + (System.currentTimeMillis()-time) + "ms");
    }
    private void menu(){
        settingsListener = e -> {
            ArrayList<Integer> sizes = new ArrayList<>();
            for(JMenu jMenu : menus){
                sizes.add(jMenu.getX());
            }

            settings1.start();

        };
        saveAsListener = e -> saveAs();
        colorListener = e -> changeColor();
        boldListener = e -> editor.setStyle(Font.BOLD);
        italicListener = e -> editor.setStyle(Font.ITALIC);
        underlineListener = e -> editor.setStyle(3);
        saveListener = e -> {
            if (editor.isLoad()) {
                save();
            } else {
                saveAs();
            }
        };
        spaceBelowListener = e -> spaceBelowInterface();
        closeListener = e -> close();
        sizeListener = e -> modifySize();
        openListener = e -> explorer();
        saveToPrintListener = e -> saveToPrint();
        modify.addActionListener(e -> {
            settings.removeActionListener(settingsListener);
            open.removeActionListener(openListener);
            close.removeActionListener(closeListener);
            save.removeActionListener(saveListener);
            saveToPrint.removeActionListener(saveToPrintListener);
            saveAs.removeActionListener(saveAsListener);
            spaceBelow.addActionListener(spaceBelowListener);
            color.addActionListener(colorListener);
            size.addActionListener(sizeListener);
            italic.addActionListener(italicListener);
            bold.addActionListener(boldListener);
            underline.addActionListener(underlineListener);
        });
        menu.addActionListener(e -> {
            settings.addActionListener(settingsListener);
            open.addActionListener(openListener);
            close.addActionListener(closeListener);
            save.addActionListener(saveListener);
            saveToPrint.addActionListener(saveToPrintListener);
            saveAs.addActionListener(saveAsListener);
            spaceBelow.removeActionListener(spaceBelowListener);
            color.removeActionListener(colorListener);
            size.removeActionListener(sizeListener);
            italic.removeActionListener(italicListener);
            bold.removeActionListener(boldListener);
            underline.removeActionListener(underlineListener);
        });


        editor.getText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (!allDeleted) {
                    settings.removeActionListener(settingsListener);
                    open.removeActionListener(openListener);
                    close.removeActionListener(closeListener);
                    save.removeActionListener(saveListener);
                    saveToPrint.removeActionListener(saveToPrintListener);
                    saveAs.removeActionListener(saveAsListener);
                    spaceBelow.removeActionListener(spaceBelowListener);
                    color.removeActionListener(colorListener);
                    size.removeActionListener(sizeListener);
                    italic.removeActionListener(italicListener);
                    bold.removeActionListener(boldListener);
                    underline.removeActionListener(underlineListener);
                    allDeleted = true;
                }
            }
        });

        ArrayList<JMenu> alModify = new ArrayList<>();
        ArrayList<JMenu> alFile = new ArrayList<>();
        ArrayList<JMenu> alTest = new ArrayList<>();
        alTest.add(menu);
        alTest.add(modify);
        alModify.add(menu);
        alModify.add(test);
        alFile.add(test);
        alFile.add(modify);
        modify.addMouseMotionListener(getMouseListener(alModify,modify));
        menu.addMouseMotionListener(getMouseListener(alFile,menu));
        test.addMouseMotionListener(getMouseListener(alTest,test));
        fontFamily.setMnemonic('T');
        for(String font : fonts){
            JMenuItem fontItem = new JMenuItem(font);
            fontItem.addActionListener(e -> editor.setFontFamily(font));
            fontFamily.add(fontItem);
        }
        save.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() )   );

        changeStyle.add(italic);
        changeStyle.add(bold);
        changeStyle.add(underline);
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        close.setAccelerator(KeyStroke.getKeyStroke('W',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveToPrint.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.setMnemonic('F');
        modify.setMnemonic('M');
        modify.add(fontFamily);
        Icon modifyIcon = new ImageIcon(System.getProperty("user.home") +System.getProperty("file.separator") +  resFolder + System.getProperty("file.separator") + "modifyIcon.png");
        menu.add(open);
        menu.add(close);
        menu.add(save);
        menu.add(saveAs);
        menu.add(saveToPrint);
        menu.add(settings);
        modify.add(changeStyle);
        modify.add(size);
        modify.add(spaceBelow);
        modify.add(color);
        menuBar.getMenuBar().add(menu);
        menuBar.getMenuBar().add(modify);
        menuBar.getMenuBar().add(test);
        underline.addActionListener(underlineListener);
        spaceBelow.addActionListener(spaceBelowListener);
        color.addActionListener(colorListener);
        size.addActionListener(sizeListener);
        italic.addActionListener(italicListener);
        bold.addActionListener(boldListener);
        settings.addActionListener(settingsListener);
        open.addActionListener(openListener);
        close.addActionListener(closeListener);
        save.addActionListener(saveListener);
        saveToPrint.addActionListener(saveToPrintListener);
        saveAs.addActionListener(saveAsListener);
        modify.setIcon(modifyIcon);
        Icon fileIcon = new ImageIcon(System.getProperty("user.home") +System.getProperty("file.separator") +  resFolder + System.getProperty("file.separator") + "fileIcon.png");
        menu.setIcon(fileIcon);
        frame.setJMenuBar(menuBar.getMenuBar());
        menus.add(menu);
        menus.add(modify);

        /*Frame Jframe = new Frame();
        Panel panel = new Panel();
        Jframe.setUndecorated(true);
        Jframe.setBackground(Color.black);
        Jframe.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        Jframe.setOpacity(0f);
        Jframe.add(panel);
         */



    }


    private MouseMotionAdapter getMouseListener(ArrayList<JMenu> jMenus, JMenu activeMenu){

        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                for(JMenu jMenu : jMenus){
                        if(new Rectangle(jMenu.getX(),jMenu.getY(),jMenu.getWidth(), jMenu.getHeight()).contains(e.getPoint())){

                            Rectangle jMenuOldPos = new Rectangle(jMenu.getX(),jMenu.getY(),jMenu.getWidth(), jMenu.getHeight());
                            Rectangle activeMenuOldPos = new Rectangle(activeMenu.getX(),activeMenu.getY(),activeMenu.getWidth(),activeMenu.getHeight());
                            System.out.println(activeMenuOldPos + " : " +jMenuOldPos);
                            jMenu.setLocation(activeMenuOldPos.x,activeMenuOldPos.y);
                            activeMenu.setLocation(jMenuOldPos.x,jMenuOldPos.y);
                            jMenu.setPreferredSize(new Dimension(jMenuOldPos.width,jMenuOldPos.height));
                            activeMenu.setPreferredSize(new Dimension(activeMenuOldPos.width,activeMenuOldPos.height));

                        }
                    }

            }

        };
    }
    //color change with color chooser
    private void changeColor(){
        final JFrame frame = new JFrame("Change Color");
        JPanel panel = new JPanel();
        JButton button = new JButton("OK");

        panel.add(colorChooser);
        panel.add(button);
        button.addActionListener(e -> {
            editor.setColor(colorChooser.getColor());
            frame.setVisible(false);
        });
        frame.add(panel);
        frame.setSize(400,400);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-200,Toolkit.getDefaultToolkit().getScreenSize().height/2-200);

        frame.setVisible(true);
    }
    //close the process
    private void close(){
        JFrame frame = new JFrame("Close");
        this.frame.setTitle("");
        JPanel panel = new JPanel();
        JTextField textField = new JTextField("Do you really want to close your file?");
        JButton buttonYes = new JButton("Yes");
        JButton buttonNo = new JButton("No");
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(260,40));
        panel.add(textField);
        panel.add(buttonNo);
        panel.add(buttonYes);
        frame.add(panel);
        frame.setSize(300,300);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-150,Toolkit.getDefaultToolkit().getScreenSize().height/2-150);
        frame.setVisible(true);
        frame.requestFocus();
        buttonYes.addActionListener(e -> System.exit(0));
        buttonNo.addActionListener(e -> frame.setVisible(false));

    }
    //more than one editor in tabs or something. In work
    private void newEditor(){
        editor = new Editor();


    }
    //the modify size frame
    private void modifySize(){
        JFrame frame = new JFrame("Size");
        System.out.println("Worked??");
        JPanel panel = new JPanel();
        frame.requestFocus();
        JComboBox<Integer> comboBox = new JComboBox<>();
        JButton button = new JButton("OK");
        for(int i = 2; i< 128; i+=2){
            comboBox.addItem(i);
        }
        button.addActionListener(e -> {
            frame.setVisible(false);
            editor.setTextSize(Integer.parseInt(Objects.requireNonNull(comboBox.getSelectedItem()).toString()));
   });

        panel.add(comboBox);
        panel.add(button);
        frame.add(panel);
        frame.setSize(250,100);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-100);
        frame.setVisible(true);
    }

    private void settingz(){
        JFrame chooserFrame = new JFrame();
        JPanel panel = new JPanel();

        chooserFrame.setSize(550, 400);
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        panel.add(chooser);
        chooser.addActionListener(e -> {
            try {
                //write in selected file
                editor.save(chooser.getSelectedFile());
                chooserFrame.setVisible(false);
                //addActionListener so every time someone writes something it gets saved
                editor.getText().addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        try {
                            editor.save();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        chooserFrame.add(panel);
        chooserFrame.setVisible(true);
        chooserFrame.requestFocus();

    }
    //save as a txt file for now just if you want to print it.
    private void saveToPrint(){
        JFrame chooserFrame = new JFrame();
        JPanel panel = new JPanel();
        chooserFrame.setSize(550, 400);
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        panel.add(chooser);
        chooser.addActionListener(e -> {
            try {
                editor.saveToPrint(chooser.getSelectedFile());
                frame.setTitle(chooser.getSelectedFile().getName() + "[" + editor.getFile().getAbsolutePath() + "]" + " - " +programmName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            chooserFrame.setVisible(false);
        });
        chooserFrame.add(panel);
        chooserFrame.setVisible(true);
        chooserFrame.requestFocus();
    }

    private void explorer(){
        JPanel panel = new JPanel();
        final File[] file = File.listRoots();
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.accept(file[0]);
        fileChooser.addActionListener(e -> {
            if (e.getActionCommand().equals("ApproveSelection")) {
                try {
                    editor.load(fileChooser.getSelectedFile());
                    wordCount.setText("Words: " + editor.getText().getText().split(" ").length + " Chars: " + editor.getText().getText().toCharArray().length);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.frame.setTitle(editor.getFile().getName() + " [" + editor.getFile() + "]" + " - " +programmName);
                expFrame.setVisible(false);
            }
        });

        panel.add(fileChooser);
        expFrame.add(panel);
        expFrame.setSize(550, 400);
        expFrame.setVisible(true);
        expFrame.requestFocus();
    }
    private void spaceBelowInterface(){
        JFrame spaceBelowFrame= new JFrame("Set space below");
        JPanel spaceBelowPanel = new JPanel();
        JTextField textField = new JTextField();
        JButton button = new JButton("Submit");
        button.addActionListener(e -> {
            float arg = Float.parseFloat(textField.getText());
            editor.setSpaceBelow(arg);
            spaceBelowFrame.setVisible(false);
        });
        spaceBelowPanel.add(textField);
        spaceBelowPanel.add(button);
        spaceBelowFrame.add(spaceBelowPanel);
        spaceBelowFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-100,Toolkit.getDefaultToolkit().getScreenSize().height/2-100);
        spaceBelowFrame.setSize(new Dimension(400,160));
        textField.setPreferredSize(new Dimension(200,80));
        button.setPreferredSize(new Dimension(200/2,80/2));
        spaceBelowFrame.setVisible(true);


    }


    private ActionListener getMainListener(int menu){
        switch (menu) {
            case selMenu:{
                return e -> {
                    settings.addActionListener(settingsListener);
                    open.addActionListener(openListener);
                    close.addActionListener(closeListener);
                    save.addActionListener(saveListener);
                    saveToPrint.addActionListener(saveToPrintListener);
                    saveAs.addActionListener(saveAsListener);

                    spaceBelow.removeActionListener(spaceBelowListener);
                    color.removeActionListener(colorListener);
                    size.removeActionListener(sizeListener);
                    italic.removeActionListener(italicListener);
                    bold.removeActionListener(boldListener);
                    underline.removeActionListener(underlineListener);

                };
            }case  selModify: {
                return e -> {

                    settings.removeActionListener(settingsListener);
                    open.removeActionListener(openListener);
                    close.removeActionListener(closeListener);
                    save.removeActionListener(saveListener);
                    saveToPrint.removeActionListener(saveToPrintListener);
                    saveAs.removeActionListener(saveAsListener);


                    spaceBelow.addActionListener(spaceBelowListener);
                    color.addActionListener(colorListener);
                    size.addActionListener(sizeListener);
                    italic.addActionListener(italicListener);
                    bold.addActionListener(boldListener);
                    underline.addActionListener(underlineListener);
                };
            }
            default: return e -> {
                    if (!allDeleted) {

                        settings.removeActionListener(settingsListener);
                        open.removeActionListener(openListener);
                        close.removeActionListener(closeListener);
                        save.removeActionListener(saveListener);
                        saveToPrint.removeActionListener(saveToPrintListener);
                        saveAs.removeActionListener(saveAsListener);
                        spaceBelow.removeActionListener(spaceBelowListener);
                        color.removeActionListener(colorListener);
                        size.removeActionListener(sizeListener);
                        italic.removeActionListener(italicListener);
                        bold.removeActionListener(boldListener);
                        underline.removeActionListener(underlineListener);


                        allDeleted = true;
                    }


                };
        }
    }

    private void saveAs() {
        JFrame chooserFrame = new JFrame();
        JPanel panel = new JPanel();

        chooserFrame.setSize(550, 400);
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        panel.add(chooser);
        chooser.addActionListener(e -> {
            try {
                editor.save(chooser.getSelectedFile());
                text.setName(chooser.getSelectedFile());
                chooserFrame.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        chooserFrame.add(panel);
        chooserFrame.setVisible(true);
        chooserFrame.requestFocus();
    }


    private void save(){
        try {
            editor.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

