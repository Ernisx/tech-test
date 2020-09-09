package tech.test;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface DetailService {

    List<Detail> getDetails();

    List<Detail> getDetails(String searchBy, String value);

    void insert(List<Detail> details);

    BigDecimal averageCost(List<Detail> details);
}
