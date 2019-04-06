package com.autoever.pilot.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "app")
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private String pageSize;
}
