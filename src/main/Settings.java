package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Settings {
    private boolean autoSave;

    public boolean isDarkmode() {
        load();
        return darkmode;
    }

    public void setDarkmode(boolean darkmode) {
        this.darkmode = darkmode;
    }

    private boolean darkmode;

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    private MyThread myThread = new MyThread();
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel();
    JCheckBox autoSaveCheckbox = new JCheckBox();
    JEditorPane autoSaveEditorPane = new JEditorPane();
    JCheckBox darkmodeCheckbox = new JCheckBox();
    JEditorPane darkmodeEditorPane = new JEditorPane();

    private void ask(){
        JFrame askFrame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Ok");
        JButton button1 =new JButton("Later");
        JTextField textField = new JTextField("You have to restart the program so the settings apply");
        textField.setEditable(false);
        button.addActionListener(e -> {
            System.exit(0);
        });
        button1.addActionListener(e -> {
            askFrame.setVisible(false);
            jFrame.setVisible(false);
        });
        panel.add(textField, Component.CENTER_ALIGNMENT);
        panel.add(button,Component.LEFT_ALIGNMENT);
        panel.add(button1,Component.RIGHT_ALIGNMENT);
        askFrame.add(panel);
        textField.setPreferredSize(new Dimension(350,60));
        askFrame.setSize(400,200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        askFrame.setLocation(screenSize.width/2-200,screenSize.height/2-100);
        askFrame.setVisible(true);
    }
    private void save() {
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"))) {
            stream.writeBoolean(autoSave);
            stream.writeBoolean(darkmode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void load(){
        try(DataInputStream stream = new DataInputStream(new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"))) {
            autoSave = stream.readBoolean();
            darkmode = stream.readBoolean();
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        autoSaveCheckbox.setSelected(autoSave);
        darkmodeCheckbox.setSelected(darkmode);
    }
    public void check(){
        load();

    }
    public void start(){

        load();


        darkmodeEditorPane.setEditable(false);
        darkmodeEditorPane.setText("Dark-Mode");
        darkmodeCheckbox.addActionListener(e -> {

            darkmode = !darkmode;
            save();
            ask();
        });


        autoSaveEditorPane.setEditable(false);
        autoSaveEditorPane.setText("Auto-Save");

        autoSaveCheckbox.addActionListener(e -> {
            autoSave = !autoSave;
            if (autoSave) {

                myThread.start();
            } else {
                myThread.interrupt();

            }
            save();
            ask();

        });
        jPanel.add(autoSaveCheckbox, Component.RIGHT_ALIGNMENT);
        jPanel.add(autoSaveEditorPane, Component.LEFT_ALIGNMENT);
        jPanel.add(darkmodeCheckbox, Component.RIGHT_ALIGNMENT);
        jPanel.add(darkmodeEditorPane,Component.LEFT_ALIGNMENT);
        jFrame.add(jPanel);
        jFrame.setSize(450, 450);
        jFrame.setVisible(true);

    }
}
