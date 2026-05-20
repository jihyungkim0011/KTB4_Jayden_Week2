package org.example.controller;

import org.example.Home;

public class NavigationController extends Controller{

    @Override
    public void run() {
        System.out.println("원래 화면으로 돌아갑니다.");
        returnHomeList();
    }

    public static void shutDown() {
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);
    }

    public static void returnHomeList() {
        Home home = new Home();
        home.homeList();
    }
}
