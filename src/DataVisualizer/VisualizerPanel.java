package DataVisualizer;

//Author: Miles Glover
//purpose of file: hold the panels of TablePanel, StatsPanel, DetailsPanel, ChartPanel

import javax.swing.*;
import java.awt.*;

public class VisualizerPanel extends JFrame {

    public VisualizerPanel(TablePanel tablePanel, DetailsPanel detailsPanel, StatsPanel statsPanel) {

        //sets up main window
        setTitle("Data Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new GridLayout(2, 2));

        //adds panels in quadrants
        add(tablePanel);
        add(statsPanel);
        add(detailsPanel);
        add(new JLabel("Chart Panel (Placeholder)"));

        //displays the window
        setVisible(true);

    }

}

