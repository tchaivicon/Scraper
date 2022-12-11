package com.chae.scraper.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@ConfigurationProperties(prefix = "jsoup.download")
@PropertySource(value = "classpath:jsoupProperties.yml"
              , factory = YamlPropertySourceFactory.class)
public class JSoupProperties {
    private String url;

    @Getter
    @Setter
    public static class path{
        private String local;
        private String server;
    }
}
