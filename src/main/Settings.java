package src.main;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Settings {
    private boolean autoSave;

    public boolean isAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    private MyThread myThread = new MyThread();
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel();
    JCheckBox jCheckBox = new JCheckBox();
    JEditorPane editorPane = new JEditorPane();



    private void save() {
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"))) {
            stream.writeBoolean(autoSave);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void load(){
        try(DataInputStream stream = new DataInputStream(new FileInputStream(System.getProperty("user.home") + System.getProperty("file.separator") + "TextEditorSettings"))) {
            autoSave = stream.readBoolean();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        jCheckBox.setSelected(autoSave);

    }
    public void start(){
        load();



        editorPane.setEditable(false);
        editorPane.setText("Auto-Save");

        jCheckBox.addActionListener(e -> {
            autoSave = !autoSave;
            if (autoSave) {

                myThread.start();
            } else {

            }
            save();

        });
        jPanel.add(jCheckBox, Component.RIGHT_ALIGNMENT);
        jPanel.add(editorPane, Component.LEFT_ALIGNMENT);
        jFrame.add(jPanel);
        jFrame.setSize(450, 450);
        jFrame.setVisible(true);

    }
}
