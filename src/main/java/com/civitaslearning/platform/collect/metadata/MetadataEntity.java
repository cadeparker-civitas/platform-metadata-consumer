package com.civitaslearning.platform.collect.metadata;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MetadataEntity {

  private UUID id;
  private String name;
  private String qualifiedName;
  private Long version;
  private Map<String, Object> attributes = new HashMap<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getQualifiedName() {
    return qualifiedName;
  }

  public void setQualifiedName(String qualifiedName) {
    this.qualifiedName = qualifiedName;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public void setAttribute(String key, Object value) {
    this.attributes.put(key, value);
  }
}
