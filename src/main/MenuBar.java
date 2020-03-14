package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class MenuBar {
    private JMenuBar menuBar;
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

    public JMenuBar getMenuBar(){
        return menuBar;

    }
    MenuBar(){
        menuBar = new JMenuBar();
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);
        Font font = new Font("Tahoma",Font.PLAIN,12);
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        ArrayList<JMenu> alModify = new ArrayList<>();
        ArrayList<JMenu> alFile = new ArrayList<>();
        ArrayList<JMenu> alTest = new ArrayList<>();
        menuBar.setPreferredSize(new Dimension(100,50));
        alTest.add(menu);
        alTest.add(modify);
        alModify.add(menu);
        alModify.add(test);
        alFile.add(test);
        alFile.add(modify);
        fontFamily.setMnemonic('T');

    }



}
