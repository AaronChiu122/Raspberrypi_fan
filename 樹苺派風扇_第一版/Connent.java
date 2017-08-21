//package firebase;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.magiclen.magiccommand.Command;

class Connent extends Thread {
    String[] key ; 
    Date date;
    String url = "https://washcar-7a62e.firebaseio.com/IOT-Pi";
    Firebase dataRef = new Firebase(url);
    Connent() {
        rec();
        FireBase.GoCommand();
    }
    static String fanState,mode,temp="0",onTemp="0",offTemp="0";
    
    static final GpioController gpio = GpioFactory.getInstance();
    static final GpioPinDigitalOutput pin0 = 
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "00", PinState.HIGH); // ON/OFF
    
    void rec(){
        
            dataRef.child("fanState").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    fanState = snapshot.getValue().toString();
                    System.out.println("fanState : "+fanState);
                }
                public void onCancelled() {
                    System.err.println("Listener was cancelled");
                }
                @Override
                public void onCancelled(FirebaseError fe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            dataRef.child("temp").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    temp = snapshot.getValue().toString();
                    System.out.println("temp : "+temp);
                    
                }
                public void onCancelled() {
                    System.err.println("Listener was cancelled");
                }
                @Override
                public void onCancelled(FirebaseError fe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            dataRef.child("onTemp").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    onTemp = snapshot.getValue().toString();
                    System.out.println("onTemp : "+onTemp);
                    
                }
                public void onCancelled() {
                    System.err.println("Listener was cancelled");
                }

                @Override
                public void onCancelled(FirebaseError fe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            dataRef.child("offTemp").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    offTemp = snapshot.getValue().toString();
                    System.out.println("offTemp : "+offTemp);
                    
                }
                public void onCancelled() {
                    System.err.println("Listener was cancelled");
                }

                @Override
                public void onCancelled(FirebaseError fe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            dataRef.child("mode").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    mode = snapshot.getValue().toString();
                    System.out.println("mode : "+mode);
                    
                }
                public void onCancelled() {
                    System.err.println("Listener was cancelled");
                }

                @Override
                public void onCancelled(FirebaseError fe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
    }
    public void run() {
      
        try{
            sleep(10000);
            while(true){
                Double xtemp = Double.valueOf(temp);
                Double xonTemp = Double.valueOf(onTemp);
                Double xoffTemp = Double.valueOf(offTemp);


                if(mode.equals("manual")){//手動
//                    System.out.println("手動模式啟動");
                    if(fanState.equals("0")){
                        pin0.low();
//                        System.out.println("手動關");
                    }if(fanState.equals("1")){
//                        System.out.println("手動開");
                        pin0.high();
                    }
                }
                if(mode.equals("auto")){//自動
//                    System.out.println("自動模式啟動");
                    if(xtemp > xonTemp){//手機要做檢查
                        pin0.high();
//                        System.out.println("自動開");
                        dataRef.child("fanState").setValue(1);
                    }
                    if(xtemp < xoffTemp){
                        pin0.low();
//                        System.out.println("自動關");
                        dataRef.child("fanState").setValue(0);
                    }

                }
                sleep(1000);
                
            } 
        }catch(Exception ex){
            System.out.println("沒連到網路LINE182");
                FireBase.cn=null;
                FireBase.cn=new Connent();
//                FireBase.cn.stop();
                FireBase.cn.start();
//                new Connent().start();
        }
        
    }
}


        


   
      
   
