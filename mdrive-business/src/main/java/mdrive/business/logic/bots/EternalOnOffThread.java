package mdrive.business.logic.bots;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 26.03.11
 * Time: 18:31
 * To change this template use File | Settings | File Templates.
 */

/**
 * Thread which will be always running, but in ON and OFF state (doing work or not)
 */
abstract class EternalOnOffThread extends Thread {
    private static final Logger log = Logger.getLogger(EternalOnOffThread.class);
    private int sleepTime = 3000;
    private boolean active;

//TODO: implement OneShotOnOff  threads instead of Eternal

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(sleepTime);
                if (isActive()) {
                    log.trace("doing work");
                    doWork();
                } else {
                    log.trace("not doing");
                }
            }
        } catch (InterruptedException e) {
            log.debug("Received Interrupt signal");
        }
    }

    public abstract void doWork();

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setActive(boolean value) {
        active = value;
        log.debug("set active = " + value);
    }

    public boolean isActive() {
        log.debug("active = " + active);
        return active;
    }
}