package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class shutDown {
    @FXML
    private Button yes;
    @FXML
    private Button no;


    public void ShutDown() {
        try {
            HelloController.dout.writeUTF("Shutdown");
            HelloController.dout.flush();
            Stage stage = (Stage) no.getScene().getWindow();
            stage.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void cancel() {
        Stage stage = (Stage) no.getScene().getWindow();
        stage.close();
    }
}
