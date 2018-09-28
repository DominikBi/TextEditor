package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main  implements ActionListener{

    public static void main(String[] args){
        main main = new main();
        main.Learning();
    }
    public void Learning()

    {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton button = new JButton("Test");
        jFrame.setLayout(new BorderLayout());
        jFrame.setSize(jFrame.getMaximumSize());
        jFrame.add(button, BorderLayout.SOUTH);
        jFrame.setVisible(true);
    }
    {



    }
    public String[] VerbInput(String... Verbs) {
        String[] VerbList =new String[Verbs.length];
        for (int i = 0; i< Verbs.length;i++) {
            Verbs[i] = VerbList[i];
        }
        return VerbList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
