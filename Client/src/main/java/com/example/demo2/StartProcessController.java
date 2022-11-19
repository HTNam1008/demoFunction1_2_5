package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartProcessController {
    @FXML
    private TextField ProcessStartName;

    public void SetStartProcess(ActionEvent event) throws IOException {
        String processName=ProcessStartName.getText();
        if (processName.isEmpty())
        {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Please enter process name!");
            alert1.showAndWait();
        }
        else {
            HelloController.dout.writeUTF("ProcessRunningStart"+"/"+processName);
            HelloController.dout.flush();
            String line=HelloController.din.readUTF();
            if (line.equals("NO"))
            {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Cant start process!");
                alert1.showAndWait();
            }
            else {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Start "+processName+" Successfully!");
                alert1.showAndWait();
            }
        }
    }
}
