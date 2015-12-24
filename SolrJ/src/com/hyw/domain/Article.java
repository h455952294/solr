package com.hyw.domain;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by 45595 on 2015/12/24.
 */
public class Article {
    @Field(value = "id")
    private String id;
    @Field(value = "name")
    private String Nm;
    @Field(value = "title")
    private String Tt;
    @Field(value = "content")
    private String Ct;
    @Field(value = "price")
    private Double Pr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNm() {
        return Nm;
    }

    public void setNm(String nm) {
        Nm = nm;
    }

    public String getTt() {
        return Tt;
    }

    public void setTt(String tt) {
        Tt = tt;
    }

    public String getCt() {
        return Ct;
    }

    public void setCt(String ct) {
        Ct = ct;
    }

    public Double getPr() {
        return Pr;
    }

    public void setPr(Double pr) {
        Pr = pr;
    }
}
