package com.my.project.cryptoservice.util;

import com.my.project.cryptoservice.entity.Crypto;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

@Log4j2
public class CsvUtils {
    private static final String RESOURCE_FOLDER_FORMAT = "classpath*:\\%s\\*.csv";

    /**
     * Read and parse cryptos from CSV file
     * @param file {@link File} file CSV file
     * @return {@link List<Crypto>} of cryptos
     * @throws FileNotFoundException if file not found
     */
    static List<Crypto> readCryptosFromCsvFile(File file) throws FileNotFoundException {
        log.info("Reading CSV file: {}", file.getAbsolutePath());
        return new CsvToBeanBuilder<Crypto>(new FileReader(file))
                .withType(Crypto.class)
                .build()
                .parse();
    }


    /**
     * Read all CSV files in folder
     * @param folder {@link String} path to folder with CSV files
     * @return {@link List<Crypto>} list of cryptos
     */
    public static List<Crypto> readAllCsvFilesInFolder(String folder) {
        Resource[] resources;
        try {
            resources = new PathMatchingResourcePatternResolver(CsvUtils.class.getClassLoader())
                    .getResources(format(RESOURCE_FOLDER_FORMAT, folder));
        } catch (IOException e) {
            log.warn("Error while reading folder for CSV files: {}", folder, e);
            return Collections.emptyList();
        }
        List<Crypto> result = new LinkedList<>();
        log.debug("Found {} CSV files in folder {}", resources.length, folder);
        for (Resource resource : resources) {
            try {
                result.addAll(readCryptosFromCsvFile(resource.getFile()));
            } catch (IOException e) {
                log.warn("Error while reading CSV file {}", resource.getFilename(), e);
            }
        }
        return result;
    }
}
