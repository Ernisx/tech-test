package tech.test.dao;

import tech.test.Detail;

import java.util.List;

public interface DetailDao {

    List<Detail> getDetails();

    List<Detail> getDetails(String searchBy, String value);

    void insert(Detail detail);
}
