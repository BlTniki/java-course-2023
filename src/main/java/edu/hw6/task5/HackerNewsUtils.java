package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class HackerNewsUtils {
    private final static String TOP_STORIES_URI = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private final static String STORY_ITEM_URI_TEMPLATE = "https://hacker-news.firebaseio.com/v0/item/%d.json";

    private final static Pattern STORY_ID_REGEX = Pattern.compile("\\d+");
    private final static Pattern TITLE_EXTRUDER = Pattern.compile("\"title\":\"([^\"]+)\"");
    private final static int CLEAR_TITLE_GROUP_INDEX = 1;

    private HackerNewsUtils() {
    }

    private static @NotNull String makeGetRequest(@NotNull String uriStr) {
        HttpResponse<String> response;

        try (HttpClient client = HttpClient.newBuilder().build();) {
            final URI uri = new URI(uriStr);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    public static long[] hackerNewsTopStories() {
        final String body = makeGetRequest(TOP_STORIES_URI);

        // how about a little hack? =)
        final int idCount = body.length() - body.replace(",", "").length() + 1;
        final long[] idArray = new long[idCount];

        final Matcher matcher = STORY_ID_REGEX.matcher(body);
        for (int i = 0; i < idCount && matcher.find(); i++) {
            idArray[i] = Long.parseLong(matcher.group());
        }

        return idArray;
    }

    public static @NotNull String news(long id) {
        final String body = makeGetRequest(STORY_ITEM_URI_TEMPLATE.formatted(id));

        final Matcher matcher = TITLE_EXTRUDER.matcher(body);

        if (!matcher.find()) {
            throw new RuntimeException("Failed to parse response body");
        }

        return matcher.group(CLEAR_TITLE_GROUP_INDEX);
    }
}
