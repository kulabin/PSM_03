import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataTablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public DataTablePanel() {
        setLayout(new BorderLayout());
        String[] columnNames = {"Time", "Theta Euler", "Omega Euler", "Energy Euler",
                "Theta Mid", "Omega Mid", "Energy Mid",
                "Theta RK4", "Omega RK4", "Energy RK4"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void clearRows() {
        tableModel.setRowCount(0);
    }

    public int getRowCount() {
        return tableModel.getRowCount();
    }

    public void removeRow(int index) {
        tableModel.removeRow(index);
    }
}