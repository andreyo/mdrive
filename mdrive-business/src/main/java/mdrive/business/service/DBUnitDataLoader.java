package mdrive.business.service;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 13.08.11
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public interface DBUnitDataLoader {

    final static String INITIAL_DB_XML_PATH = "initialdb.xml";
    final static String INITIAL_DB_CSV_PATH = "csv_initial_db";

    void initTestData() throws Exception;

    void initTestDataXml() throws Exception;

    void initTestDataXml(String initialDbPath) throws Exception;

    void initTestDataCsv() throws Exception;

    void initTestDataCsv(String initialDbPath) throws Exception;

    DataSource getDataSource();
}
