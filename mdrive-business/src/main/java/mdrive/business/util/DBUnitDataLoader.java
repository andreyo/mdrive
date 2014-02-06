package mdrive.business.util;

import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * User: andrey.osipov
 */
public class DBUnitDataLoader {
    private static final Logger log = Logger.getLogger(DBUnitDataLoader.class);
    final static String INITIAL_DB_XML_PATH = "initialdb.xml";
    final static String INITIAL_DB_CSV_PATH = "csv_initial_db_full";

    private DataSource dataSource;

    public DBUnitDataLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initTestDataXml() throws Exception {
        initTestDataXml(INITIAL_DB_XML_PATH);
    }

    public void initTestDataXml(String initialDbPath) throws Exception {
        IDatabaseConnection conn = null;
        ClassPathResource dataSet = new ClassPathResource(initialDbPath);
        IDataSet data = new XmlDataSet(new InputStreamReader(dataSet.getInputStream(), "UTF-8"));
        conn = new DatabaseConnection(dataSource.getConnection());
        DatabaseConfig config = conn.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        DatabaseOperation.CLEAN_INSERT.execute(conn, data);
        log.debug("Xml Test Data CLEAN INSERT-ED");
    }

    public void initTestDataCsv() throws Exception {
        long t0 = System.nanoTime();
        initTestDataCsv(INITIAL_DB_CSV_PATH);
        log.info("loaded in " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0) + " ms");
    }

    public void initTestDataCsv(String initialDbPath) throws Exception {
        IDatabaseConnection conn = null;
        IDataSet data = new CsvDataSet(new ClassPathResource(initialDbPath).getFile());
        conn = new DatabaseConnection(dataSource.getConnection());
        DatabaseConfig config = conn.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        DatabaseOperation.CLEAN_INSERT.execute(conn, data);
        log.debug("Csv Test Data CLEAN INSERT-ED");
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}