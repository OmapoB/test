package ru.omarov.test;

public class App {
    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        runner.start();
        System.out.println(Runner.matches.get());
    }
}
