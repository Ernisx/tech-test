package tech.test;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface CsvParser {

    List<Detail> parse(MultipartFile file);

    List<Detail> parse(File file);
}
