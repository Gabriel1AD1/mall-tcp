package com.labotec.pe.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheDao<T> implements CacheDao<T> {

  private final StringRedisTemplate redis;
  private final ObjectMapper mapper;

  @Override
  public void set(String key, T value, long ttlSeconds) {
    long start = System.currentTimeMillis();
    try {
      String json = mapper.writeValueAsString(value);
      redis.opsForValue().set(key, json, Duration.ofSeconds(ttlSeconds));

      log.debug("[CACHE-SET] key={} ttl={}s type={} time={}ms",
        key, ttlSeconds, value.getClass().getSimpleName(),
        System.currentTimeMillis() - start);

    } catch (Exception e) {
      log.error("[CACHE-ERROR-SET] Error setting key={} valueType={}",
        key, value != null ? value.getClass().getName() : "null", e);
    }
  }

  @Override
  public void set(String key, T value) {
    long start = System.currentTimeMillis();
    try {
      String json = mapper.writeValueAsString(value);
      redis.opsForValue().set(key, json);

      log.debug("[CACHE-SET] key={} (no TTL) type={} time={}ms",
        key, value.getClass().getSimpleName(),
        System.currentTimeMillis() - start);

    } catch (Exception e) {
      log.error("[CACHE-ERROR-SET] key={} error={}", key, e.getMessage(), e);
    }
  }

  @Override
  public T get(String key, Class<T> clazz) {
    long start = System.currentTimeMillis();
    try {
      String json = redis.opsForValue().get(key);

      if (json == null) {
        log.warn("[CACHE-MISS] key={} type={}", key, clazz.getSimpleName());
        return null;
      }

      T value = mapper.readValue(json, clazz);

      log.debug("[CACHE-HIT] key={} type={} time={}ms",
        key, clazz.getSimpleName(),
        System.currentTimeMillis() - start);

      return value;

    } catch (Exception e) {
      log.error("[CACHE-ERROR-GET] key={} expectedType={} error={}",
        key, clazz.getName(), e.getMessage(), e);
      return null;
    }
  }

  @Override
  public void delete(String key) {
    try {
      Boolean removed = redis.delete(key);

      if (Boolean.TRUE.equals(removed)) {
        log.debug("[CACHE-DEL] key={} removed=true", key);
      } else {
        log.warn("[CACHE-DEL] key={} removed=false (key not found)", key);
      }

    } catch (Exception e) {
      log.error("[CACHE-ERROR-DEL] key={} error={}", key, e.getMessage(), e);
    }
  }

  @Override
  public boolean exists(String key) {
    try {
      boolean exists = Boolean.TRUE.equals(redis.hasKey(key));
      log.debug("[CACHE-EXISTS] key={} exists={}", key, exists);
      return exists;

    } catch (Exception e) {
      log.error("[CACHE-ERROR-EXISTS] key={} error={}", key, e.getMessage(), e);
      return false;
    }
  }

  /** LIST SUPPORT **/
  public <E> List<E> getList(String key, Class<E> elementType) {
    long start = System.currentTimeMillis();
    try {
      String json = redis.opsForValue().get(key);

      if (json == null) {
        log.warn("[CACHE-MISS-LIST] key={} elementType={}", key, elementType.getSimpleName());
        return List.of();
      }

      List<E> list = mapper.readValue(
        json,
        mapper.getTypeFactory().constructCollectionType(List.class, elementType)
      );

      log.debug("[CACHE-HIT-LIST] key={} size={} type={} time={}ms",
        key, list.size(), elementType.getSimpleName(),
        System.currentTimeMillis() - start);

      return list;

    } catch (Exception e) {
      log.error("[CACHE-ERROR-GET-LIST] key={} elementType={} error={}",
        key, elementType.getName(), e.getMessage(), e);
      return List.of();
    }
  }

  public <E> void setList(String key, List<E> list, long ttlSeconds) {
    long start = System.currentTimeMillis();
    try {
      String json = mapper.writeValueAsString(list);
      redis.opsForValue().set(key, json, Duration.ofSeconds(ttlSeconds));

      log.debug("[CACHE-SET-LIST] key={} size={} ttl={}s type={} time={}ms",
        key, list.size(), ttlSeconds,
        list.isEmpty() ? "empty" : list.get(0).getClass().getSimpleName(),
        System.currentTimeMillis() - start);

    } catch (Exception e) {
      log.error("[CACHE-ERROR-SET-LIST] key={} error={}", key, e.getMessage(), e);
    }
  }
}
