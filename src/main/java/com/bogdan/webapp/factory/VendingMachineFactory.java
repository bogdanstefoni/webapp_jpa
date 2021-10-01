package com.bogdan.webapp.factory;

import com.bogdan.webapp.service.IVendingMachine;
import com.bogdan.webapp.service.VendingMachineImpl;

public class VendingMachineFactory {
    public static IVendingMachine createVendingMachine() {
        return new VendingMachineImpl();
    }
}
