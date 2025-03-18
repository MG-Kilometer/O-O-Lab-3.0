package DataVisualizer;

//Author: Miles Glover
//purpose of file: does stats


import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;


public class StatsPanel extends JPanel {

    private JTable statsTable;
    private DefaultTableModel statsModel;

    public StatsPanel() {

        //sets layout
        setLayout(new BorderLayout());

        //column names for stats table
        String[] columnNames = {"Statistic", "Value"};

        //dummy data for now
        Object[][] statsData = {
                {"Total Rows", 0},
                {"Average Value", 0.0},
                {"Maximum", 0}
        };

        //creates stats table
        statsModel = new DefaultTableModel(statsData, columnNames);
        statsTable = new JTable(statsModel);

        //adds table inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(statsTable);
        add(scrollPane, BorderLayout.CENTER);

    }

    //updates stats based on given values
    public void updateStats(int rowCount, double avgValue, int maxValue) {

        statsModel.setValueAt(rowCount, 0, 1);
        statsModel.setValueAt(avgValue, 1, 1);
        statsModel.setValueAt(maxValue, 2, 1);

    }

}

