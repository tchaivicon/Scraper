package com.chae.scraper.service.properties;

import com.chae.scraper.properties.JSoupProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JSoupPropertiesTest {
    @Autowired
    private JSoupProperties jSoupProperties;

    @Test
    public void Test_Properties(){
        System.out.println(jSoupProperties.getUrl());
    }
}