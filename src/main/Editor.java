package src.main;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class Editor extends JComponent {
    MutableAttributeSet mas = new SimpleAttributeSet();
    private JTextPane text = new JTextPane();
    private File file;
    private String lastLoad;

    public int getTextSize() {
        return textSize;
    }

    public int textSize = StyleConstants.getFontSize(mas);
    private Color color = Color.BLACK;
    private ArrayList<ModifiedText> ModifiedTexts = new ArrayList<>();
    private int modiedTextedLen;
    private boolean sizeHasChanged;
    private boolean isItalic;
    private boolean isBold;
    private Color fontColor;
    private String endText;
    private String fontFamily = "arial";
    private MyFont currentMyFont = new MyFont();
    private float spaceBelow = StyleConstants.getSpaceBelow(mas);
    private boolean isUnderlined;

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public boolean isLoad() {
        return file != null;
    }
    public void setColor(Color color){
        this.color = color;
        sizeHasChanged = false;
        setStyle(0);
    }


    public void setTextSize(int textSize) {
        this.textSize = textSize;
        sizeHasChanged = true;
        setStyle(0);

    }

    public JTextPane getText() {
        text.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 30,Toolkit.getDefaultToolkit().getScreenSize().height/2));
        return text;
    }
    Editor(){
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                StyledDocument doc = (StyledDocument) text.getDocument();
                doc.setCharacterAttributes(text.getText().length(),1,mas,true);
            }
        });
    }


    public boolean hasChanged() {
        return !lastLoad.equals(text.getText());
    }

    public void setText(String text) {
        lastLoad = text;
        this.text.setText(text);
    }

    public void save() throws IOException {
            save(file);

    }

    public File getFile() {
        return file;
    }

    public void setFontFamily(String fontFamily){
        this.fontFamily = fontFamily;
        setStyle(0);
    }

    public MyFont getCurrentMyFont() {
        return currentMyFont;
    }

    public void setCurrentMyFont(MyFont currentMyFont) {
        this.currentMyFont = currentMyFont;
    }
    public void setSpaceBelow(float spaceBelow){
        this.spaceBelow = spaceBelow;

    }

    public void setStyle(int style) {


        if (style == 1) {
            if (!StyleConstants.isBold(mas)) {
                StyleConstants.setBold(mas, true);
                currentMyFont.setBold(true);
            } else {
                StyleConstants.setBold(mas, false);
                currentMyFont.setBold(false);
            }
            isBold = !isBold;

        } else if (style == 2) {
            if (!StyleConstants.isItalic(mas)) {
                StyleConstants.setItalic(mas, true);
                currentMyFont.setItalic(true);

            } else {
                StyleConstants.setItalic(mas, false);
                System.out.println(StyleConstants.isItalic(mas));
                currentMyFont.setItalic(false);
            }
            isItalic = !isItalic;
        }
        else if(style == 3){
            if(!StyleConstants.isUnderline(mas)){
                StyleConstants.setUnderline(mas,true);


            } else {
                StyleConstants.setUnderline(mas,false);
            }
        }
        int selStart = text.getSelectionStart();
        int selLen = text.getSelectionEnd() - text.getSelectionStart();
        StyleConstants.setForeground(mas, color);
        currentMyFont.setForeground(color);
        fontColor = color;
        if (sizeHasChanged) {
            StyleConstants.setFontSize(mas, textSize);
            currentMyFont.setSize(textSize);
            sizeHasChanged = false;
        }
        StyleConstants.setSpaceBelow(mas,spaceBelow);
        StyleConstants.setFontFamily(mas, fontFamily);
        currentMyFont.setFontFamily(fontFamily);
        System.out.println(selStart + " : " + selLen + " : " + StyleConstants.getFontSize(mas) + " : " + StyleConstants.getForeground(mas) + " : " + StyleConstants.isItalic(mas) + " : " + StyleConstants.isBold(mas));
        StyledDocument doc = (DefaultStyledDocument) text.getDocument();

        doc.setCharacterAttributes(selStart, selLen, mas, true);

        ModifiedTexts.add(new ModifiedText(selStart, selLen, StyleConstants.getForeground(mas), StyleConstants.getFontSize(mas), StyleConstants.isItalic(mas), StyleConstants.isBold(mas),StyleConstants.getSpaceBelow(mas), StyleConstants.isUnderline(mas)));
    }

    public ArrayList<ModifiedText> getModifiedTexts() {
        return ModifiedTexts;
    }

    public void setModifiedTexts(ArrayList<ModifiedText> modifiedTexts) {
        ModifiedTexts = modifiedTexts;
    }

    public void save(File file) throws IOException {
        this.file = file;
        try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file + ".te"))) {

            stream.writeUTF(text.getText());
            stream.writeInt(ModifiedTexts.size());
            for(ModifiedText modifiedText : ModifiedTexts){
                stream.writeShort(modifiedText.start);
                stream.writeShort(modifiedText.len);
                stream.writeInt(modifiedText.color.getRed());
                stream.writeInt(modifiedText.color.getGreen());
                stream.writeInt(modifiedText.color.getBlue());
                stream.writeShort(modifiedText.size);
                stream.writeBoolean(modifiedText.isItalic);
                stream.writeBoolean(modifiedText.isBold);
                stream.writeFloat(modifiedText.spaceBelow);
                stream.writeBoolean(modifiedText.isUnderlined);
            }
            FileInputStream fis = new FileInputStream(file + ".te");
            while(fis.available() > 0){
                endText += (char) fis.read();
            }
            System.out.println(endText);


        }
    }

    public void load(File file) throws IOException {
        MutableAttributeSet ownMas = new SimpleAttributeSet();
        this.file = file;
        try (DataInputStream stream = new DataInputStream(new FileInputStream(file))) {
            String text1 = stream.readUTF();
            setText(text1);
            ArrayList<ModifiedText> modifiedTexts = new ArrayList<>();
            int x = stream.readInt();
            for(int i = 0; i <  x; i++) {
                ModifiedText modifiedText = new ModifiedText( stream.readShort(),stream.readShort(),new Color(stream.readInt(),stream.readInt(),stream.readInt()),stream.readShort(),stream.readBoolean(),stream.readBoolean(),stream.readFloat(), stream.readBoolean());
                modifiedTexts.add(modifiedText);
            }


            for(ModifiedText modifiedText : modifiedTexts){
                if(modifiedText.isBold) {
                    StyleConstants.setBold(ownMas, true);
                }
                if(modifiedText.isItalic){
                    StyleConstants.setItalic(ownMas,true);
                }
                if(modifiedText.isUnderlined){
                    StyleConstants.setUnderline(ownMas,true);
                }
                StyleConstants.setFontSize(ownMas,modifiedText.size);
                StyleConstants.setForeground(ownMas,modifiedText.color);
                StyleConstants.setSpaceBelow(ownMas,modifiedText.spaceBelow);

                StyledDocument doc = (DefaultStyledDocument) text.getDocument();
                doc.setCharacterAttributes(modifiedText.start,modifiedText.len,ownMas,true);

            }

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
