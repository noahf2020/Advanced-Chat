package com.example.demo3;
import com.example.CommunicationData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;


public class HelloController {
    @FXML
    private Label welcomeText;

    public String IP;
    public TextField IPField;
    public TextField Sender;
    public TextField Receiver;
    public TextField Message;
    public ListView<Object> allMessages;

    public Button saveIpButton;
    public Button sendMessage;
    ObjectOutputStream objOut;
    ObjectInputStream objIn;
    HelloController Controller;
    ClientConnection newClient;

    public void initialize() throws Exception {

        int port = 3256;

        System.out.println("connecting to server");
        Socket newSocket = new Socket("10.37.157.240",3256);
        newClient = new ClientConnection(newSocket);
//        OutputStream out =  newSocket.getOutputStream();
//        objOut = new ObjectOutputStream(out);
      //  objOut.writeObject("Hi Mr. Hernandez, Noah");



        MyCoolDataStructure Queue = new MyCoolDataStructure();
        DataReader myReader = new DataReader(newClient,Queue);
        ProgramLogic logic = new ProgramLogic(Queue, this, newClient.getObjOut());
        Thread DataReadThread = new Thread(myReader);
        Thread logicThread = new Thread(logic);
        DataReadThread.start();
        logicThread.start();

        CommunicationData indentify = new CommunicationData("Noah","SERVER","ID",0);
        newClient.getObjOut().writeObject(indentify);
//        InputStream in = newSocket.getInputStream();
//        objIn  = new ObjectInputStream(in);
//        Object message = objIn.readObject();
//        System.out.println(message);
    }
    public void saveIp() {

     IP = IPField.getText();
     System.out.println(IP);
 }

 public void sendMessageFunc() throws IOException {
    String newMessage = Message.getText();
     CommunicationData data1 = new CommunicationData("Noah","ALL","Hello",0);
      newClient.getObjOut().writeObject(data1);


 }

}