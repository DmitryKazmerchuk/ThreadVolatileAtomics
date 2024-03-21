package ru.netology;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NicknameGeneration {
    public static int counterSizeThree = 0;
    public static int counterSizeFour = 0;
    public static int counterSizeFive = 0;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counterSizeThreeAtomic = new AtomicInteger(counterSizeThree);
        AtomicInteger counterSizeFourAtomic = new AtomicInteger(counterSizeFour);
        AtomicInteger counterSizeFiveAtomic = new AtomicInteger(counterSizeFive);

        Random random = new Random();
        String[] texts = new String[100_000];

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == 3 && threeSymbols(str)) {
                    counterSizeThreeAtomic.getAndIncrement();
                }
                if (str.length() == 4 && threeSymbols(str)) {
                    counterSizeFourAtomic.getAndIncrement();
                }
                if (str.length() == 5 && threeSymbols(str)) {
                    counterSizeFiveAtomic.getAndIncrement();
                }
            }
        }
        );

        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == 3 && fourSymbols(str)) {
                    counterSizeThreeAtomic.getAndIncrement();
                }
                if (str.length() == 4 && fourSymbols(str)) {
                    counterSizeFourAtomic.getAndIncrement();
                }
                if (str.length() == 5 && fourSymbols(str)) {
                    counterSizeFiveAtomic.getAndIncrement();
                }
            }
        }
        );

        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if (str.length() == 3 && fiveSymbols(str)) {
                    counterSizeThreeAtomic.getAndIncrement();
                }
                if (str.length() == 4 && fiveSymbols(str)) {
                    counterSizeFourAtomic.getAndIncrement();
                }
                if (str.length() == 5 && fiveSymbols(str)) {
                    counterSizeFiveAtomic.getAndIncrement();
                }
            }
        }
        );

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.println("Красивых слов с длиной 3: " + counterSizeThreeAtomic.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterSizeFourAtomic.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterSizeFiveAtomic.get() + " шт");
    }

    public static boolean threeSymbols(String str) {
        int count = 0;
        for (int i = 1; i < str.length(); i++) {
            int j = i - 1;
            if(str.charAt(j) == str.charAt(i)){
                count++;
            }
        }
        if (count == str.length() - 1){
            return true;
        }
        return false;
    }

    public static boolean fourSymbols(String str) {
        String str1 = str.substring(0, str.length() / 2);
        String str2 = str.substring(str.length() / 2);
        return new StringBuilder(str2).reverse().toString().equals(str1);
    }

    public static boolean fiveSymbols(String str) {
        String sorted = str.chars()
                .sorted().
                collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return str.equals(sorted);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}