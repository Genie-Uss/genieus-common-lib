package com.genieus.common.config;

import com.genieus.common.auth.PassportWebConfig;
import com.genieus.common.event.config.EventConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({PassportWebConfig.class, EventConfig.class})
public class CommonConfig {}
