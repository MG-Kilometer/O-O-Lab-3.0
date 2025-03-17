package DataVisualizer;

//Author: Miles Glover
//purpose of file: does details

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {

    public DetailsPanel() {

        //sets up panel layout
        setLayout(new BorderLayout());

        //adds placeholder text
        add(new JLabel("Details Panel - Placeholder", SwingConstants.CENTER), BorderLayout.CENTER);

    }

}
