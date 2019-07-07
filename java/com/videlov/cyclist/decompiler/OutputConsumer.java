package com.videlov.cyclist.decompiler;

import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.api.SinkReturns;

import java.util.*;
import java.util.function.Consumer;

class OutputConsumer implements OutputSinkFactory {
    private Map<String, Set<String>> packages = new HashMap<>();

    private Consumer<SinkReturns.DecompiledMultiVer> dumpDecompiled =
            d -> {
                if (d.getRuntimeFrom() > 0) System.out.println("JRE above " + d.getRuntimeFrom());
                handleClass(d.getPackageName(), parseImportedPackages(d.getJava()));
            };

    @Override
    public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
        return Collections.singletonList(SinkClass.DECOMPILED_MULTIVER);
    }

    @Override
    public <T> Sink<T> getSink(SinkType sinkType, SinkClass sinkClass) {
        if (sinkType == SinkType.JAVA && sinkClass == SinkClass.DECOMPILED_MULTIVER) {
            return x -> dumpDecompiled.accept((SinkReturns.DecompiledMultiVer) x);
        }
        return ignore -> {};
    }

    private void handleClass(String packageName, Set<String> imports) {
        Set<String> packageImports = packages.get(packageName);
        if (packageImports != null) {
            packageImports.addAll(imports);
            packages.put(packageName, packageImports);
        } else {
            packages.put(packageName, imports);
        }
    }

    private Set<String> parseImportedPackages(String code) {
        Set<String> imports = new HashSet<>();
        for (String line : Arrays.asList(code.split("\n"))) {
            if (line.startsWith("import")) {
                String p = line.split("import ")[1].split("\\.[a-zA-Z]*;")[0];
                imports.add(p);
            }
        }
        return imports;
    }

    public Map<String, Set<String>> getPackages() {
        return packages;
    }
}
