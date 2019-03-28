package org.jhoule.inevitable;

import java.util.Collection;

public interface IRecurringEvent extends IChronologicalEvent {

    Long getRegularInterval();

    Collection<IAction> getRegularActions();
}
