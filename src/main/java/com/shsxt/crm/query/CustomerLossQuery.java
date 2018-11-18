package com.shsxt.crm.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shsxt.crm.base.BaseQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerLossQuery extends BaseQuery {

    private String cusNo;

    private String cusName;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
