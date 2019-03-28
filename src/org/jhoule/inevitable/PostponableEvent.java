package org.jhoule.inevitable;

import java.util.Calendar;
import java.util.Collection;

public abstract class PostponableEvent extends RunningEvent {

    Calendar lastRun = null;
    Calendar nextRun = null;

    @Override
    protected boolean setup() {
        return true;
    }

    @Override
    public Calendar getLastOccurence() {
        return lastRun;
    }

    @Override
    public Calendar getNextDue() {
        return nextRun;
    }

    @Override
    public Collection<IPostponement> getPostponements() {
        return null;
    }
}
