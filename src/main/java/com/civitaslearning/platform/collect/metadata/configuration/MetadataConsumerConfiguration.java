package com.civitaslearning.platform.collect.metadata.configuration;

import com.civitaslearning.platform.collect.metadata.TableMetadataListener;
import com.civitaslearning.platform.confluent.kafka.ConfluentPropertiesHolder;
import com.civitaslearning.platform.confluent.kafka.KafkaStreamsContainer;
import com.civitaslearning.platform.confluent.kafka.KafkaUtilities;
import com.civitaslearning.platform.confluent.kafka.MetadataStreamsPropertiesHolder;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties( {MetadataConsumerPropertiesHolder.class})
public class MetadataConsumerConfiguration {

  @Bean
  public TableMetadataListener tableMetadataListener(KafkaStreamsContainer defaultKafkaStreamsContainer,
                                                     KafkaUtilities kafkaUtilities,
                                                     MetadataConsumerProperties properties) {
    return new TableMetadataListener(properties.getTableMetadataTopic(), properties.getMetadataServiceTopic(), defaultKafkaStreamsContainer, kafkaUtilities);
  }

  @Bean
  public NewTopic tableMetadataTopic(MetadataConsumerProperties properties) {
    return new NewTopic(properties.getTableMetadataTopic(), 1, (short) 2);
  }

  @Bean
  public NewTopic metadataServiceTopic(MetadataConsumerProperties properties) {
    return new NewTopic(properties.getMetadataServiceTopic(), 1, (short) 2);
  }
}
