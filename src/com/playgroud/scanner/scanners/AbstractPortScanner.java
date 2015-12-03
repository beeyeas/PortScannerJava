package com.playgroud.scanner.scanners;

import java.util.concurrent.Callable;

import com.playgroud.scanner.model.PortScannerResult;
import com.playgroud.scanner.model.PortScannerTuple;

public abstract class AbstractPortScanner implements Callable<PortScannerResult> {

    protected static final  int TIMEOUT = 200;
    protected PortScannerTuple currentPortScannerTuple;
}
