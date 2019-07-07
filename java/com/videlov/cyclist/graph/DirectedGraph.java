package com.videlov.cyclist.graph;

import java.util.*;
import com.google.common.collect.ImmutableSet;

public class DirectedGraph implements Iterable<String> {

    private final Map<String, Set<String>> nodes = new HashMap<>();

    private DirectedGraph() {}

    public void push(String node) {
        if (!nodes.containsKey(node)) {
            nodes.put(node, new HashSet<>());
        }
    }

    public void connect(String from, String to) {
        if (!nodes.containsKey(from) || !nodes.containsKey(to)) {
            throw new NoSuchElementException("Nodes must already be in the graph.");
        }
        nodes.get(from).add(to);
    }

    public Set<String> edgesFrom(String node) {
        Set<String> edgesFrom = nodes.get(node);
        if (edgesFrom == null) {
            throw new NoSuchElementException("Node does not exist.");
        }
        return ImmutableSet.copyOf(edgesFrom);
    }

    public Iterator<String> iterator() {
        return nodes.keySet().iterator();
    }

    public static DirectedGraph from(Map<String, Set<String>> map) {
        DirectedGraph dg = getInstance();
        map.keySet().forEach(dg::push);

        for (Map.Entry<String, Set<String>> e : map.entrySet()) {
            e.getValue().forEach(x -> dg.connect(e.getKey(), x));
        }
        return dg;
    }

    static DirectedGraph getInstance() {
        return new DirectedGraph();
    }
}
