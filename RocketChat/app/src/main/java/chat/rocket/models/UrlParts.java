package chat.rocket.models;

import java.util.Map;

/**
 * Created by julio on 21/11/15.
 */
public class UrlParts {
    private String url;
    private Map<String, String> meta;
    private Map<String, String> headers;
    private Map<String, String> parsedUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParsedUrl() {
        return parsedUrl;
    }

    public void setParsedUrl(Map<String, String> parsedUrl) {
        this.parsedUrl = parsedUrl;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }
}
