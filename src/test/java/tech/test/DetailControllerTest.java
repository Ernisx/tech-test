package tech.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import tech.test.service.DetailService;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class DetailControllerTest {

    @Autowired
    private CsvParser csvParser;

    @Autowired
    private DetailService detailService;

    @LocalServerPort
    private int port;

    private static final String FILE_NAME = "testData.csv";

    private TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    public void assertUploadedTest() throws JsonProcessingException {
        ResponseEntity<String> response = uploadFile();

        ObjectMapper mapper = new ObjectMapper();
        UploadFileResponse uploadFileResponse = mapper.readValue(response.getBody(), UploadFileResponse.class);

        assertEquals(uploadFileResponse.getFileType(), "text/csv");
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getDetailsTest() {
        uploadFile();
        ResponseEntity<List<Detail>> responseEntity = restTemplate.exchange(
                createURLWithPort("/details"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Detail>>() {
                });

        List<Detail> responseDetails = responseEntity.getBody();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertFalse(CollectionUtils.isEmpty(responseDetails));

        assertEquals(responseDetails.size(), getDetailsFromResource().size());
    }

    @Test
    public void searchByDetailsTest() {
        uploadFile();
        String searchBy = "destination";
        String value = "+370622232";
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/details/searchBy"))
                .queryParam("searchBy", searchBy)
                .queryParam("value", value);
        ResponseEntity<List<Detail>> responseEntity = restTemplate.exchange(
                uri.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Detail>>() {
                });

        List<Detail> responseDetails = responseEntity.getBody();
        for (Detail detail : responseDetails) {
            assertTrue(value.equals(detail.getDestination()));
        }
    }

    @Test
    public void getAllDetailsAverageCOstTest() {
        uploadFile();
        String response = restTemplate.getForObject(createURLWithPort("/details/averageCost"), String.class);
        assertEquals(response, detailService.averageCost(getDetailsFromResource()).toString());
    }

    private ResponseEntity<String> uploadFile() {
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("file", new org.springframework.core.io.ClassPathResource(FILE_NAME));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        return new TestRestTemplate().exchange(
                createURLWithPort("/upload-file"),
                HttpMethod.POST,
                entity,
                String.class);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private List<Detail> getDetailsFromResource() {
        return csvParser.parse(new File("src/test/resources/testData.csv"));
    }

}