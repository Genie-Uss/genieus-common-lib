package com.genieus.common.config;

import com.genieus.common.auth.PassportWebConfig;
import com.genieus.common.event.config.EventConfig;
import com.genieus.common.logging.LoggingConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({PassportWebConfig.class, EventConfig.class, LoggingConfig.class})
public class CommonConfig {}
