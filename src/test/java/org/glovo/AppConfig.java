package org.glovo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

@PropertySource("classpath:configuration.properties")
@ComponentScan("org.glovo")
@ContextConfiguration
public class AppConfig {
}
