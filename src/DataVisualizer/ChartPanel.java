package DataVisualizer;

//Author: Miles Glover
//purpose of file: does chart


import javax.swing.*;
import java.awt.*;

public class ChartPanel extends JPanel {

    public ChartPanel() {

        //sets up panel layout
        setLayout(new BorderLayout());

        //adds placeholder text
        add(new JLabel("Chart Panel - Placeholder", SwingConstants.CENTER), BorderLayout.CENTER);

    }

}

