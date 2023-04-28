package com.my.project.cryptoservice.util;


import com.my.project.cryptoservice.entity.Crypto;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CsvUtilsTest {

    private CsvUtils csvUtils = new CsvUtils();

    @Test
    public void testCsvReader() throws FileNotFoundException {
        File file = new File(getClass().getClassLoader().getResource("cryptosource//ETH_values.csv").getPath());
        List<Crypto> cryptosFormCsvFile = csvUtils.readCryptosFromCsvFile(file);

        System.out.println(cryptosFormCsvFile);
    }

    @Test
    public void testCsvReaderAllFiles() throws IOException {
        List<Crypto> cryptosFormCsvFile = csvUtils.readAllCsvFilesInFolder("cryptosource");
        System.out.println(cryptosFormCsvFile);
    }
}
