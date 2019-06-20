package src.main;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class Editor extends JComponent {
    MutableAttributeSet mas = new SimpleAttributeSet();
    private JTextPane text = new JTextPane();
    private File file;
    private String lastLoad;
    private int size;
    private Color color = Color.BLACK;
    private ArrayList<ModifiedText> ModifiedTexts = new ArrayList<>();
    private int modiedTextedLen;
    private boolean sizeHasChanged;


    public boolean isLoad() {
        return file != null;
    }
    public void setColor(Color color){
        this.color = color;
        sizeHasChanged = false;
        setStyle(0);
    }


    public void setSize(int size) {
        this.size = size;
        sizeHasChanged = true;
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
        int selStart = text.getSelectionStart();
        int selLen = text.getSelectionEnd()- text.getSelectionStart();
        StyleConstants.setForeground(mas,color);
        if(sizeHasChanged) {
            StyleConstants.setFontSize(mas, size);
            sizeHasChanged = false;
        }

        StyledDocument doc = (DefaultStyledDocument) text.getDocument();
        doc.setCharacterAttributes(selStart, selLen ,mas,false);
        StyleConstants.setFontFamily(mas,"Arial");
            ModifiedTexts.add(new ModifiedText(selStart,selLen, StyleConstants.getForeground(mas),StyleConstants.getFontSize(mas),StyleConstants.isItalic(mas),StyleConstants.isBold(mas)));


    }

    public void save(File file) throws IOException {
        this.file = file;
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file + ".te"))) {

            stream.writeUTF(text.getText());
            stream.writeInt(ModifiedTexts.size());
            for(ModifiedText modifiedText : ModifiedTexts){
                stream.writeShort(modifiedText.start);
                stream.writeShort(modifiedText.len);
                stream.writeUTF(String.valueOf(modifiedText.color));
                stream.writeShort(modifiedText.size);
                stream.writeBoolean(modifiedText.isItalic);
                stream.writeBoolean(modifiedText.isBold);
            }


        }
    }

    public void load(File file) throws IOException {
        MutableAttributeSet ownMas = new SimpleAttributeSet();
        this.file = file;
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            String text1 = stream.readUTF();
            System.out.println(text1);
            setText(text1);
            ArrayList<ModifiedText> modifiedTexts = new ArrayList<>();
            int x = stream.readInt();
            System.out.println(x);
            for(int i = 0; i <  x; i++) {
                ModifiedText modifiedText = new ModifiedText( stream.readShort(),stream.readShort(),Color.getColor(stream.readUTF()),stream.readShort(),stream.readBoolean(),stream.readBoolean());
                modifiedTexts.add(modifiedText);
            }


            System.out.println(text.getText() + "whahaaayyy");
            System.out.println(modifiedTexts.size());
            for(ModifiedText modifiedText : modifiedTexts){
                if(modifiedText.isBold) {
                    StyleConstants.setBold(ownMas, true);
                }
                if(modifiedText.isItalic){
                    StyleConstants.setItalic(ownMas,true);
                }
                StyleConstants.setFontSize(ownMas,modifiedText.size);
                StyleConstants.setForeground(ownMas,modifiedText.color);
                StyledDocument doc = (StyledDocument) text.getDocument();
                System.out.println(StyleConstants.getForeground(ownMas) + " : " + StyleConstants.getFontSize(ownMas) + " : " + StyleConstants.isBold(ownMas) + " : " + StyleConstants.isItalic(ownMas) + " : " + modifiedText.start + " : " + modifiedText.len);
                doc.setCharacterAttributes(modifiedText.start,modifiedText.len,ownMas,false);

            }
            System.out.println(text.getText());
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
