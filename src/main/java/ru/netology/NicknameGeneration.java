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
                if(crBeautyIdentically(str)) {
                    switch (str.length()) {
                        case 3:
                            counterSizeThreeAtomic.getAndIncrement();
                            break;
                        case 4:
                            counterSizeFourAtomic.getAndIncrement();
                            break;
                        case 5:
                            counterSizeFiveAtomic.getAndIncrement();
                            break;
                    }
                }
            }
        }
        );

        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if(crBeautyPalindrome(str)) {
                    switch (str.length()) {
                        case 3:
                            counterSizeThreeAtomic.getAndIncrement();
                            break;
                        case 4:
                            counterSizeFourAtomic.getAndIncrement();
                            break;
                        case 5:
                            counterSizeFiveAtomic.getAndIncrement();
                            break;
                    }
                }
            }
        }
        );

        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if(crBeautySort(str)) {
                    switch (str.length()) {
                        case 3:
                            counterSizeThreeAtomic.getAndIncrement();
                            break;
                        case 4:
                            counterSizeFourAtomic.getAndIncrement();
                            break;
                        case 5:
                            counterSizeFiveAtomic.getAndIncrement();
                            break;
                    }
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

    public static boolean crBeautyIdentically(String str) {
        int count = 0;
        for (int i = 1; i < str.length(); i++) {
            int j = i - 1;
            if(str.charAt(j) == str.charAt(i)){
                count++;
            }
        }
        if (count == str.length()){
            return true;
        }
        return false;
    }

    public static boolean crBeautyPalindrome(String str) {
        String str1 = str.substring(0, str.length() / 2);
        String str2 = str.substring(str.length() / 2);
        return new StringBuilder(str2).reverse().toString().equals(str1);
    }

    public static boolean crBeautySort(String str) {
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