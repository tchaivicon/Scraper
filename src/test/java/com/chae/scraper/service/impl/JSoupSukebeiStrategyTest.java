package com.chae.scraper.service.impl;

import com.chae.scraper.service.JSoupStrategy;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
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
}