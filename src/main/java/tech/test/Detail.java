package tech.test;

import com.opencsv.bean.CsvBindByName;

public class Detail {
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String account;
    @CsvBindByName
    private String destination;
    @CsvBindByName
    private String startDate;
    @CsvBindByName
    private String endDate;
    @CsvBindByName
    private String status;
    @CsvBindByName
    private String costPerMinute;

    public Detail() {
    }

    public Detail(String id, String account, String destination, String startDate, String endDate, String status, String costPerMinute) {
        this.id = id;
        this.account = account;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.costPerMinute = costPerMinute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCostPerMinute() {
        return costPerMinute;
    }

    public void setCostPerMinute(String costPerMinute) {
        this.costPerMinute = costPerMinute;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Detail{");
        sb.append("id='").append(id).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", destination='").append(destination).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", costPerMinute='").append(costPerMinute).append('\'');
        sb.append('}');
        return sb.toString();
    }
}