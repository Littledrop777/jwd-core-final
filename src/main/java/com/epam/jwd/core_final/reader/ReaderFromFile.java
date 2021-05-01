package com.epam.jwd.core_final.reader;

import com.epam.jwd.core_final.Main;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.exception.InvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ReaderFromFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrewReader.class);
    private static final String COMMENT = "#";
    private static ReaderFromFile instance;

    private ReaderFromFile() {
    }

    public static ReaderFromFile getInstance() {
        if (instance == null) {
            instance = new ReaderFromFile();
        }
        return instance;
    }

    public List<String> readFileByLine(String path) throws InvalidStateException {
        File file = new File(Objects.requireNonNull(Main.class
                .getClassLoader().getResource(path)).getFile());
        List<String> lines = new ArrayList<>();

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(COMMENT)) {
                    continue;
                }
                lines.add(line.trim());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        if (lines.isEmpty()) {
            throw new InvalidStateException(Error.FILE_IS_EMPTY + path);
        }
        return lines;
    }
}
