package com.tkj.garagedoorz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "doorz" )
public record DoorzProperties( Door doorOne, Door doorTwo, Position positionOne, Position positionTwo ) {
}

