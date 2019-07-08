package com.cz.day4_muke.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Day 4 Muke.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
