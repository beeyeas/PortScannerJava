package com.playgroud.scanner.scanners;

import java.net.InetSocketAddress;
import java.net.Socket;

import com.playgroud.scanner.model.PortScannerResult;
import com.playgroud.scanner.model.PortScannerTuple;

public class TCPPortScanner extends AbstractPortScanner {
    public TCPPortScanner(PortScannerTuple tuple) {
        this.currentPortScannerTuple = tuple;
    }
    
    @Override
    public PortScannerResult call() throws Exception {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(currentPortScannerTuple.getIpAddress(), currentPortScannerTuple.getPort()), TIMEOUT);
            socket.close();
            return new PortScannerResult(currentPortScannerTuple, true);
        } catch (Exception ex) {
            return new PortScannerResult(currentPortScannerTuple, false);
        }
    }
}
