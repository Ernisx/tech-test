package tech.test;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tech.test.exception.ApiFileNotFoundException;
import tech.test.exception.ApiIOException;

import java.io.*;
import java.util.List;

@Component
public class CsvParserImpl implements CsvParser {
    @Override
    public List<Detail> parse(MultipartFile file) {
        try {
            return parse(file.getInputStream());
        } catch (IOException e) {
            throw new ApiIOException(file.getName() + " file is interrupted");
        }
    }

    public List<Detail> parse(File file) {
        try {
            return parse(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ApiFileNotFoundException(file.getName() + " file not found.");
        }
    }


    private List<Detail> parse(InputStream inputStream) {
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            CsvToBean<Detail> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Detail.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();

        } catch (Exception ex) {
            throw new RuntimeException(); // FIXME!!!!
        }
    }
}
