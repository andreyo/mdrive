package mdrive.business;

import mdrive.business.service.DBUnitDataLoader;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml"})
public abstract class HsqldbJUnit4SpringContextTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    DBUnitDataLoader dbUnitDataLoader;


    public void initTestData() throws Exception {
        dbUnitDataLoader.initTestData();
    }

    public void initTestDataXml() throws Exception {
        dbUnitDataLoader.initTestDataXml();
    }

    public void initTestDataXml(String initialDbPath) throws Exception {
        dbUnitDataLoader.initTestDataXml(initialDbPath);
    }

    public void initTestDataCsv() throws Exception {
        dbUnitDataLoader.initTestDataCsv();
    }

    public void initTestDataCsv(String initialDbPath) throws Exception {
        dbUnitDataLoader.initTestDataCsv(initialDbPath);
    }

    public DataSource getDataSource() {
        return dbUnitDataLoader.getDataSource();
    }
}