package DataVisualizer;

//Author: Miles Glover
//purpose of file: displays stats of three selected columns of interest

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatsPanel extends JPanel {

    DataList dataList;
    private JTable statsTable;
    private DefaultTableModel statsModel;
    private JLabel meanLabel1, medianLabel1, rangeLabel1;
    private JLabel meanLabel2, medianLabel2, rangeLabel2;
    private JLabel meanLabel3, medianLabel3, rangeLabel3;

    //column indices
    private final int col1 = 30;
    private final int col2 = 31;
    private final int col3 = 32;

    public StatsPanel(DataList dataList) {

        this.dataList = dataList;

        //sets layout
        setLayout(new BorderLayout());

        //title label
        JLabel titleLabel = new JLabel("Stats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        //panel for summary stats (stacked layout)
        JPanel summaryPanel = new JPanel(new GridLayout(3, 3, 10, 5));

        //stat labels for first column
        meanLabel1 = new JLabel("Mean: -");
        medianLabel1 = new JLabel("Median: -");
        rangeLabel1 = new JLabel("Range: -");

        summaryPanel.add(new JLabel(dataList.getLine(0).get(col1) + ":"));
        summaryPanel.add(meanLabel1);
        summaryPanel.add(medianLabel1);
        summaryPanel.add(rangeLabel1);

        //stat labels for second column
        meanLabel2 = new JLabel("Mean: -");
        medianLabel2 = new JLabel("Median: -");
        rangeLabel2 = new JLabel("Range: -");

        summaryPanel.add(new JLabel(dataList.getLine(0).get(col2) + ":"));
        summaryPanel.add(meanLabel2);
        summaryPanel.add(medianLabel2);
        summaryPanel.add(rangeLabel2);

        //stat labels for third column
        meanLabel3 = new JLabel("Mean: -");
        medianLabel3 = new JLabel("Median: -");
        rangeLabel3 = new JLabel("Range: -");

        summaryPanel.add(new JLabel(dataList.getLine(0).get(col3) + ":"));
        summaryPanel.add(meanLabel3);
        summaryPanel.add(medianLabel3);
        summaryPanel.add(rangeLabel3);

        add(summaryPanel, BorderLayout.CENTER);

        //calculate stats for the selected columns
        updateStats(dataList, col1, meanLabel1, medianLabel1, rangeLabel1);
        updateStats(dataList, col2, meanLabel2, medianLabel2, rangeLabel2);
        updateStats(dataList, col3, meanLabel3, medianLabel3, rangeLabel3);

    }

    //calculates and updates stats (mean, median, range) for a given column
    public void updateStats(DataList dataList, int columnIndex, JLabel meanLabel, JLabel medianLabel, JLabel rangeLabel) {

        ArrayList<Double> values = new ArrayList<>();

        //collects values from the column
        for (int i = 1; i < dataList.size(); i++) {

            try {
                values.add(Double.parseDouble(dataList.getLine(i).get(columnIndex)));

            } catch (NumberFormatException ignored) { }

        }

        if (values.isEmpty()) return;

        //sorts the values for median and range calculation
        Collections.sort(values);

        //calculates mean, median, and range
        double mean = values.stream().mapToDouble(a -> a).average().orElse(0.0);
        double median = values.size() % 2 == 0 ?
                (values.get(values.size() / 2 - 1) + values.get(values.size() / 2)) / 2.0 :
                values.get(values.size() / 2);
        double range = values.get(values.size() - 1) - values.get(0);

        //updates the labels with calculated values
        meanLabel.setText("Mean: " + String.format("%.2f", mean));
        medianLabel.setText("Median: " + String.format("%.2f", median));
        rangeLabel.setText("Range: " + String.format("%.2f", range));

    }

    //updates the stats after applying filters
    public void filteredUpdate(List<ArrayList<String>> filteredData) {

        DataList filteredDataList = new DataList();

        //adds filtered data to a new DataList
        for (ArrayList<String> row : filteredData) {

            filteredDataList.add(row);

        }

        //updates stats with filtered data
        updateStats(filteredDataList, col1, meanLabel1, medianLabel1, rangeLabel1);
        updateStats(filteredDataList, col2, meanLabel2, medianLabel2, rangeLabel2);
        updateStats(filteredDataList, col3, meanLabel3, medianLabel3, rangeLabel3);

    }

}




