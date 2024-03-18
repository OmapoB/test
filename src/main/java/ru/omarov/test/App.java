package ru.omarov.test;

import ru.omarov.test.Runner;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        runner.start();
        System.out.println(Runner.matches.get());
    }
}
