package DataVisualizer;

//Author: Miles Glover
//purpose of file: does details

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {

    private JTextArea detailsText;
    private JLabel selectedRowLabel;

    public DetailsPanel() {

        //sets layout
        setLayout(new BorderLayout());

        //label for currently selected row
        selectedRowLabel = new JLabel("Selected Row: None");
        add(selectedRowLabel, BorderLayout.NORTH);

        //text area for displaying row details
        detailsText = new JTextArea();
        detailsText.setEditable(false);
        detailsText.setLineWrap(true);
        detailsText.setWrapStyleWord(true);

        //adds text area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(detailsText);
        add(scrollPane, BorderLayout.CENTER);

    }

    //updates the displayed details based on selected row
    public void updateDetails(String rowDetails, int rowIndex) {

        selectedRowLabel.setText("Selected Row: " + (rowIndex + 1));
        detailsText.setText(rowDetails);

    }

}
