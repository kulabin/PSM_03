import javax.swing.*;

public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Symulacja ruchu wahadła");
        Sim simPanel = new Sim();
        frame.add(simPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
