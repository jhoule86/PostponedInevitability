package org.jhoule.inevitable;

import java.util.Calendar;

public interface IChronological {

    Calendar getLastOccurence();

    Calendar getNextDue();

    Calendar setNextDue(Calendar c);
}
