package cscie55.hw7;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Link implements Serializable
{
    private String url;
    private long timestamp;
    private List<String> tags;

    public Link(String url, long timestamp, List<String> tags) {
        this.url = url;
        this.timestamp = timestamp;
        this.tags = tags;
    }

    /**
     * Takes a String and returns a Link object
     * @param line the String to parse
     * @return the Link object that the line represents
     */
    public static Link parse(String line)
    {
        int urlTokenEnd = line.indexOf(URL_TOKEN) + URL_TOKEN.length();
        int urlStart = line.indexOf(QUOTE, urlTokenEnd) + 1;
        assert urlStart > urlTokenEnd : String.format("urlTokenEnd: %d, urlStart: %d", urlTokenEnd, urlStart);
        int urlEnd = line.indexOf(QUOTE, urlStart);
        assert urlEnd > urlStart;
        String url = line.substring(urlStart, urlEnd);
        int timestampTokenEnd = line.indexOf(TIMESTAMP_TOKEN, urlEnd) + TIMESTAMP_TOKEN.length();
        int timestampStart = line.indexOf(SPACE, timestampTokenEnd);
        // Get past consecutive spaces
        while (line.charAt(timestampStart) == SPACE) {
            timestampStart++;
        }
        int timestampEnd = line.indexOf(COMMA, timestampStart);
        long timestamp = Long.parseLong(line.substring(timestampStart, timestampEnd));
        int tagsTokenEnd = line.indexOf(TAGS_TOKEN, timestampEnd) + TAGS_TOKEN.length();
        int startQuote;
        int endQuote = tagsTokenEnd;
        List<String> tags = new ArrayList<String>();
        while ((startQuote = line.indexOf(QUOTE, endQuote + 1) + 1) != 0) {
            endQuote = line.indexOf(QUOTE, startQuote);
            String tag = line.substring(startQuote, endQuote);
            tags.add(tag);
        }
        return new Link(url, timestamp, tags);
    }

    /**
     * Get this Link's URL.
     * @return String representing the URL
     */
    public String url(){
        return url;
    }

    /**
     * Get this Link's timestamp
     * @return long representing the timestamp
     */
    public long timestamp() {
        return timestamp;
    }

    /**
     * Get this Link's tags
     * @return List of Strings, each of which is a tag associated with this Link
     */
    public List<String> tags() {
        return tags;
    }

    /**
     * Checks if an Object equals this Link
     * @param o the Object to check
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Link link = (Link) o;

        if (timestamp != link.timestamp) {
            return false;
        }
        if (!url.equals(link.url)) {
            return false;
        }

        return true;
    }

    /**
     * Generates a hash of this Link, taking into account only URL and timestamp
     * @return int representing the hash
     */
    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }

    private static final String URL_TOKEN = "\"url\"";
    private static final String TIMESTAMP_TOKEN = "\"timestamp\"";
    private static final String TAGS_TOKEN = "\"tags\"";
    private static final char QUOTE = '"';
    private static final char SPACE = ' ';
    private static final char COMMA = ',';
}
