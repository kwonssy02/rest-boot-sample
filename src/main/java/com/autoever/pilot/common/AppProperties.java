package com.autoever.pilot.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "app")
//@Validated
@Getter @Setter
public class AppProperties {

    @NotEmpty
    private String pageSize;
}
