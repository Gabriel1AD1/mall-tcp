package com.labotec.pe.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCacheDao<T> implements CacheDao<T> {

  private final StringRedisTemplate redis;
  private final ObjectMapper mapper ;

  @Override
  public void set(String key, T value, long ttlSeconds) {
    try {
      String json = mapper.writeValueAsString(value);
      ValueOperations<String, String> ops = redis.opsForValue();
      ops.set(key, json, Duration.ofSeconds(ttlSeconds));
    } catch (Exception e) {
      log.error("Error setting cache for key {}", key, e);
    }
  }

  @Override
  public void set(String key, T value) {
    try {
      String json = mapper.writeValueAsString(value);
      redis.opsForValue().set(key, json);
    } catch (Exception e) {
      log.error("Error setting cache for key {}", key, e);
    }
  }

  @Override
  public T get(String key, Class<T> clazz) {
    try {
      String json = redis.opsForValue().get(key);
      if (json == null) return null;

      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      log.error("Error getting cache for key {}", key, e);
      return null;
    }
  }

  @Override
  public void delete(String key) {
    redis.delete(key);
  }

  @Override
  public boolean exists(String key) {
    return redis.hasKey(key);
  }

  /**
   * Métodos extra para LISTAS usando TypeReference
   */
  public <E> List<E> getList(String key, Class<E> elementType) {
    try {
      String json = redis.opsForValue().get(key);
      if (json == null) return List.of();

      return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, elementType));

    } catch (Exception e) {
      log.error("Error getting list cache for key {}", key, e);
      return List.of();
    }
  }

  public <E> void setList(String key, List<E> list, long ttlSeconds) {
    try {
      String json = mapper.writeValueAsString(list);
      redis.opsForValue().set(key, json, Duration.ofSeconds(ttlSeconds));
    } catch (Exception e) {
      log.error("Error setting list cache for key {}", key, e);
    }
  }
}
