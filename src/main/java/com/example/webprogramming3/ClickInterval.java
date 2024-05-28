package com.example.webprogramming3;

import com.example.webprogramming3.ClickIntervalMBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ClickInterval implements ClickIntervalMBean, Serializable {
    private List<Long> clickTimestamps = new ArrayList<>();
    private long averageInterval = 0;

    @Inject
    private SimpleAgent simpleAgent;
    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused){
        simpleAgent.registerBean(this, "area");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused){
        simpleAgent.unregisterBean(this);
    }

    @Override
    public synchronized void addClick() {
        clickTimestamps.add(System.nanoTime());
    }

    @Override
    public synchronized double getAverageInterval() {
        if (clickTimestamps.size() < 2) {
            return 0;
        }

        long totalInterval = 0;
        for (int i = 1; i < clickTimestamps.size(); i++) {
            totalInterval += (clickTimestamps.get(i) - clickTimestamps.get(i - 1));
        }
        return totalInterval / (double) (clickTimestamps.size() - 1);
    }
}