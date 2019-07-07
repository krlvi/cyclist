package com.videlov.cyclist.decompiler;

import org.benf.cfr.reader.api.CfrDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PackageAnalyzer {
    private final OutputConsumer consumer;
    private final CfrDriver driver;

    public PackageAnalyzer() {
        consumer = new OutputConsumer();
        driver = new CfrDriver.Builder().withOutputSink(consumer).build();
    }

    public Map<String, Set<String>> getPackages(String jarPath) {
        driver.analyse(Collections.singletonList(jarPath));
        return filterExternal(consumer.getPackages());
    }

    private Map<String, Set<String>> filterExternal(Map<String, Set<String>> in) {
        Map<String, Set<String>> out = new HashMap<>();
        Set<String> keys = in.keySet();
        for (String pkg : keys) {
            Set<String> imports =
                    in.get(pkg).stream()
                            .filter(keys::contains)
                            .filter(x -> !x.equals(pkg))
                            .collect(Collectors.toSet());
            out.put(pkg, imports);
        }
        return out;
    }
}
