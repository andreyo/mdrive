package mdrive.report.model.tmp;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * User: andrey.osipov
 * Date: 1/27/12
 * Time: 2:11 PM
 */
public class ScriptRegistryEntityTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        Date date = new Date();
        ScriptRegistryEntity sre1 = getSRE(1L, "data", "description", "code1", ScriptType.SQL, date);
        ScriptRegistryEntity sre2 = getSRE(1L, "data", "description", "code1", ScriptType.SQL, date);
        assertTrue(sre1.equals(sre2));
    }

    public ScriptRegistryEntity getSRE(Long id, String data, String description, String scriptCode,
                                       ScriptType scriptType, Date updatedOn) {
        ScriptRegistryEntity scriptRegistryEntity = new ScriptRegistryEntity();
        scriptRegistryEntity.setId(id);
        scriptRegistryEntity.setData(data);
        scriptRegistryEntity.setDescription(description);
        scriptRegistryEntity.setScriptCode(scriptCode);
        scriptRegistryEntity.setScriptType(scriptType);
        scriptRegistryEntity.setUpdatedOn(updatedOn);
        return scriptRegistryEntity;
    }
}
