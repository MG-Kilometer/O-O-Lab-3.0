package DataVisualizer;

//Author: Miles Glover
//purpose of file: takes data from DataList and displays it in a table format in a Jframe window. also does filtering and sorting of data


import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;

public class TablePanel extends JPanel {

    //constant class gui variables
    private final int DEFAULT_HEADER_FONT_SIZE = 14;
    private final int DEFAULT_PADDING = 30;
    private final String PANEL_NAME = "Data Table Viewer";

    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private JComboBox<String> filterDropdown1;
    private JComboBox<String> filterDropdown2;
    private JComboBox<String> filterDropdown3;
    private JComboBox<String> sortColumnDropdown;
    private JComboBox<String> sortOrderDropdown;
    private DetailsPanel detailsPanel;
    private StatsPanel statsPanel;

    public TablePanel(DataList dataList, DetailsPanel detailsPanel, StatsPanel statsPanel) {

        //sets up references to other panels
        this.detailsPanel = detailsPanel;
        this.statsPanel = statsPanel;

        //sets up panel layout
        setLayout(new BorderLayout());

        //creates header label
        JLabel titleLabel = new JLabel(PANEL_NAME, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        //gets the data from DataList
        ArrayList<ArrayList<String>> data = dataList.getData();

        //makes sure that data is not empty before proceeding
        if (data.isEmpty()) {

            JOptionPane.showMessageDialog(this, "No data available to display!", "Error", JOptionPane.ERROR_MESSAGE);
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

        //creates table model with data
        model = new DefaultTableModel(tableData, columnNames);
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        //locks column reordering
        table.getTableHeader().setReorderingAllowed(false);

        //disables auto-resizing so horizontal scrolling can work
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //sets column widths based on header text length
        setColumnWidths(table, columnNames);

        //creates scroll pane with horizontal and vertical scrolling
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        //creates filter and sorting controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 3, 10, 10));

        //dropdowns for filtering
        filterDropdown1 = new JComboBox<>(new String[]{"Filter 1: None", "Option A", "Option B"});
        filterDropdown2 = new JComboBox<>(new String[]{"Filter 2: None", "Option X", "Option Y"});
        filterDropdown3 = new JComboBox<>(new String[]{"Filter 3: None", "Option M", "Option N"});

        //dropdowns for sorting
        sortColumnDropdown = new JComboBox<>(columnNames);
        sortOrderDropdown = new JComboBox<>(new String[]{"Ascending", "Descending"});

        //apply sorting button
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applySorting();
            }
        });

        //adds all components to the control panel
        controlPanel.add(filterDropdown1);
        controlPanel.add(filterDropdown2);
        controlPanel.add(filterDropdown3);
        controlPanel.add(sortColumnDropdown);
        controlPanel.add(sortOrderDropdown);
        controlPanel.add(sortButton);

        //adds the control panel to the window
        add(controlPanel, BorderLayout.SOUTH);

        //adds selection listener to update details panel
        table.getSelectionModel().addListSelectionListener(event -> {

            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {

                int modelRow = table.convertRowIndexToModel(selectedRow);
                String[] rowData = new String[columnNames.length];

                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = model.getValueAt(modelRow, i).toString();
                }

                detailsPanel.updateDetails(rowData);

            }

        });

        //updates stats panel with initial data
        updateStatsPanel();

    }

    //method to apply sorting based on dropdown selection
    private void applySorting() {

        int columnIndex = sortColumnDropdown.getSelectedIndex();
        boolean ascending = sortOrderDropdown.getSelectedItem().equals("Ascending");

        sorter.setComparator(columnIndex, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    return ascending ? Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2))
                            : Integer.compare(Integer.parseInt(o2), Integer.parseInt(o1));
                } catch (NumberFormatException e) {
                    return ascending ? o1.compareTo(o2) : o2.compareTo(o1);
                }
            }
        });

        sorter.sort();
        updateStatsPanel();

    }

    //method to update stats panel based on visible data
    private void updateStatsPanel() {

        ArrayList<String[]> visibleData = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {

            String[] rowData = new String[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {

                rowData[j] = model.getValueAt(i, j).toString();

            }

            visibleData.add(rowData);

        }

        statsPanel.updateStatistics(visibleData);

    }

    //method to set column widths based on header text length
    private void setColumnWidths(JTable table, String[] columnNames) {

        JTableHeader header = table.getTableHeader();
        FontMetrics fm = header.getFontMetrics(header.getFont());
        TableColumnModel columnModel = table.getColumnModel();

        for (int i = 0; i < columnNames.length; i++) {

            TableColumn column = columnModel.getColumn(i);
            int width = fm.stringWidth(columnNames[i]) + DEFAULT_PADDING;
            column.setPreferredWidth(width);

        }

    }

}



