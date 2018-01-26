package com.civitaslearning.platform.collect.metadata.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("civ.platform-metadata-consumer")
public class MetadataConsumerPropertiesHolder implements MetadataConsumerProperties {

  private String mdsUrl;
  private String mdsTimeout;
  private String tableMetadataTopic;
  private String metadataServiceTopic;

  @Override
  public String getMdsUrl() {
    return mdsUrl;
  }

  public void setMdsUrl(String mdsUrl) {
    this.mdsUrl = mdsUrl;
  }

  @Override
  public String getMdsTimeout() {
    return mdsTimeout;
  }

  public void setMdsTimeout(String mdsTimeout) {
    this.mdsTimeout = mdsTimeout;
  }

  @Override
  public String getTableMetadataTopic() {
    return tableMetadataTopic;
  }

  public void setTableMetadataTopic(String tableMetadataTopic) {
    this.tableMetadataTopic = tableMetadataTopic;
  }

  @Override
  public String getMetadataServiceTopic() {
    return metadataServiceTopic;
  }

  public void setMetadataServiceTopic(String metadataServiceTopic) {
    this.metadataServiceTopic = metadataServiceTopic;
  }
}
