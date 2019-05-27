package com.servlet.machinestate;

import com.servlet.service.MachineService;

import java.util.HashMap;

public class NoMoneyState implements MachineState {

    private MachineService machineService;

    public NoMoneyState(MachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public String insertMoney(String amount) {
        return this.machineService.addMoney(amount);
    }

    @Override
    public HashMap<String, Object> dispenseProduct(int productNumber) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "You did not input money. Please try again.");
        return response;
    }

    @Override
    public HashMap<String, Object> reset() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
}
