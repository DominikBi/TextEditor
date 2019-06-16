package src.main;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;

public class Editor extends JComponent {

    private JTextPane text = new JTextPane();
    private File file;
    private String lastLoad;
    private int size;
    private Color color = Color.BLACK;


    public boolean isLoad() {
        return file != null;
    }
    public void setColor(Color color){
        this.color = color;
        setStyle(0);
    }


    public void setSize(int size) {
        this.size = size;
        setStyle(0);

    }

    public JTextPane getText() {
        text.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 30,Toolkit.getDefaultToolkit().getScreenSize().height/2));
        return text;
    }

    public Editor() {
        text.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-30,Toolkit.getDefaultToolkit().getScreenSize().height/2));
    }

    public boolean hasChanged() {
        return !lastLoad.equals(text.getText());
    }

    public void setText(String text) {
        lastLoad = text;
        this.text.setText(text);
    }

    public void save() throws IOException {
        if (hasChanged()) {
            save(file);
        }
    }

    public File getFile() {
        return file;
    }



    public void setStyle(int style) {
        MutableAttributeSet mas = new SimpleAttributeSet();

        if(style == 1) {
            if(!StyleConstants.isBold(mas)) {
                StyleConstants.setBold(mas, true);
            }else{
                StyleConstants.setBold(mas,false);
            }
        }
        else if(style == 2) {
            if (!StyleConstants.isItalic(mas)) {
                StyleConstants.setItalic(mas, true);
            } else {
                StyleConstants.setItalic(mas, false);
            }
        }
        StyleConstants.setForeground(mas,color);
        StyleConstants.setFontSize(mas, size);
        StyledDocument doc = (DefaultStyledDocument) text.getDocument();
        doc.setCharacterAttributes(text.getSelectionStart(), text.getSelectionEnd()- text.getSelectionStart(),mas,false);
        StyleConstants.setFontFamily(mas,"Arial");
    }

    public void save(File file) throws IOException {
        this.file = file;
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file))) {
            stream.writeShort(text.getFont().getSize());
            stream.writeShort(text.getFont().getStyle());
            stream.writeUTF(text.getFont().getName());
            stream.writeUTF(text.getText());
        }
    }

    public void load(File file) throws IOException {
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            int fontSize = stream.readShort();
            int style = stream.readShort();
            String name = stream.readUTF();

            text.setFont(new Font(name, style, fontSize));
            setText(stream.readUTF());
        }
    }
    public void saveToPrint(File file) throws FileNotFoundException {
        this.file = file;
        FileOutputStream fos = new FileOutputStream(file + ".txt");
        try {
            fos.write(text.getText().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
