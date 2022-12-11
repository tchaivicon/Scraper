package com.chae.scraper.service.factory;

import com.chae.scraper.service.JSoupStrategy;
import com.chae.scraper.service.enums.JSoupEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsoupStrategyFactory {
    private final List<JSoupStrategy> strategyList;

    public JSoupStrategy getJsoupStrategy(JSoupEnums enums){
        return strategyList.stream()
                .filter(strategy -> strategy.getStrategyName().equals(enums))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Strategy not found"));
    }
}
