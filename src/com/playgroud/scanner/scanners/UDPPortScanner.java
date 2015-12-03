package com.playgroud.scanner.scanners;

import java.net.DatagramSocket;

import com.playgroud.scanner.model.PortScannerResult;
import com.playgroud.scanner.model.PortScannerTuple;

public class UDPPortScanner extends AbstractPortScanner {
    public UDPPortScanner(PortScannerTuple tuple) {
        this.currentPortScannerTuple = tuple;
    }

    @Override
    public PortScannerResult call() throws Exception {

        try {
            DatagramSocket server = new DatagramSocket(currentPortScannerTuple.getPort());
            server.close();
            return new PortScannerResult(currentPortScannerTuple, true);
        } catch (Exception ex) {
            return new PortScannerResult(currentPortScannerTuple, false);
        }
    }
}
