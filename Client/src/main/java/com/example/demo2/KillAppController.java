package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class KillAppController {

    @FXML
    private TextField KillAppPid;

    @FXML
    public void setKillApp(ActionEvent event) throws IOException {
        String pid=KillAppPid.getText();
        if (pid.isEmpty())
        {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Please enter application PID!");
            alert1.show();
        }
        else {
            HelloController.dout.writeUTF("AppRunningKill "+"/"+pid);
            HelloController.dout.flush();
            String line=HelloController.din.readUTF();
            if (line.equals("NO"))
            {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Cant kill application!");
                alert1.showAndWait();
            }
            else {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Kill app successfully!");
                alert1.showAndWait();
            }
        }


    }

}
