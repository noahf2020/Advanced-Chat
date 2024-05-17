package com.example.demo3;

import com.example.CommunicationData;
import javafx.application.Platform;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ProgramLogic implements Runnable{

    static ArrayList<ObjectOutputStream> manyOuts =  new ArrayList<>();
    MyCoolDataStructure inData;
   HelloController theController;
   Boolean serverMode;
    public ProgramLogic(MyCoolDataStructure inData, HelloController theController, ObjectOutputStream ObjOut) {
        this.theController = theController;
        this.inData = inData;
        manyOuts.add(ObjOut);
        serverMode = false;
    }

    public void run(){
        CommunicationData inMessage1 = (CommunicationData) inData.get();
        while(true){
            if(inMessage1!= null){
                //add yo lis
                if(theController != null){

                    CommunicationData finalMessage = inMessage1;

                    Platform.runLater(new Runnable(){
                        @Override
                        public void run(){
                            theController.allMessages.getItems().add(finalMessage.getMessage());
                        }
                    });



                }else{
                    System.out.println(inMessage1.getMessage());
                }
                try{
                    if(serverMode){
                        for(ObjectOutputStream eachOut: manyOuts){
                            if(inMessage1.getTo() == "name"){

                                eachOut.writeObject(inMessage1);
                            }

                        }
                    }
                }catch(Exception ex){

                }

            }
            inMessage1 = (CommunicationData)inData.get();
        }

    }
}
