package com.playgroud.scanner.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.playgroud.scanner.model.PortScannerTuple;

/**
 * 
 * Helper PortScannerUtil
 *
 */
public class PortScannerUtil {
    
    public static final String UDP = "udp";
    public static final String TCP = "tcp";
    /*
     * Load all TCP :<ports> range  
     */
    public static ArrayList<PortScannerTuple> loadDefaultList(String protocol){
        ArrayList<PortScannerTuple> portScannerInputList = new ArrayList<PortScannerTuple>();
        try {
            for (int i = 1; i <= 65536; i++) {
                PortScannerTuple tuple = new PortScannerTuple("127.0.0.1", i);
                if (protocol.equalsIgnoreCase(UDP)){
                    tuple.setIsUDP(true);
                }else{
                    tuple.setIsUDP(false);
                }
                portScannerInputList.add(tuple);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portScannerInputList; 
    }
    
    public static ArrayList<PortScannerTuple> loadConfig() throws Exception{
        ArrayList<PortScannerTuple> inputList = new ArrayList<>();
        String portRange = null;
        String ipAddress = "127.0.0.1";//default lo
        String protocol = TCP;
        
        Properties properties = new Properties();
        
        try {
            properties.load(new FileInputStream("config.properties"));
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                if (key.equalsIgnoreCase("portrange")){
                    portRange = value;
                }
                if (key.equalsIgnoreCase("ipaddress")){
                    ipAddress = value;
                }
                if (key.equalsIgnoreCase("protocol")){
                    if (value.equalsIgnoreCase(TCP) || (value.equalsIgnoreCase(UDP))){
                        protocol = value;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
        
        try{
            if (portRange!=null){
                String startPort = null;
                String endPort = null;

                String[] portParser = portRange.split("-");
                if (portParser.length == 2){
                    startPort = portParser[0];
                    endPort = portParser[1];
                }
                if ((startPort==null) || (endPort ==null)) throw new Exception("Invalid values specified in config.properties");
                int startPortInt = Integer.parseInt(startPort);
                int endPortInt = Integer.parseInt(endPort);
                if (startPortInt >= endPortInt){
                    int temp = startPortInt;
                    startPortInt = endPortInt;
                    endPortInt = temp;
                }
                for (int port=startPortInt ; port<=endPortInt ; port++){
                    PortScannerTuple tuple = new PortScannerTuple(ipAddress, port);
                    if (protocol.equalsIgnoreCase(UDP)){
                        tuple.setIsUDP(true);
                    }else if (protocol.equalsIgnoreCase(TCP)){
                        tuple.setIsUDP(false);
                    }
                    inputList.add(tuple);
                }
            }
        }catch(Exception e){
            throw e;
        }
        return inputList;
    }
}
