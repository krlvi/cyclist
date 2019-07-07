package com.videlov.cyclist;

import com.videlov.cyclist.decompiler.PackageAnalyzer;

import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String... args) {
        if (args.length < 1) {
            System.err.println("Supply a jar path.");
            System.exit(1);
        }
        String jarPath = args[0];
        PackageAnalyzer packageAnalyzer = new PackageAnalyzer();
        Map<String, Set<String>> packages = packageAnalyzer.getPackages(jarPath);

        System.out.println(packages.size());
    }
}
