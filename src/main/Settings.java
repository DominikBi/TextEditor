package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Settings {
    private boolean autoSave;

    public boolean isDarkmode() {
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


    private void save() {
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"))) {
            stream.writeBoolean(autoSave);
            stream.writeBoolean(darkmode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void load(){
        try(DataInputStream stream = new DataInputStream(new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"))) {
            autoSave = stream.readBoolean();
            darkmode = stream.readBoolean();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        autoSaveCheckbox.setSelected(autoSave);
        darkmodeCheckbox.setSelected(darkmode);
    }
    public void start(){

        load();


        darkmodeEditorPane.setEditable(false);
        darkmodeEditorPane.setText("Dark-Mode");
        darkmodeCheckbox.addActionListener(e -> darkmode = !darkmode);
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
