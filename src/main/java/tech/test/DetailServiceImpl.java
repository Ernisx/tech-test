package tech.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailDao detailDao;

    @Override
    public List<Detail> getDetails() {
        List<Detail> details = detailDao.getDetails();
        if (CollectionUtils.isEmpty(details)) {
            throw new EmptyResultDataAccessException("Data not found", 1);
        }
        return details;
    }

    @Override
    public List<Detail> getDetails(String searchBy, String value) {
        List<Detail> details = detailDao.getDetails(searchBy, value);
        if (CollectionUtils.isEmpty(details)) {
            throw new EmptyResultDataAccessException("Data not found", 1);
        }
        return details;
    }

    @Override
    public void insert(List<Detail> details) {
        for (Detail detail : details) detailDao.insert(detail);
    }

    @Override
    public BigDecimal averageCost(List<Detail> details) {
        BigDecimal total = new BigDecimal(0);
        for (Detail detail : details) {
            if (detail.getCostPerMinute() != null) {
                total = total.add(convert(detail.getCostPerMinute()));
            }
        }
        return total.divide(new BigDecimal(details.size()), BigDecimal.ROUND_CEILING);
    }

    private BigDecimal convert(String value) {
        return new BigDecimal(String.format("%.2f", new BigDecimal(value)));
    }


}
