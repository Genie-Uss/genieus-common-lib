package com.genieus.common.config;

import com.genieus.common.passport.PassportWebConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(PassportWebConfig.class)
public class CommonConfig {}
