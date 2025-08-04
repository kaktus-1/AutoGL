package de.kaktus4.autogl.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.TimeUnit;

public class CacheManager {
  private final Cache<String, Boolean> cache;

  public CacheManager() {
    this.cache = CacheBuilder.newBuilder()
        .maximumSize(200)
        .expireAfterWrite(90, TimeUnit.SECONDS)
        .build();
  }

  public boolean contains(String message, String host) {
    return cache.getIfPresent(host + "|" + message) != null;
  }

  /**
  * Checks if the message qualifies
  * @return true if cache does not contain the message, or it qualifies (matched regex before) | false if it is not null and does not qualify
  */
  public boolean qualifies(String message, String host) {
    Boolean qualifies = cache.getIfPresent(host + "|" + message);
    return qualifies == null || qualifies;
  }

  public void add(String message, Boolean qualifies, String host) {
    cache.put(host + "|" + message, qualifies);
  }

  public void clear() {
    cache.invalidateAll();
  }

}
