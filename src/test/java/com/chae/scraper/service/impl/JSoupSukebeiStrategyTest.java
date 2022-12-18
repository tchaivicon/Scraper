package com.chae.scraper.service.impl;

import com.chae.scraper.service.JSoupStrategy;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class JSoupSukebeiStrategyTest {

    @Test
    void search() throws Exception {
        JSoupStrategy jSoupStrategy = new JSoupSukebeiStrategy();
        String queryString = "fc2";
        String date = "20221211";
        String query = "/?f=0&c=0_0&q="+queryString+"&p=";
        List<String> list = jSoupStrategy.search(date,query);
        list.forEach(System.out::println);
    }

    @Test
    void download() throws URISyntaxException {
        JSoupStrategy jSoupStrategy = new JSoupSukebeiStrategy();
        List<String> downloadList = new ArrayList<>();
        downloadList.add("https://sukebei.nyaa.si/download/3792663.torrent");
        downloadList.add("https://sukebei.nyaa.si/download/3792662.torrent");
        jSoupStrategy.download(downloadList);
    }
    @Test
    void integrationTest() throws Exception {
        //12-06 ~ 12-17
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDate = "20221216";
        String endDate = "20221206";
        String targetDate;
        List<String> list = new ArrayList<>();
        LocalDate startLocalDate = LocalDate.parse(startDate, formatter);
        LocalDate endLocalDate = LocalDate.parse(endDate, formatter).minusDays(1);

        JSoupStrategy jSoupStrategy = new JSoupSukebeiStrategy();

        do{
            targetDate = startLocalDate.format(formatter);
            System.out.println(targetDate);
            startLocalDate = startLocalDate.minusDays(1);
            String queryString = "fc2";
            String date = "20221211";
            String query = "/?f=0&c=0_0&q="+queryString+"&p=";
            list.addAll(jSoupStrategy.search(date,query));
            jSoupStrategy.download(list);
            list = new ArrayList<>();
        }while(startLocalDate.isAfter(endLocalDate));


    }
}