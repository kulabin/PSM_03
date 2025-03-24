import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Symulacja ruchu wahadła z tabelką");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        // Utworzenie panelu tabelki
        DataTablePanel dataTablePanel = new DataTablePanel();
        // Utworzenie panelu symulacji z odwołaniem do tabelki
        Sim simPanel = new Sim(dataTablePanel);

        // Ułożenie paneli w pionowym podziale
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, simPanel, dataTablePanel);
        splitPane.setDividerLocation(400);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
