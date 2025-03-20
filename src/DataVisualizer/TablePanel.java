package DataVisualizer;

//Author: Miles Glover
//purpose of file: takes data from DataList and displays it in a table format in a Jframe window. also does filtering and sorting of data


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel {

    //constant thresholds for filters
    private final double Filter1Threshold = 2.113;
    private final double Filter2Threshold = 20000;

    //GUI components
    private JTable table;
    private DefaultTableModel model;
    private DataList dataList;
    private DetailsPanel detailsPanel;
    private StatsPanel statsPanel;
    private ChartPanel chartPanel;
    private JComboBox<String> sortColumnSelector;
    private JComboBox<String> timeFilter, numFilter1, numFilter2;

    //constructor for TablePanel class
    public TablePanel(DataList dataList, DetailsPanel detailsPanel, StatsPanel statsPanel, ChartPanel chartPanel) {

        this.dataList = dataList;
        this.detailsPanel = detailsPanel;
        this.statsPanel = statsPanel;
        this.chartPanel = chartPanel;

        setLayout(new BorderLayout());

        //title label for the panel
        JLabel titleLabel = new JLabel("Table", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        //create column headers based on data from dataList
        ArrayList<String> headers = dataList.getLine(0);
        String[] columnNames = headers.toArray(new String[0]);

        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        //populate table rows with data from dataList
        for (int i = 1; i < dataList.size(); i++) {

            model.addRow(dataList.getLine(i).toArray(new Object[0]));

        }

        //set table to not auto resize columns
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //create sort column selector combo box
        sortColumnSelector = new JComboBox<>(columnNames);
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(this::sortTable);

        //create filter combo boxes
        timeFilter = new JComboBox<>(new String[]{"No Filter", "Before 2020", "After 2020"});
        numFilter1 = new JComboBox<>(new String[]{"No Filter", "Above "+Filter1Threshold, "Below "+Filter1Threshold});
        numFilter2 = new JComboBox<>(new String[]{"No Filter", "Above "+Filter2Threshold, "Below "+Filter2Threshold});

        //create apply filters button
        JButton filterButton = new JButton("Apply Filters");
        filterButton.addActionListener(this::applyFilters);

        //create control panel with sorting and filtering controls
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //sort by label and column selector
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Sort by:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(sortColumnSelector, gbc);

        gbc.gridx = 2;
        controlPanel.add(sortButton, gbc);

        //time filter label and combo box
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Time Filter:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(timeFilter, gbc);

        //filter for CO2 Factor
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Filter CO2.Factor.OIL:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(numFilter1, gbc);

        //filter for Positive Generation
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("Filter Positive.Generation:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(numFilter2, gbc);

        //apply filters button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        controlPanel.add(filterButton, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        //listener to update details panel on table row selection
        table.getSelectionModel().addListSelectionListener(event -> updateDetailsPanel());

    }

    //method to sort table based on selected column
    private void sortTable(ActionEvent e) {

        int columnIndex = sortColumnSelector.getSelectedIndex();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        //copy table data into ArrayList for sorting
        for (int i = 0; i < model.getRowCount(); i++) {

            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < model.getColumnCount(); j++) {

                row.add(model.getValueAt(i, j).toString());

            }
            data.add(row);

        }

        //sort data based on selected column index
        data.sort((row1, row2) -> row1.get(columnIndex).compareTo(row2.get(columnIndex)));

        //update table with sorted data
        model.setRowCount(0);
        for (ArrayList<String> row : data) {

            model.addRow(row.toArray());

        }

    }

    //method to apply filters to table data
    private void applyFilters(ActionEvent e) {

        List<ArrayList<String>> filteredData = new ArrayList<>();

        //iterate through data rows to apply filters
        for (int i = 1; i < dataList.size(); i++) {

            ArrayList<String> row = dataList.getLine(i);
            boolean matchesFilter = true;

            //time filter based on year
            String date = row.get(1);
            int year = Integer.parseInt(date.split("/")[2]);
            String timeSelection = (String) timeFilter.getSelectedItem();
            if ("Before 2020".equals(timeSelection) && year >= 2020) matchesFilter = false;
            if ("After 2020".equals(timeSelection) && year < 2020) matchesFilter = false;

            //numerical filter 1 (CO2 factor)
            try {
                String num1Selection = (String) numFilter1.getSelectedItem();
                double value1 = Double.parseDouble(row.get(26));
                if (("Above " + Filter1Threshold).equals(num1Selection) && value1 <= Filter1Threshold)
                    matchesFilter = false;
                if (("Below " + Filter1Threshold).equals(num1Selection) && value1 >= Filter1Threshold)
                    matchesFilter = false;
            }catch (NumberFormatException ex) {matchesFilter = true;}

            //numerical filter 2 (positive generation)
            try {
                String num2Selection = (String) numFilter2.getSelectedItem();
                double value2 = Double.parseDouble(row.get(38));
                if (("Above " + Filter2Threshold).equals(num2Selection) && value2 <= Filter2Threshold)
                    matchesFilter = false;
                if (("Below " + Filter2Threshold).equals(num2Selection) && value2 >= Filter2Threshold)
                    matchesFilter = false;
            }catch (NumberFormatException ex) {matchesFilter = true;}

            //add row to filtered data if it matches all filters
            if (matchesFilter) filteredData.add(row);

        }

        //update table with filtered data
        model.setRowCount(0);
        for (ArrayList<String> row : filteredData) {

            model.addRow(row.toArray());

        }

        //update each of the other panels to reflect the filtered data
        statsPanel.filteredUpdate(filteredData);
        chartPanel.filteredUpdate(filteredData,dataList.getLine(0));
        detailsPanel.filteredUpdate(filteredData,dataList.getLine(0));

    }

    //method to update details panel with selected row details
    private void updateDetailsPanel() {

        int rowIndex = table.getSelectedRow();
        if (rowIndex >= 0) {

            StringBuilder details = new StringBuilder();
            for (int i = 0; i < model.getColumnCount(); i++) {

                details.append(model.getColumnName(i)).append(": ").append(model.getValueAt(rowIndex, i)).append("\n");

            }

            detailsPanel.updateDetails(details.toString(), rowIndex);

        }

    }

}