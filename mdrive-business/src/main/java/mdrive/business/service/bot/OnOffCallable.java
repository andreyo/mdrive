package mdrive.business.service.bot;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 26.01.14.
 */
public abstract class OnOffCallable<T> implements Callable<T>, InitializingBean {

    public static final Long DELAY = 20L;

    private volatile boolean isOn;

    @Autowired
    ScheduledExecutorService scheduledExecutorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduledExecutorService.schedule(this, DELAY, TimeUnit.SECONDS);
    }

    @Override
    public abstract T call() throws Exception;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean onOff) {
        this.isOn = onOff;
    }
}
