package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartAppController {

    @FXML
    private TextField AppStartName;

    public void SetStartApp(ActionEvent event) throws IOException {
        String appName=AppStartName.getText();
        if (appName.isEmpty())
        {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Please enter application name!");
            alert1.showAndWait();
        }
        else {
            HelloController.dout.writeUTF("AppRunningStart"+"/"+appName);
            HelloController.dout.flush();
            String line=HelloController.din.readUTF();
            if (line.equals("NO"))
            {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Cant start application!");
                alert1.showAndWait();
            }
            else {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Start "+appName+" Successfully!");
                alert1.showAndWait();
            }
        }
    }

}
