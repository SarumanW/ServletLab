package com.servlet.machinestate;

import com.servlet.entity.Product;
import com.servlet.service.MachineService;

import java.util.HashMap;
import java.util.List;

public class MachineContext implements MachineState {
    private MachineState state;
    private static MachineService machineService;
    private static MachineState noMoneyState;
    private static MachineState hasMoneyState;

    static {
        machineService = new MachineService();
        noMoneyState = new NoMoneyState(machineService);
        hasMoneyState = new HasMoneyState(machineService);
    }

    public MachineContext() {
        this.state = noMoneyState;
    }

    @Override
    public String insertMoney(String amount) {
        String denomination = this.state.insertMoney(amount);

        if (this.state instanceof NoMoneyState) {
            this.state = hasMoneyState;
        }

        return denomination;
    }

    @Override
    public HashMap<String, Object> dispenseProduct(int productNumber) {
        HashMap<String, Object> response;

        response = this.state.dispenseProduct(productNumber);

        if (this.state instanceof HasMoneyState) {
            this.state = noMoneyState;
        }

        return response;
    }

    @Override
    public HashMap<String, Object> reset() {
        HashMap<String, Object> response;

        response = this.state.reset();

        if (this.state instanceof HasMoneyState) {
            this.state = noMoneyState;
        }

        return response;
    }

    public static List<Product> getListOfProducts() {
        return machineService.getListOfProducts();
    }
}
