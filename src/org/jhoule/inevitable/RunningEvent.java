package org.jhoule.inevitable;

import java.util.Calendar;
import java.util.Collection;

public abstract class RunningEvent implements IPostponableRunningEvent {

    private Thread mThread = null;
    class SetupException extends RuntimeException
    {

    }

    public RunningEvent()
    {
        if (! setup())
        {
            throw new SetupException();
        }

        mThread = new Thread(this);
    }

    protected abstract boolean setup();

    @Override
    public void run()
    {
        while (getNextDue() != null)
        {
            Calendar now = Calendar.getInstance();
            Calendar nd = getNextDue();
            if (nd.after(now))
            {
                long w = now.getTimeInMillis() - nd.getTimeInMillis();
                mThread.sleep(w);
            }

            Collection<IPostponement> pps = getPostponements();

            boolean skipPerform = false;
            Long postponmentInterval = null;
            Collection<IAction> prefix = null;
            Collection<IAction> epilogue = null;

            if (pps != null)
            {
                for (IPostponement p: pps)
                {
                   PostponementResponse answer = p.attempt();

                   if (answer == PostponementResponse.CANCEL)
                   {
                       // we're done performing.
                       setNextDue(null);
                       return;
                   }

                   if (answer == null || answer == PostponementResponse.NONE)
                   {
                       // try the next way to reach out to the user
                       continue;
                   }

                   skip = answer == PostponementResponse.SKIP;
                   if(skip)
                   {
                       // don't perform any actions.
                       // don't ask anymore questions.
                       break;
                   }

                   if (answer == PostponementResponse.NEXT_CYCLE)
                   {
                       postponmentInterval = getRegularInterval();
                       break;
                   }

                   if (answer instanceof CustomPostponementResponse)
                   {
                       CustomPostponementResponse r = ((CustomPostponementResponse) answer);
                       skip = r.avoidRegularActions();

                       // user gave us a new timeframe
                       Long ci = ((CustomPostponementResponse) answer).getCustomInterval();
                       if (ci != null)
                       {
                           postponmentInterval = ci;
                       }

                       prefix = r.getCustomPrep();
                       epilogue = r.getCustomEpilog();
                       // don't ask more questions
                       break;
                   }

                   String s = answer.getClass().getName();

                   System.err.println("Ignoring unknown Postponement answer of type " + s);
                }
            }

            // we have no more postponements to run

            if (postponmentInterval != null && postponmentInterval > 0)
            {
                // we've been told to wait.
                mThread.wait(postponmentInterval);
            }

            if (prefix != null)
            {
                for (IAction a: prefix)
                {
                    a.attempt();
                }
            }

            Collection<IAction> regular = getRegularActions();
            if (! (skipPerform || regular == null))
            {
                for (IAction b: regular)
                {
                    b.attempt();
                }
            }

            if (epilogue != null)
            {
                for (IAction c: epilogue)
                {
                    c.attempt();
                }
            }


            Long interval = getRegularInterval();
            if (interval == null || interval < 0)
            {
                // this is not supposed to happen again.
                // kill the thread!
                return;
            }

            // schedule this
            Calendar next = Calendar.getInstance();
            next.setTimeInMillis(next.getTimeInMillis() + interval);
            setNextDue(next);
        } // while loop that checks next due date
    } // thread death
}
