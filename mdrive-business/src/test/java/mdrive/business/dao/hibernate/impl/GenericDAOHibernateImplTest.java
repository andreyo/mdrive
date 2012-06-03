package mdrive.business.dao.hibernate.impl;

import mdrive.business.bean.GoBidBean;
import mdrive.business.bean.nullobject.NullGoBidBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.impl.CriteriaImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: andrey.osipov
 * Date: 1/24/12
 * Time: 5:54 PM
 */
public class GenericDAOHibernateImplTest {

    private HibernateTemplate hibernateTemplateMock;
    private GenericDAOHibernateImpl genericDAOHibernate;

    @Before
    public void before() {
        hibernateTemplateMock = mock(HibernateTemplate.class);
        genericDAOHibernate = new GenericDAOHibernateImpl<GoBidBean, Long>(GoBidBean.class) {
            @Override
            public GoBidBean getNullObject() {
                return NullGoBidBean.instance;
            }
        };
        genericDAOHibernate.setHibernateTemplate(hibernateTemplateMock);
    }

    @Test
    public void testFindById() throws Exception {
        GoBidBean goBidBean = new GoBidBean();
        goBidBean.setComment("hello");
        when(hibernateTemplateMock.get(GoBidBean.class, 11)).thenReturn(goBidBean);
        System.out.println(genericDAOHibernate.findById(11));
    }

    @Test
    public void testCreateExampleCriteria() throws Exception {
        GoBidBean goBidBean = new GoBidBean();
        goBidBean.setComment("hello");
        goBidBean.setPrice("25");

        when(hibernateTemplateMock.findByCriteria(argThat(new ValidCriteria(goBidBean))))
                .thenReturn(Arrays.asList(goBidBean));
        List resultList = genericDAOHibernate.findByExample(goBidBean);
        assertNotNull(resultList);
        assertTrue(resultList.contains(goBidBean));
    }

    class ValidCriteria extends ArgumentMatcher<DetachedCriteria> {
        private GoBidBean goBidBean;

        public ValidCriteria(GoBidBean goBidBean) {
            this.goBidBean = goBidBean;
        }

        public boolean matches(Object criteria) {
            CriteriaImpl criteriaImpl;
            Method getCriteriaImplMethod;
            try {
                getCriteriaImplMethod = DetachedCriteria.class.getDeclaredMethod("getCriteriaImpl", null);
                getCriteriaImplMethod.setAccessible(true);
                criteriaImpl = (CriteriaImpl) getCriteriaImplMethod.invoke(criteria, null);
            } catch (NoSuchMethodException e) {
                return false;
            } catch (InvocationTargetException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            }

            return criteriaImpl.toString().contains(goBidBean.toString());
        }
    }

}