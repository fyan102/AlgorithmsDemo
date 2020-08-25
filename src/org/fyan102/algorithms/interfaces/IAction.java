package org.fyan102.algorithms.interfaces;

/**
 * The IAction class provide an interface for an action that a state of a problem can take
 *
 * @author Fan
 * @version 0.0
 */
@FunctionalInterface
public interface IAction {
    /**
     * The method for an action that one state can take
     *
     * @return a new state
     */
    IStateRepresent method();
}
