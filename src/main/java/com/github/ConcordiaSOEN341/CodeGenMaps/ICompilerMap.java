package com.github.ConcordiaSOEN341.CodeGenMaps;

public interface ICompilerMap<K,V> {
    void initializeMap();
    V getValue(K key);
}
