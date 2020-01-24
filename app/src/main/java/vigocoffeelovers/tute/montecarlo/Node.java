package vigocoffeelovers.tute.montecarlo;

import vigocoffeelovers.tute.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Node {

    private State state;
    private Node parent;
    ArrayList<Node> childArray;

    Node(Game game) {
        this.state = new State(game);
        childArray = new ArrayList<>();
    }

    Node(State state) {
        this.state = state;
        childArray = new ArrayList<>();
    }

    private Node(Node node) {
        this.childArray = new ArrayList<>();
        this.state = new State(node.getState());
        if (node.getParent() != null)
            this.parent = node.getParent();
        ArrayList<Node> childArray = node.getChildArray();
        for (Node child : childArray) {
            this.childArray.add(new Node(child));
        }
    }

    State getState() {
        return state;
    }

    private Node getParent() {
        return parent;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    ArrayList<Node> getChildArray() {
        return childArray;
    }

    Node getRandomChildNode() {
        return this.childArray.get((int)(Math.random()*this.childArray.size()));
    }
}
