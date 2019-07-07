package com.videlov.cyclist;

import com.videlov.cyclist.decompiler.PackageAnalyzer;
import com.videlov.cyclist.graph.DirectedGraph;
import com.videlov.cyclist.graph.StronglyConnectedComponents;

import java.util.*;

public class Main {
    public static void main(String... args) {
        if (args.length < 1) {
            System.err.println("Supply a jar path.");
            System.exit(1);
        }
        String jarPath = args[0];
        PackageAnalyzer packageAnalyzer = new PackageAnalyzer();

        DirectedGraph dg = DirectedGraph.from(packageAnalyzer.getPackages(jarPath));

        printGroups(StronglyConnectedComponents.findGroups(dg));
    }

    private static void printGroups(Collection<Set<String>> groups) {
        for (Set<String> group : groups) {
            System.out.println("============================================");
            for (String packageName : group) {
                System.out.println(packageName);
            }
        }
    }
}
