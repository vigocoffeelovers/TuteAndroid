package vigocoffeelovers.tute.game;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Human extends Player {

    public Human(String name) {
        super(name);
    }

    /**
     * Human interactions will be handled on the UI not here, the function will return null in order to stop the execution.
     * When the user touches a card it will turn on an event which will continue with the execution
     * @return null
     */
    @Override
    public Cards playCard(int timeToThink) {
        return null;
    }
}