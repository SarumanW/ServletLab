package com.servlet.machinestate;

import java.util.HashMap;

public interface MachineState {
    String insertMoney(String amount);

    HashMap<String, Object> dispenseProduct(int productNumber);

    HashMap<String, Object> reset();
}
