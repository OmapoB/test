package ru.omarov.test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;

public class LinesConsumer implements Runnable {
    private final ConcurrentLinkedQueue<String> lines;
    private final AtomicBoolean producerFinished;

    public LinesConsumer(final ConcurrentLinkedQueue<String> lines, AtomicBoolean finished) {
        this.lines = lines;
        this.producerFinished = finished;
    }

    @Override
    public void run() {
        try {
            consume();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void consume() {
        while (!(producerFinished.get() && lines.isEmpty())) {
            if (!lines.isEmpty()) {
                String line = lines.poll();
                count(line);
            }
        }
    }

    private void count(String where) {
        int count = 0;
        Matcher matcher = Runner.PATTERN.matcher(where);
        while (matcher.find()) {
            count++;
        }
        Runner.matches.addAndGet(count);
    }
}
