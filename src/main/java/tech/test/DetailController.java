package tech.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.test.service.DetailService;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class DetailController {

    @Autowired
    private DetailService detailService;

    @Autowired
    private CsvParser csvParser;

    @GetMapping("/details")
    public ResponseEntity<List<Detail>> getDetails() {
        return new ResponseEntity<>(detailService.getDetails(), HttpStatus.OK);
    }

    @GetMapping("/details/searchBy")
    public List<Detail> getDetailsByValue(
            @RequestParam(value = "searchBy") String searchBy,
            @RequestParam(value = "value") String value) {
        return detailService.getDetails(searchBy, value);
    }

    @GetMapping("/details/averageCost")
    public BigDecimal getAllDetailsAverageCost() {
        return detailService.averageCost(detailService.getDetails());
    }

    @PostMapping("/upload-file")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        detailService.insert(csvParser.parse(file));
        return new UploadFileResponse(file.getName(), file.getContentType(), file.getSize());
    }
}
