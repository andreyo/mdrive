package mdrive.business.dao.impl;

import mdrive.business.config.JpaTestConfig;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.I18NameBean;
import mdrive.business.util.DBUnitDataExporter;
import mdrive.business.type.GeoObjectTypeCode;
import mdrive.business.util.Translit;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by User on 01.02.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JpaTestConfig.class)
@Transactional
public class I18NameDaoImplTest {

    private static final Logger log = Logger.getLogger(I18NameDaoImplTest.class);

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    DBUnitDataExporter exporter;

    @Test
    @Rollback(false)
    public void setEnglishColumnsFromUkrainian() throws Exception {
        String geoObjectsWithEmptyNames =
                "from GeoObjectBean go join fetch go.objectI18Name objectName where go.geoObjectTypeBean.typeCode in (:typeCode1, :typeCode2) and length(objectName.valueEn) = 0";
        Query query = entityManager.createQuery(geoObjectsWithEmptyNames, GeoObjectBean.class)
                .setParameter("typeCode1", GeoObjectTypeCode.STREET)
                .setParameter("typeCode2", GeoObjectTypeCode.BUILDING);

        query.setMaxResults(200);
        List<GeoObjectBean> resultList;
        while ((resultList = query.getResultList()).size() > 0) {
            for (GeoObjectBean geoObjectBean : resultList) {
                I18NameBean nameBean = geoObjectBean.getObjectI18Name();
                String valueEn = Translit.translitRusUkr2En(nameBean.getValueUk());
                if (StringUtils.isBlank(valueEn)) {
                    valueEn = "0_";
                }
                nameBean.setValueEn(valueEn);
            }
            entityManager.flush();
            entityManager.clear();
            log.info("updated " + resultList.size());
        }
        log.info("exporting");
        exporter.exportTables(new String[]{"I18NAME"}, "csv_db_names_export");
    }

}
