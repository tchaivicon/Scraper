package com.chae.scraper.service.impl;

import com.chae.scraper.service.JSoupStrategy;
import com.chae.scraper.service.enums.JSoupEnums;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
@Component
public class JSoupSukebeiStrategy implements JSoupStrategy {
    private final String downloadPath = "D:"+ File.separator+"Torrents";
    private final String url = "https://sukebei.nyaa.si";
    private final int PAGE_FINITE = 14;
    private final int DATE_TD = 4;
    @Override
    public JSoupEnums getStrategyName(){
        return JSoupEnums.JSoupSukebeiStrategy;
    }
    @Override
    public List<String> search(String date, String query){
        List<String> downloadUrlList = new ArrayList<>();
        int page = 1;
        boolean whileFlag = true;
        do{
            Document doc = null;
            try {
                if(page > PAGE_FINITE) whileFlag = false;
                doc = Jsoup.connect(url + query+page++).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Element table = Objects.requireNonNull(doc).select("table").get(0);
            Elements rows = table.select("tr.success");
            if(!query.contains("fc2")){
                rows.addAll(table.select("tr.default"));
            }
            for (Element row : rows) {
                Element td = row.select("td.text-center > a").first();
                Element dateTd = row.select("td").get(DATE_TD);

                String downloadUrl = url + Objects.requireNonNull(td).attr("href");

                int dateParsedInt = Integer.parseInt(dateTd.html().split(" ")[0].replace("-", ""));
                int dateParamInt = Integer.parseInt(date);

                if (dateParsedInt < dateParamInt) {
                    whileFlag = false;
                } else {//if(dateParsedInt == dateParamInt)
                    downloadUrlList.add(downloadUrl);
                }

            }
        }while(whileFlag);
        return downloadUrlList;
    }
    @Override
    public void download(List<String> downloadUrlList) {
        downloadUrlList
                .forEach(downloadUrl->{
                    try {
                        download(downloadUrl);
                        Thread.sleep(10 * 1000);
                    } catch (URISyntaxException | IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
    private void download(String downloadUrl) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Path tempFile = Paths.get(downloadPath+ File.separator+"temp");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(downloadUrl))
                .GET()
                .build();
        HttpResponse<Path> response =client.send( request , HttpResponse.BodyHandlers.ofFile(tempFile));

        processTempFile(tempFile, response);

    }

    private void processTempFile(Path tempFile, HttpResponse<Path> response) {
        HttpHeaders headers = response.headers();

        List<String> templist = headers.allValues("content-disposition");

        for(String name : templist){
            Path processedFile = Paths.get(downloadPath+ File.separator+getFileName(name));
            try {
                Files.move(tempFile, processedFile);
            }catch (Exception e){
                System.out.println("failed : "+ getFileName(name));
            }
        }
    }

    private String getFileName(String name){
        String fileName;
        String[] metaData;
        metaData = name.split("; ");
        fileName =metaData[1];
        fileName =fileName.replace("filename=","");
        fileName =fileName.replace("\"","");
        return fileName;
    }
}
