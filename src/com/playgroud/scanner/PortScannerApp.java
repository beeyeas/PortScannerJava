package com.playgroud.scanner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.playgroud.scanner.model.PortScannerResult;
import com.playgroud.scanner.model.PortScannerTuple;
import com.playgroud.scanner.scanners.TCPPortScanner;
import com.playgroud.scanner.scanners.UDPPortScanner;
import com.playgroud.scanner.util.PortScannerUtil;

/**
 * PortScannerApp main entry point
 * @author bharath
 *
 */
public class PortScannerApp {

    protected static ExecutorService portScannerExecutorService = Executors.newFixedThreadPool(20);
    private static final Logger logger = Logger.getLogger(PortScannerApp.class.getName());

    /**
     * Check with respective port(portScannerTuple) is open.
     * Spawns a thread of protocol type.
     * @param portScannerTuple
     * @return
     */
    public Future<PortScannerResult> IsPortOpen(PortScannerTuple portScannerTuple) {
        if (portScannerTuple.getIsUDP()){
            return portScannerExecutorService.submit(new UDPPortScanner(portScannerTuple));
        }
        return portScannerExecutorService.submit(new TCPPortScanner(portScannerTuple));
    }

    public List<PortScannerResult> scan(ArrayList<PortScannerTuple> portScannerInputList) throws Exception {
        if (portScannerInputList == null) {
            throw new Exception("Empty input passed");
        }
        List<Future<PortScannerResult>> futureList = new ArrayList<Future<PortScannerResult>>();
        for (PortScannerTuple portScannerTuple : portScannerInputList) {
            futureList.add(IsPortOpen(portScannerTuple));
        }
        List<PortScannerResult> resultList = new ArrayList<PortScannerResult>();
        for (Future<PortScannerResult> future : futureList) {
            PortScannerResult result = future.get();
            resultList.add(result);
        }
        cleanup();
        return resultList;
    }

    public boolean cleanup() {
        if (!portScannerExecutorService.isShutdown())
            portScannerExecutorService.shutdown();
        return true;
    }
    
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        
        logger.setUseParentHandlers(false);
        Handler conHdlr = new ConsoleHandler();
        conHdlr.setFormatter(new Formatter() {
          public String format(LogRecord record) {
            return record.getLevel() + "  :  "
                + record.getSourceClassName() + " -:- "
                + record.getSourceMethodName() + " -:- "
                + record.getMessage() + "\n";
          }
        });
        logger.addHandler(conHdlr);
        List<PortScannerResult> scanResult = null;
        try {
            ArrayList<PortScannerTuple> inputList = PortScannerUtil.loadConfig();
            //ArrayList<PortScannerTuple> inputList = PortScannerUtil.loadDefaultList(PortScannerUtil.UDP);
            PortScannerApp portScanner = new PortScannerApp();
            
             scanResult = portScanner.scan(inputList);

            for (PortScannerResult result : scanResult){
                if (result.getIsPortOpen()){
                    logger.log(Level.INFO, result.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            long timeTakeInMillis = System.nanoTime() - startTime;
            double seconds = ((double)timeTakeInMillis / 1000000000);
            logger.log(Level.INFO, "Process completed see below results : \nTotal ports scanned " + scanResult.size() + "\nTotal time taken : " + new DecimalFormat("#.##########").format(seconds) + " seconds");
        }
    }
}
