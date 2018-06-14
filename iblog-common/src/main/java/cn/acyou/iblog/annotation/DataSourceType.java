package cn.acyou.iblog.annotation;

public enum DataSourceType {

    NORMAL("dataSource"),READONLY("dataSource2");

    DataSourceType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}