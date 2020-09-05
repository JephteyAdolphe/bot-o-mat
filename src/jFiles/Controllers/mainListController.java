package jFiles.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import resources.data.dataCenter;

import java.io.*;
import java.net.URL;
import java.util.*;

public class mainListController implements Initializable {
    public AnchorPane taskPane;
    public ListView robotList;
    public Label task1;
    public Label task2;
    public Label task3;
    public Label task4;
    public Label task5;

    private ObservableList<String> observable_robot_list = FXCollections.observableArrayList();
    private File file = new File("src/resources/data/mockDatabase.txt");
    private dataCenter data = new dataCenter();
    private ArrayList<String> listed_robots = new ArrayList<>();
    private HashMap<Integer, String> taskMap = new HashMap();

    // Scans through the text file (mock database) containing the listed robots

    private void scanFile() throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        robotList.setItems(observable_robot_list);

        while (scan.hasNextLine()) {
            String robot = scan.nextLine();
            if (!listed_robots.contains(robot)) {
                observable_robot_list.add(robot);
                listed_robots.add(robot);
            }
        }

        //Collections.sort(observable_robot_list, String.CASE_INSENSITIVE_ORDER);
        scan.close();
    }

    // Returns the existing robots

    ArrayList<String> getList() throws FileNotFoundException {
        scanFile();
        return listed_robots;
    }

    // Loads the existing robots

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            taskMap.put(0, "do the dishes");
            taskMap.put(1, "sweep the house");
            taskMap.put(2, "do the laundry");
            taskMap.put(3, "take out the recycling");
            taskMap.put(4, "make a sammich");
            taskMap.put(5, "mow the lawn");
            taskMap.put(6, "rake the leaves");
            taskMap.put(7, "give the dog a bath");
            taskMap.put(8, "bake some cookies");
            taskMap.put(9, "wash the car");

            scanFile();
            robotList.getSelectionModel().select(0);
            fill_in_details();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Loads the details of the selected song in the main display

    private void fill_in_details() throws FileNotFoundException {
        String[] details = data.getDetails((String) robotList.getSelectionModel().getSelectedItem());
        System.out.println(Arrays.toString(details));


        if(details != null) {
            System.out.println("made it in");
            task1.setText(taskMap.get(Integer.parseInt(details[0])));
            task2.setText(taskMap.get(Integer.parseInt(details[1])));
            task3.setText(taskMap.get(Integer.parseInt(details[2])));
            task4.setText(taskMap.get(Integer.parseInt(details[3])));
            task5.setText(taskMap.get(Integer.parseInt(details[4])));
        } else {
            task1.setText("");
            task2.setText("");
            task3.setText("");
            task4.setText("");
            task5.setText("");
        }
    }

    public void itemSelection(MouseEvent mouseEvent) throws FileNotFoundException {
        fill_in_details();
    }

    public void completeTasks(MouseEvent mouseEvent) throws IOException {
        if(task1.getText().length() > 0) {
            data.completeTasks((String) robotList.getSelectionModel().getSelectedItem());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/mainList.fxml"));

                Parent listRoot = loader.load();
                Scene list = new Scene(listRoot);

                Stage robot_display = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                robot_display.setScene(list);
                robot_display.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void generateTasks(MouseEvent mouseEvent) throws IOException {
        if(task1.getText().equals("")) {
            data.generateTasks((String) robotList.getSelectionModel().getSelectedItem());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/mainList.fxml"));

                Parent listRoot = loader.load();
                Scene list = new Scene(listRoot);

                Stage robot_display = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                robot_display.setScene(list);
                robot_display.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createRobot(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/creation.fxml"));

            Parent creationRoot = loader.load();
            Scene creation = new Scene(creationRoot);

            Stage creation_display = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            creation_display.setScene(creation);
            creation_display.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
