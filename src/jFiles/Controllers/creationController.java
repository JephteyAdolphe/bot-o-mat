package jFiles.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import jFiles.Robot;
import javafx.stage.Stage;
import resources.data.dataCenter;

public class creationController implements Initializable {
    public RadioButton unipedal;
    public TextField robotName;
    public RadioButton bipedal;
    public RadioButton quadrupedal;
    public RadioButton arachnid;
    public RadioButton radial;
    public RadioButton aeronautical;
    public Button createButton;

    private ArrayList<RadioButton> buttons = new ArrayList<>();
    private dataCenter data = new dataCenter();


    public void create(MouseEvent mouseEvent) throws IOException {

        if(!robotName.getText().trim().equals("") && getRobotType() != null && robotName.getText().trim().length() <= 10) {    // Check for valid user input (name less than 10 char)

            String robot = robotName.getText().trim() + ", " + getRobotType();  // Creates robot
            if(!data.exists(robot)) {
                Robot rob = new Robot(robot);
                data.write(robotName.getText().trim(), getRobotType(), 0, rob.getTasks());

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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttons.add(unipedal);
        buttons.add(bipedal);
        buttons.add(quadrupedal);
        buttons.add(arachnid);
        buttons.add(radial);
        buttons.add(aeronautical);
    }

    private void disableOtherButtons(RadioButton rad) {
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i) != rad) {
                buttons.get(i).setSelected(false);
            }
        }
    }

    private String getRobotType() {
        String type = null;

        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).isSelected()) {
                type = buttons.get(i).getText();
                break;
            }
        }
        return type;
    }

    public void uniPressed(MouseEvent keyEvent) {
        disableOtherButtons(unipedal);
    }

    public void biPressed(MouseEvent keyEvent) {
        disableOtherButtons(bipedal);
    }

    public void quadPressed(MouseEvent keyEvent) {
        disableOtherButtons(quadrupedal);
    }

    public void aracPressed(MouseEvent keyEvent) {
        disableOtherButtons(arachnid);
    }

    public void radialPressed(MouseEvent keyEvent) {
        disableOtherButtons(radial);
    }

    public void aeroPressed(MouseEvent keyEvent) {
        disableOtherButtons(aeronautical);
    }
}
