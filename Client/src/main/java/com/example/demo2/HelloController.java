package com.example.demo2;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Optional;


import java.io.IOException;

public class HelloController {

    Stage stageProcess,stageApp,stageKeystroke,stageScreenshot,stageShutdown;
    @FXML

    public void moveToProcessScene(ActionEvent event) throws IOException {
        if (connectServer!=null) {
            stageProcess = new Stage();
            stageProcess.setTitle("Process");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProcessRunningSample.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stageProcess.setScene(scene);
            stageProcess.show();
        }
        else {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Error: Not connected server");
            alert1.showAndWait();
        }
    }
    @FXML
    public void moveToAppScene(ActionEvent event) throws IOException {
        if (connectServer!=null) {
            stageApp = new Stage();
            stageApp.setTitle("Process");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AppRunningSample.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stageApp.setScene(scene);
            stageApp.show();
        } else {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Error: Not connected server");
            alert1.showAndWait();
        }
    }

    @FXML
    public void moveToKeyStrokeScene(ActionEvent event) throws IOException {
        if (connectServer!=null) {
            stageKeystroke = new Stage();
            stageKeystroke.setTitle("Process");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("KeystrokeSample.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stageKeystroke.setScene(scene);
            stageKeystroke.show();
        } else {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Error: Not connected server");
            alert1.showAndWait();
        }
    }

    @FXML
    public void moveToScreenshotScene(ActionEvent event) throws IOException {
        if (connectServer!=null) {
            stageScreenshot = new Stage();
            stageScreenshot.setTitle("Process");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ScreenshotSample.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stageScreenshot.setScene(scene);
            stageScreenshot.show();
        } else {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Error: Not connected server");
            alert1.showAndWait();
        }
    }

    @FXML
    public void moveToShutdownScene(ActionEvent event) throws IOException{
        if (connectServer!=null) {
            stageShutdown = new Stage();
            stageShutdown.setTitle("ShutDown");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Shutdown.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stageShutdown.setScene(scene);
            stageShutdown.show();

        } else {
            Alert alert1=new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("Error: Not connected server");
            alert1.showAndWait();
        }
    }

    @FXML
    private TextField ServerID;

    @FXML
    private Button connectButton;


    @FXML
    public void connectDisable(KeyEvent event) throws IOException
    {
        ServerID.textProperty().addListener((observable,oldValue,newValue)->{
            connectButton.setDisable(newValue.trim().isEmpty());
        });
    }

    /*@FXML
    public void connectServer(ActionEvent event) throws IOException{
        Alert alert1=new Alert(Alert.AlertType.INFORMATION);
        alert1.setContentText("Server successfully connected!");
        alert1.show();

    }*/


    static Socket connectServer=null;
    static DataInputStream din;
    static DataOutputStream dout;

    @FXML
    private Button disconnectButton;

    @FXML
    public void setconnectButton(ActionEvent event)
    {
        try {
            String ipAddr = ServerID.getText();
            connectServer = new Socket(ipAddr, 3333);

            din = new DataInputStream(connectServer.getInputStream());
            dout = new DataOutputStream(connectServer.getOutputStream());

            String line = "";
            if ((line = din.readUTF()).equals("OK")) {
                disconnectButton.setDisable(false);
                //System.out.println("connected");
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Server successfully connected!");
                alert1.setTitle("Client");
                alert1.showAndWait();
            }
        }
        catch (Exception a){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Cant connect to server!");
                alert1.showAndWait();
        }
    }



    @FXML
    public void setDisconnectButton(ActionEvent event) throws IOException
    {
       dout.writeUTF("stop");
       dout.flush();
       connectServer.close();
       connectServer=null;
    }



}