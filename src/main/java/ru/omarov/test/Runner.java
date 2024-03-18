package ru.omarov.test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Runner {
    private final ConcurrentLinkedQueue<String> lines;
    private final ExecutorService consumers;
    private final int THREAD_AMOUNT;
    private final AtomicBoolean producerFinished;
    public static final Pattern PATTERN = Pattern.compile(
            ResourceLoader.getProperties().getProperty("searchPattern"),
            Pattern.UNICODE_CHARACTER_CLASS
    );
    public static AtomicInteger matches = new AtomicInteger(0);

    public Runner() {
        THREAD_AMOUNT = Integer.parseInt(
                ResourceLoader.getProperties().getProperty("threadAmount")
        );
        this.producerFinished = new AtomicBoolean(false);
        this.lines = new ConcurrentLinkedQueue<>();
        this.consumers = Executors.newFixedThreadPool(THREAD_AMOUNT);
    }

    public void start() throws InterruptedException {
        LinesProducer producer = new LinesProducer(lines, producerFinished);
        Thread proudcerThread = new Thread(producer);
        proudcerThread.start();
        for (int i = 0; i < THREAD_AMOUNT; i++) {
            LinesConsumer consumer = new LinesConsumer(lines, producerFinished);
            consumers.submit(consumer);
        }
        consumers.shutdown();
        consumers.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }
}
