package src.main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class main{
    String verb = "Verb";
    JFrame jFrame = new JFrame();
    JButton button = new JButton("Check");
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

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerbObj.setDeutsch("Spielen");
                VerbObj.setSpanish("Jugar");
                text = jTextField.getSelectedText();
                System.out.println(text);
                String mid = "";
                String Deutsch = "";
                String Spanisch = "";
                for(int i = 0; i<textInChars.length;i++){
                    mid = textInChars[i] + mid;
                if(mid == " " || mid == null) {
                    String finalDeutsch =  Deutsch;
                        break;

                }
                else{
                    Deutsch = mid  + Deutsch;
                }
                }
                System.out.println(Deutsch);
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
