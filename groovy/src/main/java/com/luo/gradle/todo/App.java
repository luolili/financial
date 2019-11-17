package com.luo.gradle.todo;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        int i = 0;
        Scanner scanner = new Scanner(System.in);

        while (++i > 0) {
            System.out.println("input item name");
            TodoItem todoItem = new TodoItem();
            todoItem.setName(scanner.nextLine());
            System.out.println(todoItem);

        }
    }
}
