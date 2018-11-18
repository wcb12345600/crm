package com.shsxt.crm.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shsxt.crm.base.BaseQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CustomerServeQuery extends BaseQuery {

    private String state;

    private String customer;

    private String myd;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
