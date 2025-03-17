package DataVisualizer;

//Author: Miles Glover
//purpose of file: takes data from DataList and displays it in a table format in a Jframe window

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class TablePanel extends JPanel {

    private final int DEFAULT_HEADER_FONT_SIZE = 14;
    private final int DEFAULT_PADDING = 30;

    public TablePanel(DataList dataList) {

        //sets up panel layout
        setLayout(new BorderLayout());

        //gets the data from DataList
        ArrayList<ArrayList<String>> data = dataList.getData();

        //makes sure that data is not empty before proceeding
        if (data.isEmpty()) {
            add(new JLabel("No data available to display!"), BorderLayout.CENTER);
            return;
        }

        //gets the header data and put it into both originalHeaders and columnNames
        String[] originalHeaders = data.get(0).toArray(new String[0]);
        String[] columnNames = new String[originalHeaders.length + 1];

        //adds custom column to track index of current data in the table
        columnNames[0] = "Index";
        System.arraycopy(originalHeaders, 0, columnNames, 1, originalHeaders.length);

        //gets the regular data and put it into tableData
        String[][] tableData = new String[data.size() - 1][columnNames.length];
        for (int i = 1; i < data.size(); i++) {
            tableData[i - 1][0] = String.valueOf(i);
            for (int j = 0; j < originalHeaders.length; j++) {
                tableData[i - 1][j + 1] = data.get(i).get(j);
            }
        }

        //creates table with data
        DefaultTableModel model = new DefaultTableModel(tableData, columnNames);
        JTable table = new JTable(model);

        //sets custom header font to make column names stand out
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, DEFAULT_HEADER_FONT_SIZE));

        //makes sure that column widths match header text width
        setColumnWidths(table, columnNames);

        //locks column reordering
        table.getTableHeader().setReorderingAllowed(false);

        //forces table to have a large preferred size so it extends beyond the window
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        //adds table inside a scroll pane (plus horizontal scrolling)
        add(scrollPane, BorderLayout.CENTER);

    }

    private void setColumnWidths(JTable table, String[] columnNames) {

        //gets font metrics to calculate column width
        JTableHeader header = table.getTableHeader();
        FontMetrics fm = header.getFontMetrics(header.getFont());
        TableColumnModel columnModel = table.getColumnModel();

        //sets column width based on text width
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn column = columnModel.getColumn(i);
            int width = fm.stringWidth(columnNames[i]) + DEFAULT_PADDING;
            column.setPreferredWidth(width);
        }

    }

}
