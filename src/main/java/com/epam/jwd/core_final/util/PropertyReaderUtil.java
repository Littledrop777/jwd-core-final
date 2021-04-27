package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private static PropertyReaderUtil instance;
    private static final Properties properties = new Properties();

    private PropertyReaderUtil() {
    }

    public static PropertyReaderUtil getInstance() {
        if (instance == null) {
            instance = new PropertyReaderUtil();
        }
        instance.loadProperties();
        return instance;
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public void loadProperties() {
        final String propertiesFileName = AppProperties.APPLICATION_PROPERTIES;

        try (InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream(propertiesFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public String getInputRootDir() {
        return properties.getProperty(AppProperties.INPUT_ROOT_DIR);
    }

    public String getOutputRootDir() {
        return properties.getProperty(AppProperties.OUTPUT_ROOT_DIR);
    }

    public String getCrewFileName() {
        return properties.getProperty(AppProperties.CREW_FILE_NAME);
    }

    public String getMissionsFileName() {
        return properties.getProperty(AppProperties.MISSIONS_FILE_NAME);
    }

    public String getSpaceshipsFileName() {
        return properties.getProperty(AppProperties.SPACESHIPS_FILE_NAME);
    }

    public String getSpacemapFileName() {
        return properties.getProperty(AppProperties.SPACE_MAP_FILE_NAME);
    }

    public int getFileRefreshRate() {
        return Integer.parseInt(properties.getProperty(AppProperties.FILE_REFRESH_RATE));
    }

    public String getDateTimeFormat() {
        return properties.getProperty(AppProperties.DATE_TIME_FORMAT);
    }

}
