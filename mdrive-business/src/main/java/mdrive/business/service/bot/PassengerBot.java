package mdrive.business.service.bot;

import mdrive.business.dao.GoBidDao;
import mdrive.business.model.GoBidBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: andrey.osipov
 */
public class PassengerBot extends OnOffCallable<GoBidBean> {
    private static final Logger log = Logger.getLogger(PassengerBot.class);

    @Autowired
    private GoBidDao goBidDao;


    @Override
    public GoBidBean call() throws Exception {
        if (!isOn()) return null;
        //TODO:  Random From + To
        GoBidBean goBidBean = new GoBidBean();
        goBidDao.persist(goBidBean);
        log.debug("new bid added: " + goBidBean);
        return goBidBean;
    }
}