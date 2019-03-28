package org.jhoule.inevitable;

import java.util.Collection;

public class CustomPostponementResponse extends  PostponementResponse
{
    public final long interval;

    public final Collection<IAction> actions;

    public static final long DEFAULT_WAIT = 0;

    private CustomPostponementResponse()
    {
        this(DEFAULT_WAIT);
    }

    public CustomPostponementResponse(long val)
    {
        this(val, null);
    }

    public CustomPostponementResponse(Collection<IAction>  acts)
    {
        this(DEFAULT_WAIT, acts);
    }

    public CustomPostponementResponse(long val, Collection<IAction>  acts)
    {
        interval = val;
        actions = acts;
    }

    public Long getCustomInterval()
    {
        return interval;
    }

    public Collection<IAction> getCustomPrep()
    {
        return actions;
    }

    public Collection<IAction> getCustomEpilog()
    {
        return actions;
    }

    public boolean avoidRegularActions()
    {
        return false;
    }
}
