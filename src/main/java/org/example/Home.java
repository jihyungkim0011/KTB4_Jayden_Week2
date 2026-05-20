package org.example;

import org.example.adapter.ListAdapter;
import org.example.controller.NavigationController;
import org.example.utils.InputManager;

import java.util.Map;


public class Home {
    private final ListAdapter listAdapter = new ListAdapter();

    private final Map<Integer, String> menuMap = Map.of(
            1, "내 계좌/입출금",
            2, "대출",
            3, "예금",
            4, "종료"
    );

    public void home() {
        System.out.println("====================================");
        System.out.println("안녕하세요. 반갑습니다.");
        System.out.println("Jayden 은행입니다.");
        homeList();
    }

    public void homeList() {
        System.out.println("====================================");
        System.out.println();
        System.out.println("무엇을 도와드릴까요?");
        System.out.println();
        System.out.println();
        menuMap.entrySet().stream()
                .forEach(entry -> {
                    System.out.println("[" + entry.getKey() + "] " + entry.getValue());
                });
        System.out.println();
        System.out.println();

        int number = InputManager.inputInt("번호 선택: ");
        System.out.println(number + " 번을 선택하셨습니다.");

        getService(number);
    }

    private void getService(int number) {
        shutDownIfNumberIs4(number);

        listAdapter.getController(number).run();
    }

    private void shutDownIfNumberIs4(int number) {
        if (number == 4) {
            NavigationController.shutDown();
        }
    }
}
