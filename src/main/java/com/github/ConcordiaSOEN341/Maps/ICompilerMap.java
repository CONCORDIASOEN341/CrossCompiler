package com.github.ConcordiaSOEN341.Maps;

public interface ICompilerMap<K,V> {
    void initializeMap();
    V getValue(K key);
}
