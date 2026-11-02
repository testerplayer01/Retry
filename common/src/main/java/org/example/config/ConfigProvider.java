package org.example.config;

import org.aeonbits.owner.ConfigFactory;

public final class ConfigProvider {
    private static final ProjectConfig CONFIG =
            ConfigFactory.create(ProjectConfig.class);

    private ConfigProvider() {
    }

    public static ProjectConfig get() {
        return CONFIG;
    }
}
