package com.my.project.cryptoservice.util;


import com.my.project.cryptoservice.entity.Crypto;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvUtilsTest {

    //Not really unit tests more like integration, just a way to see how the csv reader works
    @Test
    public void testCsvReader() throws FileNotFoundException {
        File file = new File(getClass().getClassLoader().getResource("test//test_values.csv").getPath());
        List<Crypto> cryptosFormCsvFile = CsvUtils.readCryptosFromCsvFile(file);

        assertEquals(15, cryptosFormCsvFile.size());
    }

    @Test
    public void testCsvReaderAllFiles() {
        List<Crypto> cryptosFormCsvFile = CsvUtils.readAllCsvFilesInFolder("test");

        assertEquals(16, cryptosFormCsvFile.size());
    }
}
