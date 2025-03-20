package DataVisualizer;

//Author: Miles Glover
//purpose of file: takes DataList ArrayList and displays it using simple GUI utilizing Jframe's

import javax.swing.*;

public class TableRunner {

    public static void main(String[] args) {

        //creates instance of DataList
        DataList Data_List = new DataList();

        //prints 1st and 10th row of data, and how many row of data exist
        System.out.println("First row of data -\t\t"+Data_List.getLine(1));
        System.out.println("Tenth row of data -\t\t"+Data_List.getLine(10));
        System.out.println("Number of data rows:\t"+(Data_List.size()-1)+" total rows");

        //creates panels
        DetailsPanel detailsPanel = new DetailsPanel(Data_List);
        StatsPanel statsPanel = new StatsPanel(Data_List);
        ChartPanel chartPanel = new ChartPanel(Data_List);
        TablePanel tablePanel = new TablePanel(Data_List, detailsPanel, statsPanel, chartPanel);

        //creates main visualizer window
        SwingUtilities.invokeLater(() -> new VisualizerPanel(tablePanel, detailsPanel, statsPanel, chartPanel));

    }

}
