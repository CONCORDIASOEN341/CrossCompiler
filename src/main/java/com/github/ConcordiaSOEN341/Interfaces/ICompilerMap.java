package com.github.ConcordiaSOEN341.Interfaces;

public interface ICompilerMap<K, V> {
    void initializeMap();

    V getValue(K key);
}
