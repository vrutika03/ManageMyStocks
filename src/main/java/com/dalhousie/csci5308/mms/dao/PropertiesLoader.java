package com.dalhousie.csci5308.mms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class PropertiesLoader {
    public Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream("application.properties");
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
}
