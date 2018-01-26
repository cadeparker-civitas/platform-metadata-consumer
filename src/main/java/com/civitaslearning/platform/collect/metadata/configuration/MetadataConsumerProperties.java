package com.civitaslearning.platform.collect.metadata.configuration;

public interface MetadataConsumerProperties {

  String getMdsUrl();

  String getMdsTimeout();

  String getTableMetadataTopic();

  String getMetadataServiceTopic();
}
