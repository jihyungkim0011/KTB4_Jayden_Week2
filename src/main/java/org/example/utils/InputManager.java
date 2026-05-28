package org.example.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class InputManager {
    private  static final Scanner sc = new Scanner(System.in);

    public static int inputInt(String message) {
        while (true) {
            System.out.println(message);
            try {
                int result = Integer.parseInt(sc.nextLine().trim());

                if (result > 0) {
                    return result;
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static Long inputLong(List<Long> ids, String message) {
        while (true) {
            System.out.println(message);
            try {
                Long result = Long.parseLong(sc.nextLine().trim());

                if (ids.contains(result)) {
                    return result;
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static BigDecimal inputMoney(String message) {
        while (true) {
            System.out.println(message);
            try {
                BigDecimal result = new BigDecimal(sc.nextLine().trim());

                if (result.compareTo(BigDecimal.ZERO) >= 0) {
                    return result;
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다.");
            }
        }


    }

    public static BigDecimal inputMoney(BigDecimal amount, String message) {
        while (true) {
            System.out.println(message);
            try {
                BigDecimal result = new BigDecimal(sc.nextLine().trim());

                if (result.compareTo(BigDecimal.ZERO) >= 0 && result.compareTo(amount) <= 0) {
                    return result;
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static String inputString(String message) {
        while (true) {
            System.out.println(message);
            String result = sc.nextLine().trim();

            if (!result.isEmpty()) {
                return result;
            }
        }
    }
}
