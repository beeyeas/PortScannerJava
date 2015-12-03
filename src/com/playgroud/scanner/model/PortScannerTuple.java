package com.playgroud.scanner.model;

public class PortScannerTuple{
    private String ipAddress;
    private Integer port;
    private Boolean isUDP;
    
    public PortScannerTuple(String ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public Integer getPort() {
        return port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }
    
    public Boolean getIsUDP() {
        return isUDP;
    }

    public void setIsUDP(Boolean isUDP) {
        this.isUDP = isUDP;
    }

    @Override
    public String toString() {
        String protocol = isUDP ? "UDP" : "TCP";
        return ipAddress + ":" +protocol +":"  +port;
    }
}
