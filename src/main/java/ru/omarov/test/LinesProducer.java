package ru.omarov.test;

import java.io.BufferedReader;
import java.io.FileReader;
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
        this.inputFile = ResourceLoader.getProperties().getProperty("inputFile");
    }

    @Override
    public void run() {
        try {
            produce();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        finished.set(true);
    }

    private void produce() throws IOException, InterruptedException {
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
