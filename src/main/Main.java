package src.main;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Main implements Runnable{


    private JFrame frame = new JFrame("Unknown File");
    private JPanel panel = new JPanel();
    private JFrame expFrame = new JFrame();
    private JColorChooser colorChooser = new JColorChooser();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu wordCount;
    public boolean darkmode;
    public Editor editor;
    private String programmName = "Sonit";
    private Text text = new Text();
    private String[] fonts;
    private ArrayList<JMenu> menus = new ArrayList<>();
    private JMenu menu = new JMenu("File");
    private JMenu modify = new JMenu("Modify");
    private JMenu test = new JMenu("Test");

    private String picRes = "https://image.flaticon.com/icons/svg/196/196308.svg";
    private Settings settings1 = new Settings();
    private String resFolder = "SonitRes";


    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
    private MouseMotionListener getMouseListener(ArrayList<JMenu> jMenus, JMenu activeMenu){

        return new MouseMotionListener() {
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
                            System.out.println(jMenu.getText());

                        }
                    }

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        };
    }
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

    private void close(){
        JFrame frame = new JFrame("Close");
        this.frame.setTitle("");
        JPanel panel = new JPanel();
        JTextField textField = new JTextField("Do you really want to close youre file?");
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
        buttonYes.addActionListener(e -> System.exit(0));
        buttonNo.addActionListener(e -> frame.setVisible(false));

    }
    private void newEditor(){
        editor = new Editor();


    }

    private void modifySize(){
        final JFrame frame = new JFrame("Size");
        JPanel panel = new JPanel();
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
                        Thread.currentThread().notify();
                        System.out.println("ads");
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

    }
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

    private void menu(){
        JMenu fontFamily = new JMenu("Font");
        JMenu changeStyle = new JMenu("Change Text Style");
        JMenuItem settings = new JMenuItem("Settings");
        JMenuItem saveAs = new JMenuItem("Save As");
        JMenuItem color = new JMenuItem("Change Color");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem size = new JMenuItem("Size");
        JMenuItem italic = new JMenuItem("Italic");
        JMenuItem bold = new JMenuItem("Bold");
        JMenuItem underline = new JMenuItem("Underline");
        JMenuItem saveToPrint = new JMenuItem("Save to Print");
        JMenu spaceBelow = new JMenu("Space Below Text");
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

        spaceBelow.addActionListener(e -> spaceBelowInterface());
        save.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() )   );
        italic.addActionListener(e -> editor.setStyle(Font.ITALIC));
        bold.addActionListener(e -> editor.setStyle(Font.BOLD));
        color.addActionListener(e -> changeColor());

        size.addActionListener(e -> modifySize());
        underline.addActionListener(e -> editor.setStyle(3));
                save.addActionListener(e -> {
                    if (editor.isLoad()) {
                        save();
                    } else {
                        saveAs();
                    }
                });
        close.addActionListener(e -> close());
        open.addActionListener(e -> explorer());
        saveAs.addActionListener(e -> saveAs());
        saveToPrint.addActionListener(e -> saveToPrint());
        settings.addActionListener(e -> {
            ArrayList<Integer> sizes = new ArrayList<>();
            for(JMenu jMenu : menus){
                sizes.add(jMenu.getX());
            }

            settings1.start();
        });
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
        menuBar.add(menu);
        menuBar.add(modify);
        menuBar.add(test);

        modify.setIcon(modifyIcon);
        Icon fileIcon = new ImageIcon(System.getProperty("user.home") +System.getProperty("file.separator") +  resFolder + System.getProperty("file.separator") + "fileIcon.png");
        menu.setIcon(fileIcon);

        frame.setJMenuBar(menuBar);

        menus.add(menu);
        menus.add(modify);

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
    }

    private void start(){
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        frame.setTitle("Unknown File" + " - " + programmName);
        MyThread myThread = new MyThread();

        myThread.start();
        menu();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor = new Editor();
        editor.getText().setPreferredSize(new Dimension(frame.getWidth()-10,frame.getHeight()));
        text.setSuffix("");
        settings1.check();
        darkmode = settings1.isDarkmode();
        if(darkmode){
            frame.setBackground(Color.darkGray);
            editor.getText().setForeground(Color.GRAY);
            menuBar.setBackground(Color.lightGray);
        }
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                editor.getText().setPreferredSize(new Dimension(frame.getWidth() -40,frame.getHeight()));

            }
        });
        Image icon = null;
        try {


                URL url = new URL(picRes);
                icon = ImageIO.read(url);
                if(icon == null){
                    File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + resFolder + System.getProperty("file.separator") + "th.jpg");
                    System.out.println(file);
                    try {
                        icon = ImageIO.read(file);
                        System.out.println("Icon from: " + file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Icon from: " + url);
                }

        }catch (IIOException e1){
            File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "TeRes" + System.getProperty("file.separator") + "th.jpg");
            try {
                icon = ImageIO.read(file);
                System.out.println("Icon from: " +file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setIconImage(icon);

        Thread darkModeThread =  new Thread();
        darkModeThread.start();
        wordCount = new JMenu("Words: " + editor.getText().getText().split(" ").length + " Chars: " + editor.getText().getText().toCharArray().length);
        editor.getText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                wordCount.setText("Words: " + editor.getText().getText().split(" ").length + " Chars: " + editor.getText().getText().toCharArray().length);
                super.keyTyped(e);
            }
        });
        panel.add(editor.getText(), Component.BOTTOM_ALIGNMENT);
        menuBar.add(wordCount);
        wordCount.setLocation(menuBar.getX()+ menuBar.getWidth()-wordCount.getWidth(),menuBar.getY());
        frame.add(panel);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        frame.setVisible(true);
    }

    private void save(){
        try {
            editor.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long currentMillis = System.currentTimeMillis();
        while(true){

            if(currentMillis + 1000 == System.currentTimeMillis()){
                if(settings1.isDarkmode()){
                        //inWork

                }

                currentMillis+=1000;

            }


            }
        }
    }

