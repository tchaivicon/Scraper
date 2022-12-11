package com.chae.scraper.service;

import com.chae.scraper.service.enums.JSoupEnums;

import java.net.URISyntaxException;
import java.util.List;

public interface JSoupStrategy {
    JSoupEnums getStrategyName();
    void download(List<String> downloadUrlList) throws URISyntaxException;
    List<String> search(String date, String query)throws Exception;
}
