package DataVisualizer;

//Author: Miles Glover
//purpose of file: hold the panels of TablePanel, StatsPanel, DetailsPanel, ChartPanel

import javax.swing.*;
import java.awt.*;

public class VisualizerPanel extends JFrame {

    public VisualizerPanel(DataList dataList) {

        //sets up main window
        setTitle("Data Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new GridLayout(2, 2));

        //adds the four quadrants to the panel
        add(new TablePanel(dataList));
        add(new StatsPanel());
        add(new ChartPanel());
        add(new DetailsPanel());

        //displays the window
        setVisible(true);

    }

}

