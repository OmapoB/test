package ru.omarov.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinesProducer implements Runnable {

    private final ConcurrentLinkedQueue<String> lines;
    private final AtomicBoolean finished;
    private final String inputFile;

    public LinesProducer(ConcurrentLinkedQueue<String> lines, AtomicBoolean finished) {
        this.lines = lines;
        this.finished = finished;
        this.inputFile = ResourceLoader.getProperty("inputFile");
    }

    @Override
    public void run() {
        try {
            produce();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            finished.set(true);
        }
    }

    private void produce() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(inputFile), StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
    }
}
