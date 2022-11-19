package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Controller {

    ServerSocket ss;
    Socket clientConnect;
    DataInputStream din;
    DataOutputStream dout;
    public String line;

    public void waitMessageFromClient() throws IOException {
        din = new DataInputStream(clientConnect.getInputStream());
        dout = new DataOutputStream(clientConnect.getOutputStream());
        dout.writeUTF("OK");
        dout.flush();
        System.out.println("connected");
        line = "";
        while (!line.equals("stop")) {
            line = din.readUTF();
            String[] x=line.split("/");
            System.out.println("client says:" + line);
            if (line.equals("stop")) {
                break;
            }

            if (line.equals("AppRunningList")) {
                getListRunningApp();
            } else if (/*line.substring(0, line.indexOf(" ")).equals("AppRunningStart")*/ x[0].equals("AppRunningStart")) {
                //String appName = line.substring(line.indexOf(" ") + 1, line.length());
                String appName=x[1];
                System.out.println(appName);
                //System.out.println(line.substring(0, line.indexOf(" ")));
                startApp(appName);
                dout.writeUTF("OK");
                dout.flush();
            } else if (x[0].equals("AppRunningKill")) {
                String id = x[1];
                if (killApp(id)) {
                    dout.writeUTF("OK");
                } else {
                    dout.writeUTF("NO");
                }
                dout.flush();
            }

            else if (line.equals("ProcessRunningList")) {
                getListRunningProcess();
            } else if (x[0].equals("ProcessRunningStart")) {
                String processName = x[1];
                System.out.println(processName);
                startProcess(processName);
                dout.writeUTF("OK");
                dout.flush();
            } else if (x[0].equals("ProcessRunningKill")) {
                String id = x[1];
                if (killProcess(id)) {
                    dout.writeUTF("OK");
                } else {
                    dout.writeUTF("NO");
                }
                dout.flush();
            }else if (x[0].equals("Shutdown"))
            {
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setContentText("Computer will shutdown in 5s");
                alert1.showAndWait();
                //Runtime.getRuntime().exec("cmd /c shutdown -s -t 5");
            }
        }

        din.close();
        ss.close();
        clientConnect.close();
    }

    @FXML
    private Button exitButton;
    @FXML
    private Button openServerButton;

    public void setOpenServerButton(ActionEvent e) {
        try {
            ss = new ServerSocket(3333);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Server is opened !");
            alert.setTitle("Server");

            Optional<ButtonType> option = alert.showAndWait();
            clientConnect = ss.accept();

            if (option.get() == ButtonType.OK) {
                waitMessageFromClient();
            }

        } catch (IOException ioe) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cant connect with client!");
            alert.setTitle("Server");
            alert.showAndWait();
        }
    }

    public void setExitButton(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    static boolean check(List<String> s, String st) {
        for (int i = 0; i < s.size(); i++) {
            if (st.equals(s.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void getListRunningApp() throws IOException {
        List<String> appRunning = new ArrayList<String>();
        String appName, pid;
        String br;
        Process p = Runtime.getRuntime().exec("tasklist /v /nh /fo csv");
        BufferedReader input = new BufferedReader
                (new InputStreamReader(p.getInputStream()));
        while ((br = input.readLine()) != null) {
            //System.out.println(line+'\n');
            String[] parse = br.split(",");
            appName = parse[parse.length - 1];
            pid = parse[1];
            pid = pid.substring(1, pid.length()-1);
            appName = appName.substring(1, appName.length() - 1);
            if (!appName.equals("N/A") && check(appRunning, appName)) {
                dout.writeUTF(appName + "/" + pid);
                dout.flush();
                appRunning.add(appName);
            }
        }
        dout.writeUTF("Done");
        dout.flush();
        input.close();
    }

    public void startApp(String serviceName) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + serviceName + ".exe");
            System.out.println(serviceName + " started successfully!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean killApp(String id) {
        boolean isValid = false;
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist /v /nh /fi \"PID eq " + id + "\" /fo csv");
            BufferedReader input = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {

                isValid = true;
                String[] parse = line.split(",");
                String processName = parse[0];
                processName = processName.substring(1, processName.length() - 1);
                //System.out.println(processName+'\n');
                try {
                    Runtime.getRuntime().exec("taskkill /IM " + processName);
                    System.out.println(processName + " killed successfully!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            input.close();
        } catch (Exception err) {
            isValid = false;
        }
        return isValid;
    }

    public void getListRunningProcess() throws IOException {
        String br;
        Process process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while ((br = input.readLine()) != null) {
            String[] parse = br.split(",");
            String processName = parse[0];
            String pid = parse[1];
            pid = pid.substring(1, pid.length() - 1);
            processName = processName.substring(1, processName.length() - 1);
            //System.out.println(processName+" "+pid);
            dout.writeUTF(processName + "/" + pid);
            dout.flush();
        }

        dout.writeUTF("Done");
        dout.flush();
        input.close();
    }

    public void startProcess(String nameProcess) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + nameProcess);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Cann't start process!");
            alert.setTitle("alert");
            alert.showAndWait();
        }
    }

    public boolean killProcess(String pid) {
        boolean isValid = false;
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist /v /nh /fi \"PID eq " + pid + "\" /fo csv");
            BufferedReader input = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {

                isValid = true;
                Runtime.getRuntime().exec("taskkill /PID " + pid + " /F");
                System.out.println("Process killed successfully!");
            }
        } catch (IOException e) {
            isValid = false;
        }
        return isValid;
    }
}