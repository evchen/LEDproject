/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ledproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Enumeration;

/**
 *
 * @author eve
 */
public class ArduinoComm implements SerialPortEventListener {

    /**
     * @param args the command line arguments
     */
    SerialPort serialPort;

    private static final String PORT_NAMES[] = {
        "/dev/ttyACM3", // Linux
        "/dev/ttyUSB1",
        "COM3" // Windows
    };
    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    static String messageString = "FF00FF";
    static byte[] array = new byte[]{0, 0, -1};

    public void initialize() {

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find port.");
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();;
            serialPort.close();
        }
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                System.out.println(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    public void comm(int red, int green, int blue) {
        try {
            

            output.write(convert(red, green, blue));
            System.out.println(array);

            //output.close();
            //serialPort.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public byte[] convert(int red, int green, int blue) {
        byte[] array = new byte[3];
        byte[] bytes = java.nio.ByteBuffer.allocate(4).putInt(red).array();
        array[0] = bytes[3];
        bytes = java.nio.ByteBuffer.allocate(4).putInt(green).array();
        array[1] = bytes[3];
        bytes = java.nio.ByteBuffer.allocate(4).putInt(blue).array();
        array[2] = bytes[3];
        return array;
    }

}
