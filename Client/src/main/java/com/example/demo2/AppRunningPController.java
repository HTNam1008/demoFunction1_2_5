package com.example.demo2;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

import java.io.IOException;

public class AppRunningPController {
    @FXML
    public void moveToKillApp(ActionEvent event) throws IOException {
        Stage stage5_1=new Stage();
        stage5_1.setTitle("Kill");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("KillApp.fxml"));
        Scene scene5_1 = new Scene(fxmlLoader.load());
        stage5_1.setScene(scene5_1);
        stage5_1.show();
    }

    @FXML
    public void moveToStartApp(ActionEvent event) throws IOException {
        Stage stage5_2=new Stage();
        stage5_2.setTitle("Start");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartApp.fxml"));
        Scene scene5_2 = new Scene(fxmlLoader.load());
        stage5_2.setScene(scene5_2);
        stage5_2.show();
    }
    public class appDetail {
        SimpleStringProperty appName;
        SimpleStringProperty pid;
        appDetail(String appName,String pid)
        {
            this.appName= new SimpleStringProperty(appName);
            this.pid= new SimpleStringProperty(pid);
        }
        public String getAppName()
        {
            return appName.get();
        }

        public void setAppName(String fAppName)
        {
            appName.set(fAppName);
        }

        public  String getPid()
        {
            return pid.get();
        }

        public void  setPid(String fPid)
        {
            pid.set(fPid);
        }

    }

    @FXML
    private TableView<appDetail>ListAppTable;
    @FXML
    private TableColumn<appDetail,String>nameApp;
    @FXML
    private TableColumn<appDetail,String>pidApp;

    @FXML
    public void getListRunningApp(ActionEvent event) throws IOException {
        HelloController.dout.writeUTF("AppRunningList");
        HelloController.dout.flush();

        ListAppTable.getItems().clear();

        nameApp.setCellValueFactory(new PropertyValueFactory<>("appName"));
        pidApp.setCellValueFactory(new PropertyValueFactory<>("pid"));

        String appName,pid;
        String line;
        while (!(line=HelloController.din.readUTF()).equals("Done"))
        {
            String[] x=line.split("/");
            appName=x[0];
            pid=x[1];
            ListAppTable.getItems().add(new appDetail(appName,pid));
        }
        System.out.println("Done");
    }

}
