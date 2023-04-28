package com.my.project.cryptoservice;

import com.my.project.cryptoservice.entity.Crypto;
import com.my.project.cryptoservice.service.CryptoService;
import com.my.project.cryptoservice.util.CsvUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Crypto suggestion service",
                version = "1.0",
                description = "Service to retrieve crypto history data from CSV and provide metrics for it via REST API."
        )
)
@SpringBootApplication
@Log4j2
public class CryptoServiceApplication {

    @Value(value = "${csv.folder.path}")
    private String csvFolderPath;

    @Value(value = "${csv.read-on-startup}")
    private boolean readCsvOnStartup;

    @Autowired
    private CryptoService cryptoService;

    public static void main(String[] args) {
        SpringApplication.run(CryptoServiceApplication.class, args);
    }


    /**
     * Reads all CSV files in the configured folder on startup and saves the data to the database.
     */
    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        if (readCsvOnStartup) {
            List<Crypto> cryptos = CsvUtils.readAllCsvFilesInFolder(csvFolderPath);
            log.debug("Found {} crypto history rows in CSV files", cryptos.size());
            cryptoService.saveAll(cryptos);
        }
    }
}
