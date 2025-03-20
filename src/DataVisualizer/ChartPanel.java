package DataVisualizer;

//Author: Miles Glover
//purpose of file: does the chart quadrant of the visualizerPanel and allows decisions of what column of data you are currently showing in a bar chart

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChartPanel extends JPanel {

    private JLabel titleLabel;
    private JPanel chartArea;
    private JComboBox<String> columnSelector;
    private int selectedColumn = 3;
    private List<Double> columnData = new ArrayList<>();
    private double minValue = 0, maxValue = 1;
    private ArrayList<String> header;

    public ChartPanel(DataList dataList) {
        this.header = dataList.getLine(0);

        // set layout
        setLayout(new BorderLayout());

        // title label
        titleLabel = new JLabel("Chart", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // chart area (inner panel to draw bars)
        chartArea = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                drawChart(g);

            }

        };

        chartArea.setPreferredSize(new Dimension(600, 300));
        chartArea.setBackground(Color.WHITE);
        add(chartArea, BorderLayout.CENTER);

        // column selector dropdown
        columnSelector = new JComboBox<>(header.toArray(new String[0]));
        columnSelector.addActionListener(e -> {

            selectedColumn = columnSelector.getSelectedIndex();
            updateChart(dataList);

        });
        add(columnSelector, BorderLayout.SOUTH);

    }

    // draws the chart based on column data
    private void drawChart(Graphics g) {

        if (columnData.isEmpty()) return;

        int width = chartArea.getWidth();
        int height = chartArea.getHeight();
        int barWidth = Math.max(1, width / columnData.size()); // ensure at least 1 pixel width

        g.setColor(Color.BLUE);

        for (int i = 0; i < columnData.size(); i++) {

            double normalizedHeight = (columnData.get(i) - minValue) / (maxValue - minValue);
            int barHeight = Math.max(1, (int) (normalizedHeight * height)); // ensure at least 1 pixel height
            int x = i * barWidth;
            int y = height - barHeight;
            g.fillRect(x, y, barWidth, barHeight);

        }

    }

    // updates the chart when the selected column or data changes
    private void updateChart(DataList dataList) {

        columnData.clear();
        if (dataList.size() == 0 || selectedColumn >= dataList.getLine(0).size()) {

            chartArea.repaint();
            return;

        }

        // extract selected column data
        for (ArrayList<String> row : dataList.getData()) {

            try {

                columnData.add(Double.parseDouble(row.get(selectedColumn)));

            } catch (NumberFormatException e) {

                columnData.add(0.0);

            }

        }

        // compute min and max values
        minValue = columnData.stream().min(Double::compare).orElse(0.0);
        maxValue = columnData.stream().max(Double::compare).orElse(1.0);

        chartArea.repaint();

    }

    // updates the chart with filtered data and new headers
    public void filteredUpdate(List<ArrayList<String>> filteredData, ArrayList<String> header) {

        DataList dataList = new DataList();
        dataList.clearData();

        for (ArrayList<String> row : filteredData) {

            dataList.add(row);

        }

        updateChart(dataList);

    }
}
