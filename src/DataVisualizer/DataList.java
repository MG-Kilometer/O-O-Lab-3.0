package DataVisualizer;

//Author: Miles Glover
//purpose of file: takes FileReaders output and stores it as a single ArrayList where each index is another row of data

import java.util.ArrayList;
import java.util.Arrays;

public class DataList {

    //class variable DataRows is initialized to empty ArrayList
    private ArrayList<ArrayList<String>> DataRows = new ArrayList<>();

    //default constructor
    public DataList() {

        runFileToCollectorConverter(new FileReader());

    }

    //parametrized constructor
    public DataList(String filename) {

        runFileToCollectorConverter(new FileReader(filename));

    }

    //method to put file into DataRows collector
    public void runFileToCollectorConverter(FileReader file){

        //split file content string into String Array lines
        String[] lines = file.getFileContent().split("\n");

        //take each line and make each data point its own slot in the sub ArrayList which is one row/line
        for (String line : lines) {

            ArrayList<String> row = new ArrayList<>(Arrays.asList(line.split(",")));
            DataRows.add(row);

        }

    }

    //getter for amount of DataRows in file
    public int size() {

        return DataRows.size();

    }

    //method returns a data row at the specified index
    public ArrayList<String> getLine(int index){

        return DataRows.get(index);

    }

    //getter for DataRows
    public ArrayList<ArrayList<String>> getData() {

        return DataRows;

    }

    //simple clear method for data
    public void clearData(){

        DataRows.clear();

    }

    //simple adder for new data rows (useful for reconstruction and filtering)
    public void add(ArrayList<String> row){

        DataRows.add(row);

    }

}
