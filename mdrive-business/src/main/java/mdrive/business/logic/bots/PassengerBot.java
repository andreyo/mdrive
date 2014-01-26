package mdrive.business.logic.bots;

import mdrive.business.dao.GoBidDAO;
import mdrive.business.model.GoBidBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: andrey.osipov
 */
public class PassengerBot implements InitializingBean {
    private static final Logger log = Logger.getLogger(PassengerBot.class);
    private EternalOnOffThread passengerBotThread;

    @Autowired
    private GoBidDAO goBidDAO;

    @Override
    public void afterPropertiesSet() throws Exception {
        passengerBotThread = new EternalOnOffThread() {
            @Override
            public void doWork() {
                GoBidBean goBidBean = new GoBidBean();
                goBidDAO.persist(goBidBean);
                log.debug("new bid added: " + goBidBean);
            }
        };
        passengerBotThread.setDaemon(true);//will not stop jvm from exiting
        passengerBotThread.start();
    }

    public void setActive(boolean value) {
        passengerBotThread.setActive(value);
    }

    public boolean isActive() {
        return passengerBotThread.isActive();
    }
}