package mdrive.report;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.sql.DataSource;


@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
@TransactionConfiguration(transactionManager = "txTransactionManager")
public abstract class HsqldbTransactionalJUnit4SpringContextTests extends
        AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    @Qualifier(value = "dataSet")
    private ClassPathResource dataSet;

    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource dataSource;

    @Before
    public void init() throws Exception {
        IDatabaseConnection conn = null;
        IDataSet data = new XmlDataSet(dataSet.getInputStream());
        conn = new DatabaseConnection(dataSource.getConnection());
        DatabaseConfig config = conn.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        DatabaseOperation.CLEAN_INSERT.execute(conn, data);
    }
}