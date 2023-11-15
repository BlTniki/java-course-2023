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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class DiskMap implements Map<String, String> {
    /*
    Regex for line like "key:value".
    This pattern assumes that the ":" symbol inside the key or value is escaped using "\".
    String like "l\:ol:ke\:k" will interpreted as key as "l:ol" and value as "ke:k".
     */
    private final static Pattern KEY_VAL_LINE = Pattern.compile("^(.*[^\\\\]):(.*)$");
    private final static int KEY_GROUP_INDEX = 1;
    private final static int VALUE_GROUP_INDEX = 2;
    private final static String DOUBLE_DOT = ":";
    private final static String ESCAPED_DOUBLE_DOT = "\\:";

    private Map<String, String> inMemoryMap;
    private String filePath;

    public DiskMap(String filePath) {
        this.inMemoryMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    private @NotNull String doubleDotEscape(@NotNull String s) {
        return s.replace(DOUBLE_DOT, ESCAPED_DOUBLE_DOT);
    }

    private @NotNull String removeDoubleDotEscape(@NotNull String s) {
        return s.replace(ESCAPED_DOUBLE_DOT, DOUBLE_DOT);
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final Matcher matcher = KEY_VAL_LINE.matcher(line);
                if (!matcher.matches()) {
                    throw new RuntimeException("Bad key value pair: {%s}".formatted(line));
                }
                final String key = removeDoubleDotEscape(matcher.group(KEY_GROUP_INDEX));
                final String val = removeDoubleDotEscape(matcher.group(VALUE_GROUP_INDEX));
                inMemoryMap.put(key, val);
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
                writer.write(
                    doubleDotEscape(entry.getKey()) + DOUBLE_DOT + doubleDotEscape(entry.getValue())
                );
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
