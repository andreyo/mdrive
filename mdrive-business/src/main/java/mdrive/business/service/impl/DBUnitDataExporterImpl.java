package mdrive.business.service.impl;

import mdrive.business.service.DBUnitDataExporter;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSetWriter;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.XmlDataSetWriter;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.regex.Pattern;

/**
 * User: andrey.osipov
 */
public class DBUnitDataExporterImpl implements DBUnitDataExporter {
    private static final Logger log = Logger.getLogger(DBUnitDataExporterImpl.class);

    private static final String NUMERIC_REGEX = "[-+]?\\d*\\.?\\d+";
    //write csv, otherwise xml
    private static final boolean WRITE_CSV = true;
    String CSV_EXPORT_DB_DIR = "csv_db_export";

    /**
     * See exportTables() below, this one with default charset UTF-8
     */
    @Override
    public void exportTables(String destinationFile) throws Exception {
        exportTables(null, destinationFile, Charset.forName("UTF-8"));
    }

    /**
     * See exportTables() below, this one with default charset UTF-8
     */
    @Override
    public void exportTables(String[] tableNames, String destinationFile) throws Exception {
        exportTables(tableNames, destinationFile, Charset.forName("UTF-8"));
    }

    /**
     * exportTables() is used to produce human readable (initial DB) xml file
     * all fields are saved in CDATA, except numerics
     *
     * @param tableNames
     * @param destinationFile
     * @param charset
     * @throws Exception
     */
    @Override
    public void exportTables(String[] tableNames, String destinationFile, Charset charset) throws Exception {
        IDatabaseConnection connection = new DatabaseConnection(getConnectionFromSpringTransaction());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        IDataSet dataSet;
        if (tableNames != null) {
            // partial database export
            QueryDataSet partialDataSet = new QueryDataSet(connection);
            for (String tableName : tableNames) {
                partialDataSet.addTable(tableName);
            }
            dataSet = partialDataSet;
        } else {
            //full dataSet by default
            dataSet = connection.createDataSet();
        }
        //DatabaseSequenceFilter - order tables according to foreign keys
        ITableFilter filter = new DatabaseSequenceFilter(connection);
        if (WRITE_CSV) {
            CsvDataSetWriter csvWriter = new CsvDataSetWriter(CSV_EXPORT_DB_DIR);
            csvWriter.write(new FilteredDataSet(filter, dataSet));
        } else {
            XmlDataSetWriter xmlDataSetWriter = new XmlDataSetWriter(new FileOutputStream(destinationFile),
                    charset.name()) {
                @Override
                protected void writeValue(String stringValue) throws IOException {
                    //write everything in CDATA except numbers
                    if (Pattern.matches(NUMERIC_REGEX, stringValue)) {
                        super.writeValue(stringValue);
                    } else {
                        super.writeValueCData(stringValue);
                    }
                }
            };
            xmlDataSetWriter.write(new FilteredDataSet(filter, dataSet));
        }
    }

    protected Connection getConnectionFromSpringTransaction() {
        for (Object value : TransactionSynchronizationManager.getResourceMap().values()) {
            if (value instanceof ConnectionHolder) {
                ConnectionHolder connectionHolder = (ConnectionHolder) value;
                return connectionHolder.getConnection();
            }
        }
        throw new RuntimeException("unable to get Connection from Spring transaction");
    }
}