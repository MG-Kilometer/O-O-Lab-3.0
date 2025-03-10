package DataVisualizer;

//Author: Miles Glover
//purpose of file: reads a file and processes it into a form to put into an ArrayList collection in DataList

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileReader {

    //class variables
    private final String DEFAULT_FILE_NAME = "src/DataVisualizer/CO2_DATA.csv";
    private String filename;
    private String fileContent;


    //default constructor
    public FileReader(){

        this.setFilename(DEFAULT_FILE_NAME);
        this.readFile();

    }

    //constructor with file name parameter
    public FileReader(String filename){

        this.setFilename(filename);
        this.readFile();

    }

    //method to read the file located by filename
    public void readFile(){

        //exception detector
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {

            //uses Stream to read all lines and join them with newlines
            fileContent = br.lines().collect(Collectors.joining("\n"));

        //if exception occurs, set fileContent to being empty
        } catch (IOException e) {

            fileContent = "";

        }

    }

    //getter for fileContent
    public String getFileContent() {

        return fileContent;

    }

    //getter for fileContent
    public String getFilename() {

        return filename;

    }

    //setter for filename
    public void setFilename(String filename) {

        this.filename = filename;

    }

}
