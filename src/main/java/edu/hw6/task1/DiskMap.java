package edu.hw6.task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class DiskMap implements Map<String, String> {

    private Map<String, String> inMemoryMap;
    private String filePath;

    public DiskMap(String filePath) {
        this.inMemoryMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    inMemoryMap.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            // pass
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : inMemoryMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return inMemoryMap.size();
    }

    @Override
    public boolean isEmpty() {
        return inMemoryMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inMemoryMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inMemoryMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return inMemoryMap.get(key);
    }

    @Override
    public String put(String key, String value) {
        String oldValue = inMemoryMap.put(key, value);
        saveToFile();
        return oldValue;
    }

    @Override
    public String remove(Object key) {
        String removedValue = inMemoryMap.remove(key);
        saveToFile();
        return removedValue;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        inMemoryMap.putAll(m);
        saveToFile();
    }

    @Override
    public void clear() {
        inMemoryMap.clear();
        saveToFile();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return inMemoryMap.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return inMemoryMap.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return inMemoryMap.entrySet();
    }
}
