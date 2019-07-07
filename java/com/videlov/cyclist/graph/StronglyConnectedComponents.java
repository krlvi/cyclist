package com.videlov.cyclist.graph;

import java.util.*;

public class StronglyConnectedComponents {
    private StronglyConnectedComponents() {}

    public static Collection<Set<String>> findGroups(DirectedGraph g) {
        Map<String, Integer> scc = findSCC(g);
        Map<Integer, Set<String>> out = new HashMap<>();

        for (Map.Entry<String, Integer> e : scc.entrySet()) {
            Set<String> p = out.get(e.getValue());
            if (p != null) {
                p.add(e.getKey());
                out.put(e.getValue(), p);
            } else {
                Set<String> ps = new HashSet<>();
                ps.add(e.getKey());
                out.put(e.getValue(), ps);
            }
        }
        return out.values();
    }

    private static Map<String, Integer> findSCC(DirectedGraph g) {
        Stack<String> visitOrder = dfsVisitOrder(transpose(g));
        Map<String, Integer> result = new HashMap<>();
        int i = 0;
        while (!visitOrder.isEmpty()) {
            String startPoint = visitOrder.pop();
            if (result.containsKey(startPoint)) continue;
            markReachableNodes(startPoint, g, result, i);
            i++;
        }
        return result;
    }

    private static DirectedGraph transpose(DirectedGraph g) {
        DirectedGraph result = DirectedGraph.getInstance();
        for (String node : g) {
            result.push(node);
        }
        for (String node : g) {
            for (String endpoint : g.edgesFrom(node)) {
                result.connect(endpoint, node);
            }
        }
        return result;
    }

    private static Stack<String> dfsVisitOrder(DirectedGraph g) {
        Stack<String> result = new Stack<>();
        Set<String> visited = new HashSet<>();
        for (String node : g) {
            recExplore(node, g, result, visited);
        }
        return result;
    }

    private static void recExplore(
            String node, DirectedGraph g, Stack<String> result, Set<String> visited) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        for (String endpoint : g.edgesFrom(node)) {
            recExplore(endpoint, g, result, visited);
        }
        result.push(node);
    }

    private static void markReachableNodes(
            String node, DirectedGraph g, Map<String, Integer> result, int label) {
        if (result.containsKey(node)) {
            return;
        }
        result.put(node, label);
        for (String endpoint : g.edgesFrom(node)) {
            markReachableNodes(endpoint, g, result, label);
        }
    }
}
