package com.example.mynewapplication.montecarlo;

import com.example.mynewapplication.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Node {

    State state;
    Node parent;
    ArrayList<Node> childArray;

    public Node(Game game) {
        this.state = new State(game);
        childArray = new ArrayList<>();
    }

    public Node(State state) {
        this.state = state;
        childArray = new ArrayList<>();
    }

    public Node(Node node) {
        this.childArray = new ArrayList<>();
        this.state = new State(node.getState());
        if (node.getParent() != null)
            this.parent = node.getParent();
        ArrayList<Node> childArray = node.getChildArray();
        for (Node child : childArray) {
            this.childArray.add(new Node(child));
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChildArray() {
        return childArray;
    }

    public void setChildArray(ArrayList<Node> childArray) {
        this.childArray = childArray;
    }

    public Node getRandomChildNode() {
        return this.childArray.get((int)(Math.random()*this.childArray.size()));
    }

    public Node getChildWithMaxScore() {
        return Collections.max(
            this.childArray,
            Comparator.comparing(c -> { return c.getState().getVisitCount(); }) //TODO Score or VisitCount ?¿?
        );
    }

}
