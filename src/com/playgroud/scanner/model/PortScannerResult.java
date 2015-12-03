package com.playgroud.scanner.model;

public class PortScannerResult {
    private PortScannerTuple portScannerTuple;
    private Boolean isPortOpen;
    
    public PortScannerResult(PortScannerTuple portScannerTuple, Boolean isPortOpen) {
        this.portScannerTuple = portScannerTuple;
        this.isPortOpen = isPortOpen;
    }

    public PortScannerTuple getPortScannerTuple() {
        return portScannerTuple;
    }

    public void setPortScannerTuple(PortScannerTuple portScannerTuple) {
        this.portScannerTuple = portScannerTuple;
    }

    public Boolean getIsPortOpen() {
        return isPortOpen;
    }

    public void setIsPortOpen(Boolean isPortOpen) {
        this.isPortOpen = isPortOpen;
    }
    @Override
    public String toString() {
        return portScannerTuple.toString() + " - " + isPortOpen;
    }
}
