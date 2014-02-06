package mdrive.business.service.bot;

import mdrive.business.dao.GeoObjectDao;
import mdrive.business.dao.GoBidDao;
import mdrive.business.model.GeoObjectBean;
import mdrive.business.model.GoBidBean;
import mdrive.business.type.GeoObjectTypeCode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * User: andrey.osipov
 */
public class PassengerBot {
    public static final Long DELAY = 5L;

    private volatile boolean isOn;
    ScheduledFuture<Object> schedule;


    private static final Logger log = Logger.getLogger(PassengerBot.class);

    @Autowired
    private GoBidDao goBidDao;

    @Autowired
    private GeoObjectDao geoObjectDao;

    @Autowired
    ScheduledExecutorService scheduledExecutorService;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean onOff) {
        this.isOn = onOff;
        if (isOn && schedule == null) {
            schedule = scheduledExecutorService.schedule(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return PassengerBot.this.call();
                }

            }, DELAY, TimeUnit.SECONDS);
        }
    }


    @Transactional
    public GoBidBean call() throws Exception {
        if (!isOn()) {
            log.info("Off - not fired");
            return null;
        }
        log.info("On - fired");
        GoBidBean goBidBean = new GoBidBean();
        goBidBean.setFromGeoObjectBean(getRandomFromGeoObjectBean());
        goBidBean.setToGeoObjectBean(getRandomToGeoObjectBean());
        goBidBean.setPrice("10");
        goBidDao.persist(goBidBean);
        log.debug("new bid added: " + goBidBean);
        return goBidBean;
    }

    private GeoObjectBean getRandomFromGeoObjectBean() {
        return geoObjectDao.getOneGeoObjectsByLocationAndRadius(50f, 30f, 0.51f, GeoObjectTypeCode.BUILDING);
    }

    private GeoObjectBean getRandomToGeoObjectBean() {
        return geoObjectDao.getOneGeoObjectsByLocationAndRadius(50f, 30f, 0.7f, GeoObjectTypeCode.BUILDING);
    }
}