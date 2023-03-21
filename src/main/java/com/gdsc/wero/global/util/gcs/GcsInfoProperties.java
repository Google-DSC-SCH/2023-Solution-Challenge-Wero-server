package com.gdsc.wero.global.util.gcs;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gcs")
@Getter
@Setter
public class GcsInfoProperties {
    private String bucketName;
    private String publicUrl;
}
