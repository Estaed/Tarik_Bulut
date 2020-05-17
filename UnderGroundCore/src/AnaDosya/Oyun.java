package AnaDosya;

import javax.swing.JFrame;

public class Oyun {
    public static void main(String[] args){
        JFrame pencere = new JFrame("Underground Core");
        pencere.setContentPane(new OyunPaneli());
        pencere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pencere.setResizable(false);
        pencere.pack();
        pencere.setVisible(true);
        
    }
}
