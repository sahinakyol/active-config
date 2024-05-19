package org.activeconfig;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ConfigWatcher is a class that watches a specified file path for changes and executes a callback
 * when a change is detected.
 */
public class ConfigWatcher {

    private final Path configPath;
    private final Runnable onChange;
    private final ExecutorService executorService;

    /**
     * Constructs a ConfigWatcher with the specified configuration file path and change callback.
     *
     * @param configFilePath the path to the configuration file
     * @param onChange       the callback to execute when the configuration file changes
     */
    public ConfigWatcher(String configFilePath, Runnable onChange) {
        this.configPath = Paths.get(configFilePath);
        this.onChange = onChange;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Starts watching the configuration file for changes.
     */
    public void watch() {
        executorService.execute(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                configPath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.context().toString().equals(configPath.getFileName().toString())) {
                            onChange.run();
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        });
    }
}