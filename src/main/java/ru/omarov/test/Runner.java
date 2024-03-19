package ru.omarov.test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class Runner {
    private final ConcurrentLinkedQueue<String> lines;
    private final ExecutorService consumers;
    private final int THREAD_AMOUNT;
    private final AtomicBoolean producerFinished;
    public static final Pattern PATTERN = Pattern.compile(
            ResourceLoader.getProperty("searchPattern"),
            Pattern.UNICODE_CHARACTER_CLASS
    );
    public static AtomicLong matches = new AtomicLong(0L);

    public Runner() {
        THREAD_AMOUNT = Integer.parseInt(
                ResourceLoader.getProperty("threadAmount")
        );
        this.producerFinished = new AtomicBoolean(false);
        this.lines = new ConcurrentLinkedQueue<>();
        this.consumers = Executors.newFixedThreadPool(THREAD_AMOUNT);
    }

    public void start() throws InterruptedException {
        LinesProducer producer = new LinesProducer(lines, producerFinished);
        CompletableFuture.runAsync(producer);
        for (int i = 0; i < THREAD_AMOUNT; i++) {
            LinesConsumer consumer = new LinesConsumer(lines, producerFinished);
            consumers.submit(consumer);
        }
        consumers.shutdown();
        consumers.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }
}
