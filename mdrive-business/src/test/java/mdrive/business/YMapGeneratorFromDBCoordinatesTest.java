package mdrive.business;

import org.junit.Ignore;
import org.junit.Test;
import scriptella.execution.EtlExecutor;
import scriptella.execution.EtlExecutorException;

/**
 * User: andrey.osipov
 * Date: 10/5/11
 * Time: 4:39 PM
 */
public class YMapGeneratorFromDBCoordinatesTest {

    @Ignore
    @Test
    public void generate() throws EtlExecutorException {
        EtlExecutor.newExecutor(getClass().getResource("ymaps-generator-etl.xml")).execute();
    }
}