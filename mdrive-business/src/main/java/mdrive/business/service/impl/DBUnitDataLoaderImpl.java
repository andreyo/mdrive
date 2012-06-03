package mdrive.business.service.impl;

import mdrive.business.service.DBUnitDataLoader;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStreamReader;

/**
 * User: andrey.osipov
 */
@Component
public class DBUnitDataLoaderImpl implements DBUnitDataLoader {
    private static final Logger log = Logger.getLogger(DBUnitDataLoaderImpl.class);

    private final static boolean USE_XML_DB = true;

    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource dataSource;

    @Override
    public void initTestData() throws Exception {
        if (USE_XML_DB) {
            initTestDataXml();
        } else {
            initTestDataCsv();
        }
    }

    @Override
    public void initTestDataXml() throws Exception {
        initTestDataXml(INITIAL_DB_XML_PATH);
    }

    @Override
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

    @Override
    public void initTestDataCsv() throws Exception {
        initTestDataCsv(INITIAL_DB_CSV_PATH);
    }

    @Override
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