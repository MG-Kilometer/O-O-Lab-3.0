package DataVisualizer;

//Author: Miles Glover
//purpose of file: this panel displays every single data point of a selected row into quarter of the visualizer

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DetailsPanel extends JPanel {

    private JTextArea detailsText;
    private JLabel selectedRowLabel;
    private int rowIndex;

    public DetailsPanel(DataList dataList) {

        rowIndex = 0;

        // sets layout
        setLayout(new BorderLayout());

        // title label
        JLabel titleLabel = new JLabel("Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // label for currently selected row
        selectedRowLabel = new JLabel("Selected Row: None");
        add(selectedRowLabel, BorderLayout.SOUTH);

        // text area for displaying row details
        detailsText = new JTextArea();
        detailsText.setEditable(false);
        detailsText.setLineWrap(true);
        detailsText.setWrapStyleWord(true);

        // adds text area inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(detailsText);
        add(scrollPane, BorderLayout.CENTER);

    }

    // updates the displayed details based on selected row
    public void updateDetails(String rowDetails, int rowIndex) {

        this.rowIndex = rowIndex;
        selectedRowLabel.setText("Selected Row: " + (rowIndex + 1));
        detailsText.setText(rowDetails);

    }

    // updates the details with filtered data
    public void filteredUpdate(List<ArrayList<String>> filteredData, ArrayList<String> header) {

        if(rowIndex <= filteredData.size() - 1) {

            StringBuilder filteredDataFormatted = new StringBuilder();
            int i = 0;

            // formats and displays filtered row data
            for (String item : filteredData.get(rowIndex)) {

                filteredDataFormatted.append(header.get(i));
                filteredDataFormatted.append(": ").append(item).append("\n");
                i++;

            }

            updateDetails(filteredDataFormatted.toString(), rowIndex);

        } else {

            updateDetails("", 0);

        }

    }

}
