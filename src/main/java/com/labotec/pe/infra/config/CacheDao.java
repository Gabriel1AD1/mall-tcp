package com.labotec.pe.infra.config;

import org.springframework.stereotype.Component;

@Component
public interface CacheDao<T> {

  void set(String key, T value, long ttlSeconds);
  void set(String key, T value); // sin ttl

  T get(String key, Class<T> clazz);

  void delete(String key);

  boolean exists(String key);
}
