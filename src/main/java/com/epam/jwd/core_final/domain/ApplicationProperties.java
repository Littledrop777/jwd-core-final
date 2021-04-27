package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.Properties;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {
    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String spaceshipsFileName;

    private final int fileRefreshRate;
    private final String dateTimeFormat;

    public ApplicationProperties() {
        PropertyReaderUtil property = PropertyReaderUtil.getInstance();
        property.loadProperties();
        this.inputRootDir = property.getInputRootDir();
        this.outputRootDir = property.getOutputRootDir();
        this.crewFileName = property.getCrewFileName();
        this.missionsFileName = property.getMissionsFileName();
        this.spaceshipsFileName = property.getSpaceshipsFileName();
        this.fileRefreshRate = property.getFileRefreshRate();
        this.dateTimeFormat = property.getDateTimeFormat();
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public int getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
