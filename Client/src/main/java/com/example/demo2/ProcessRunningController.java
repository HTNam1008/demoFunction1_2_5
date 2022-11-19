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

import java.io.IOException;

public class ProcessRunningController {

    @FXML
    public void moveToKillProcess(ActionEvent event) throws IOException {
        Stage stage3=new Stage();
        stage3.setTitle("Kill");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("KillProcess.fxml"));
        Scene scene3 = new Scene(fxmlLoader.load());
        stage3.setScene(scene3);
        stage3.show();
    }

    @FXML
    public void moveToStartProcess(ActionEvent event) throws IOException {
        System.out.println("Hello1");
        Stage stage4=new Stage();
        stage4.setTitle("Start");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartProcess.fxml"));
        Scene scene4 = new Scene(fxmlLoader.load());
        stage4.setScene(scene4);
        stage4.show();
    }

    public class processDetail {
        SimpleStringProperty processName;
        SimpleStringProperty pid;
        processDetail(String processName,String pid)
        {
            this.processName= new SimpleStringProperty(processName);
            this.pid= new SimpleStringProperty(pid);
        }

        public String getProcessName()
        {
            return processName.get();
        }

        public void setProcessName(String fProcessName)
        {
            processName.set(fProcessName);
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
    private TableView<processDetail> listProcessTable ;
    @FXML
    private TableColumn<processDetail,String> nameProcess;
    @FXML
    private TableColumn<processDetail,String>pidProcess;

    @FXML
    public void getListRunningProcess(ActionEvent event) throws IOException {
        HelloController.dout.writeUTF("ProcessRunningList");
        HelloController.dout.flush();

        //listIsEmpty(listProcessTable);
        listProcessTable.getItems().clear();

        nameProcess.setCellValueFactory(new PropertyValueFactory<>("processName"));
        pidProcess.setCellValueFactory(new PropertyValueFactory<>("pid"));

        String processName,pid;
        String line;
        while (!(line=HelloController.din.readUTF()).equals("Done")) {
            //System.out.println(line+'\n');
            String[] x = line.split("/");
            processName = x[0];
            pid = x[1];
            listProcessTable.getItems().add(new processDetail(processName, pid));
        }
        System.out.println("Done");
    }

    public void listIsEmpty(TableView<processDetail> listProcessTable) {

    }
}


