package org.example.adapter;

import org.example.controller.*;

import java.util.Map;

public class ListAdapter {
    private final Map<Integer, Controller> controllerMap = Map.of(
            1, new AccountController(),
            2, new LoanController(),
            3, new SavingController(),
            4, new NavigationController()
    );

    public Controller getController(int number) {
        return controllerMap.getOrDefault(number, new NavigationController());
    }
}
