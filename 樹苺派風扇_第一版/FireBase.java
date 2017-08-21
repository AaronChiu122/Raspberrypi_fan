//package firebase;
//import com.pi4j.io.gpio.GpioController;
//import com.pi4j.io.gpio.GpioFactory;
//import com.pi4j.io.gpio.GpioPinDigitalOutput;
//import com.pi4j.io.gpio.PinState;
//import com.pi4j.io.gpio.RaspiPin;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.magiclen.magiccommand.Command;
import org.magiclen.magiccommand.CommandListener;



public class FireBase{
//    static final GpioController gpio = GpioFactory.getInstance();
//    static final GpioPinDigitalOutput pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "02", PinState.HIGH); // ON/OFF
//    static final GpioPinDigitalOutput pin0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "00", PinState.HIGH); // rest

    
    public final static String commandString = "sudo /home/pi/Adafruit_Python_DHT/examples/AdafruitDHT.py 11 4";
    public static  Command command = new Command(FireBase.commandString);
    static Connent cn = new Connent();
    
    public static void main(String args[]) throws Exception {

        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");

        try{
            cn.start();
            sleep(2000);
            while(true){
                dNow=null;
                dNow = new Date( );
                cn.dataRef.child("Time").setValue(ft.format(dNow));
                sleep(10000);
            }
    
        }catch(Exception ex){
            System.out.print(ex.toString());
        }
        

    }
    public static void GoCommand(){
        command.setCommandListener(new CommandListener() {
 
	    @Override
	    public void commandStart(final String id) {
	    }
 
	    @Override
	    public void commandRunning(final String id, final String message, final boolean isError) {
//	        System.out.println(message);
//                System.out.println("InRunning");
	    }
 
	    @Override
	    public void commandException(final String id, final Exception exception) {
//	        exception.printStackTrace(System.out);
//                System.out.println("InException");
                command.runAsync("first");
	    }
 
	    @Override
	    public void commandEnd(final String id, final int returnValue) {
                try{
                    System.out.println("沒連線");
                    sleep(5000);
                    command.runAsync("first");
                }catch(Exception e){
                    
                }
                
                
	    }
	});
        
        command.runAsync("first");
    }
    public static void StopCommand(){
        command.stop("first");
    }

//  public static void washCarStart(){
//   
//        pin2.low();
//        pin0.high();
//        System.out.println("<--Pi4J--> GPIO 02 ... started.");  
//        FireBaseOfTimer timer  = new FireBaseOfTimer(60); //setTime
//        timer.start();
//           
//  }
    
}
