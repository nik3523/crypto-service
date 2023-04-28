# Crypto recommendation service
## Description
Service for recommending cryptocurrencies based on the user's preferences.
Source information is retrieved from CSV file and stored in in-memory database. By default, it's configured to read all CSV files from resources
folder on startup and save it to database. So to increase number we just need to add new CSV file. All processing is done with data from DB. 
So it's not necessary to read CSV files on each request and it's easier to switch source of data.

Service contains list of available cryptocurrencies in config file. It could be extended with new ones.

Service works with date periods (startDate, lastDate) and returns list of metrics for cryptocurrencies for each period.
For this app periods are prepopulated with default values in controller to find data and to simplify testing.

## Documentation
Locally swagger documentation is available on http://localhost:8080/swagger-ui.html

Also, all important methods have java documentation are covered with unit tests.

And the last but not least, there is this **README.md** file.

## Application specific properties
* **csv.read-on-startup** - if true, service will read all CSV files from resources folder on startup and save it to database
* **csv.folder.path** - path to folder with CSV files
* **cryptos.available** - list of available cryptocurrencies is String format separated by comma