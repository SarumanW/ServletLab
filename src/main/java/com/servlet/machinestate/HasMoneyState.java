package com.servlet.machinestate;

import com.servlet.exceptions.ChangeAvailableException;
import com.servlet.exceptions.NoProductException;
import com.servlet.exceptions.NotEnoughMoneyException;
import com.servlet.exceptions.NotSufficientChangeException;
import com.servlet.service.MachineService;

import java.util.HashMap;

public class HasMoneyState implements MachineState {

    private MachineService machineService;

    public HasMoneyState(MachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public String insertMoney(String amount) {
        return this.machineService.addMoney(amount);
    }

    @Override
    public HashMap<String, Object> dispenseProduct(int productNumber) {
        HashMap<String, Object> response = new HashMap<>();

        try {
            this.machineService.makePurchase(productNumber);
            response.put("success", true);
        } catch (NoProductException | NotEnoughMoneyException | NotSufficientChangeException ex) {
            response.put("success", false);
            response.put("message", ex.getMessage());
        } catch (ChangeAvailableException ex) {
            response.put("success", true);
            response.put("change", ex.getChange());
            response.put("message", ex.getMessage());
        }

        return response;
    }

    @Override
    public HashMap<String, Object> reset() {
        HashMap<String, Object> response = new HashMap<>();

        try {
            this.machineService.returnChangeWithoutPurchase();
        } catch (NotSufficientChangeException ex) {
            response.put("success", false);
            response.put("message", ex.getMessage());
        } catch (ChangeAvailableException ex) {
            response.put("success", true);
            response.put("change", ex.getChange());
            response.put("message", ex.getMessage());
        }

        return response;
    }
}
