package com.Ecole.demo.config;

import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.entity.TypeConduite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class TolerantEnumConverters {

    @Bean
    public Converter<String, Periode> periodeConverter() {
        return Periode::parse;
    }

    @Bean
    public Converter<String, TypeConduite> typeConduiteConverter() {
        return TypeConduite::parse;
    }
}