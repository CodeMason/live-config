package com.hubspot.liveconfig.resolver;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class EnvironmentResolver extends MapResolver {

  public EnvironmentResolver() {
    super(transformEnvMap(System.getenv()));
  }

  @VisibleForTesting
  EnvironmentResolver(Map<String, String> map) {
    super(transformEnvMap(map));
  }

  @Override
  public Set<String> keySet() {
    return Collections.emptySet();
  }

  private static Map<String, String> transformEnvMap(Map<String, String> envMap) {
    Map<String, String> map = Maps.newHashMapWithExpectedSize(envMap.size());
    for (Map.Entry<String, String> entry : envMap.entrySet()) {
      map.put(envToKey(entry.getKey()), entry.getValue());
    }
    return map;
  }

  private static String envToKey(String env) {
    return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, env).replace('-', '.');
  }
}