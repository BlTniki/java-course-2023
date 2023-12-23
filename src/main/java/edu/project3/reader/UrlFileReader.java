package edu.project3.reader;

import edu.project3.exception.BadFilePathException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public class UrlFileReader implements FileReader {
    @Override
    public @NotNull Collection<String> getStringsFrom(@NotNull String filepath)
        throws IOException, BadFilePathException {
        try {
            return getStrings(filepath);
        } catch (URISyntaxException | IllegalArgumentException e) {
            throw new BadFilePathException("Bad URL was provided (Bad syntax): " + filepath);
        } catch (NoSuchFileException | FileNotFoundException | UnknownHostException e) {
            throw new BadFilePathException("Bad URL was provided (No such file): " + filepath);
        }
    }

    @NotNull private static ArrayList<String> getStrings(@NotNull String filepath)
        throws URISyntaxException, IOException {
        URL fileUrl = new URI(filepath).toURL();
        URLConnection urlConnection = fileUrl.openConnection();

        ArrayList<String> lines = new ArrayList<>();

        try (InputStream inputStream = urlConnection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        }
        return lines;
    }
}
