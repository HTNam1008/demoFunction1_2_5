package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class KillProcessController {
    @FXML
    private TextField killProcessPid;

    @FXML
    public void setKillProcessPid(ActionEvent event) throws IOException {
        String pid=killProcessPid.getText();
        if (pid.isEmpty())
        {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Please enter process PID!");
            alert1.show();
        }
        else {
            HelloController.dout.writeUTF("ProcessRunningKill"+"/"+pid);
            HelloController.dout.flush();
            String line=HelloController.din.readUTF();
            if (line.equals("NO"))
            {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Cant kill process!");
                alert1.showAndWait();
            }
            else {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Kill process successfully!");
                alert1.showAndWait();
            }
        }


    }
}
