package org.jhoule.inevitable;

import java.util.Collection;
import java.util.Date;

public class PostponableEvent extends RunningEvent {

    Date lastRun = null;
    Date nextRun = null;

    @Override
    protected boolean setup() {
        return true;
    }

    @Override
    public boolean perform() {
        return true;
    }

    @Override
    public Date getLastOccurence() {
        return lastRun;
    }

    @Override
    public Date getNextDue() {
        return nextRun;
    }

    @Override
    public Collection<IPostponement> getPostponements() {
        return null;
    }
}
