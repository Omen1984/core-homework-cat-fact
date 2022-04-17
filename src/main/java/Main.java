import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String REMOTE_SERVICE_URL =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);

        List<Cat> cats = parserHttpJsonToBeanList(response);

        cats.stream()
                .filter((cat) -> cat.getUpvotes() != null && cat.getUpvotes() > 0)
                .forEach(System.out::println);
    }

    private static List<Cat> parserHttpJsonToBeanList(CloseableHttpResponse response) throws IOException {
        return mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {
        });
    }
}
