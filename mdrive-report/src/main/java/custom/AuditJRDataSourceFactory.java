package custom;

/**
 * User: andrey.osipov
 * Date: 4/26/12
 * Time: 3:54 PM
 */

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.Date;
import java.util.Vector;

public class AuditJRDataSourceFactory {

    public static JRDataSource createBeanCollectionDatasource() {
        return new JRBeanCollectionDataSource(createAuditBeanCollection());
    }

    public static Vector<AuditBean> createAuditBeanCollection() {
        Vector<AuditBean> coll = new Vector<AuditBean>();

        AuditBean bean = new AuditBean();
        bean.setMethodName("createUser");
        bean.setTimestamp(new Date());
        bean.setContent("we0-gaspidhhh");
        coll.add(bean);

        bean = new AuditBean();
        bean.setMethodName("deleteUser");
        bean.setTimestamp(new Date());
        bean.setContent("h-dhf09ehrj");
        coll.add(bean);

        bean = new AuditBean();
        bean.setMethodName("updateUser");
        bean.setTimestamp(new Date());
        bean.setContent("uyghjgohww");
        coll.add(bean);

        bean = new AuditBean();
        bean.setMethodName("createUser");
        bean.setTimestamp(new Date());
        bean.setContent(";ajhghghghg");
        coll.add(bean);

        return coll;
    }
}
