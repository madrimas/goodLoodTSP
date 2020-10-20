package org.uek.graph;

import java.util.Objects;

public class Edge {
    private int from;
    private int to;
    private String color;

    public Edge(int from, int to, String color) {
        this.from = from;
        this.to = to;
        this.color = color;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return from == edge.from &&
                to == edge.to || to == edge.from &&
                from == edge.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
