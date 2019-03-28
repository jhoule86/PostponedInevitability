package org.jhoule.inevitable;

public interface IPostponement {

    /**
     * Make an attempt to postpone
     * @return postponement time in milliseconds
     */
    PostponementResponse attempt();
}
