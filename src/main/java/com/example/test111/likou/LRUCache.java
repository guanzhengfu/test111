package com.example.test111.likou;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LRUCache extends LinkedHashMap<Integer, Integer> {

  private int capacity;

  public LRUCache(int capacity) {
    super(capacity, 0.75F, true);
    this.capacity = capacity;
  }

  public int get(int key) {
    return super.getOrDefault(key, -1);
  }

  // 这个可不写
  public void put(int key, int value) {
    super.put(key, value);
  }

  @Override
  protected boolean removeEldestEntry(Entry<Integer, Integer> eldest) {
    return size() > capacity;
  }
}
