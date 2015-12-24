package com.hyw.dao;

import com.hyw.domain.Article;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 45595 on 2015/12/24.
 */
public class SolrJ {
    private static String URL = "http://localhost:8983/solr";

    //建立索引的方式1
    @Test
    public void addIndexFirst() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(URL);

        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id","9527");
        solrInputDocument.addField("name","张三");
        solrInputDocument.addField("XXXX_ss", "这是个什么东西");

        solrServer.add(solrInputDocument);
        solrServer.commit();
    }

    //建立索引的方式2
    @Test
    public void addIndexSecond() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer(URL);

        //SolrInputDocument solrInputDocument = new SolrInputDocument();
        //solrInputDocument.addField("id","9527");
        //solrInputDocument.addField("name","张三");
        //solrInputDocument.addField("XXXX_ss", "这是个什么东西");
        //solrServer.add(solrInputDocument);
        List<Article> articles = new ArrayList<Article>();
        Article article = null;
        for(int i=0; i<20 ;i++){
            article = new Article();
            article.setId(i+"");
            article.setTt("标题");
            article.setCt("内容,内容，内容,内容，内容,内容，内容,内容，内容,内容");
            article.setNm("高富帅");
            article.setPr(5000.0);
            articles.add(article);
        }

        solrServer.addBeans(articles);
        solrServer.commit();
    }


    //查询
    @Test
    public void query2() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer(URL);
        //构建查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        //solrQuery.setQuery("*:*");
        solrQuery.setQuery("name:高富帅");

        //范围
        solrQuery.setStart(0);
        solrQuery.setRows(100);

        //高亮
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //我需要对那几个字进行高亮
        solrQuery.setParam("hl.fl","name");

        QueryResponse queryResponse = solrServer.query(solrQuery);
        //返回结果集
        SolrDocumentList documents = queryResponse.getResults();
        Map<String,Map<String,List<String>>> mapList = queryResponse.getHighlighting();
        //for(SolrDocument solrDocument : documents){
        //    System.out.println("id:"+solrDocument.get("id"));
        //    System.out.println("name:"+solrDocument.get("name"));
        //    System.out.println("XXXX_ss:" + solrDocument.get("XXXX_ss"));
        //}
        for(SolrDocument solrDocument : documents){
            //System.out.print("id:" + solrDocument.get("id") + ",");
            //System.out.print("title:" + solrDocument.get("title")+",");
            //System.out.print("name:" + solrDocument.get("name")+",");
            //System.out.print("content:" + solrDocument.get("content")+",");
            //System.out.println("price:" + solrDocument.get("price"));
            Map<String,List<String>> listMap = mapList.get(solrDocument.get("id"));
            List<String> strings = listMap.get("name");
            System.out.println(strings);
        }
    }
    //查询
    @Test
    public void query() throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer(URL);
        //构建查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        //solrQuery.setQuery("*:*");
        solrQuery.setQuery("name:高");

        //范围
        solrQuery.setStart(0);
        solrQuery.setRows(100);


        QueryResponse queryResponse = solrServer.query(solrQuery);
        //返回结果集
        SolrDocumentList documents = queryResponse.getResults();
        for(SolrDocument solrDocument : documents){
            System.out.print("id:" + solrDocument.get("id") + ",");
            System.out.print("title:" + solrDocument.get("title")+",");
            System.out.print("name:" + solrDocument.get("name")+",");
            System.out.print("content:" + solrDocument.get("content")+",");
            System.out.println("price:" + solrDocument.get("price"));
        }
    }
}
