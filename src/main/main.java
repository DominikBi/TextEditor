package src.main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class main{
    String verb = "Verb";
    JFrame jFrame = new JFrame();
    JButton button = new JButton("Check");
    JButton buttonAddVerb = new JButton("Add verb");
    JLabel jLabel = new JLabel(verb);
    JTextField jTextField = new JTextField("Spielen Jugar");
    char[] textInChars;
    String text = "";



    private String[] DeutschList = new String[VerbObj.getDeutschVerbs()];
    public String[] SpanischList = new String[VerbObj.getSpansichVerbs()];


    public static void main(String[] args){

        main main = new main();
        main.Learning();
    }
    public void Learning() {

        buttonAddVerb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupmenu = new JPopupMenu("New Verb");
                JTextField DeutschVerb = new JTextField("The German Verb");
                DeutschVerb.setEditable(false);
                JTextField DeutschVerbBox = new JTextField("");
                JTextField SpanischVerb = new JTextField("The Spanisch Verb");
                SpanischVerb.setEditable(false);
                JTextField SpanischVerbBox = new JTextField("");
               
                popupmenu.setLayout(new CardLayout());
                popupmenu.add(DeutschVerbBox, Component.LEFT);
                popupmenu.add(DeutschVerb, Component.LEFT);
                popupmenu.add(SpanischVerbBox, Component.RIGHT);
                popupmenu.add(SpanischVerb, Component.RIGHT);    
                popupmenu.setVisible(true);
                
                
            }
        });
        jFrame.setTitle("Spanisch Lernen");
        jLabel.setSize(400,400);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.setLayout(new BorderLayout());
        jFrame.setSize(jFrame.getMaximumSize());
        jFrame.add(jLabel, BorderLayout.NORTH);
        jFrame.add(button, BorderLayout.SOUTH);
        jFrame.add(jTextField, BorderLayout.NORTH);
        jFrame.setVisible(true);
    }
    public void NewVerb(String Deutsch,String Spanish){

        DeutschList[VerbObj.getDeutschVerbs()] = Deutsch;
        VerbObj.setDeutschVerbs(VerbObj.getDeutschVerbs()+1);
        SpanischList[VerbObj.getSpansichVerbs()] = Spanish;
        VerbObj.setSpansichVerbs(VerbObj.getSpansichVerbs()+1);


    }
}
