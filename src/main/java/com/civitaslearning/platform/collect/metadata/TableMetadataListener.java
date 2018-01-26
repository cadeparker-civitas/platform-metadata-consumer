package com.civitaslearning.platform.collect.metadata;

import com.civitaslearning.collect.events.metadata.ColumnAttributes;
import com.civitaslearning.collect.events.metadata.ColumnMetadataEntity;
import com.civitaslearning.collect.events.metadata.EntityRef;
import com.civitaslearning.collect.events.metadata.Table;
import com.civitaslearning.collect.events.metadata.TableAttributes;
import com.civitaslearning.collect.events.metadata.TableMetadataEntity;
import com.civitaslearning.platform.confluent.kafka.KafkaStreamsContainer;
import com.civitaslearning.platform.confluent.kafka.KafkaUtilities;

import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class TableMetadataListener {

  private static final Logger logger = LoggerFactory.getLogger(TableMetadataListener.class);
  private final String topic;

  public TableMetadataListener(String tableMetadataTopic, String metadataServiceTopic, KafkaStreamsContainer kafkaStreamsContainer, KafkaUtilities kafkaUtilities) {
    topic = tableMetadataTopic;
    StreamsBuilder streamsBuilder = kafkaStreamsContainer.getBuilder();
    streamsBuilder
        .stream(topic, Consumed.with(Serdes.String(), kafkaUtilities.<Table>specificSerde(false)))
        .map((key, value) -> processRecord(key, value))
        .to(metadataServiceTopic, Produced.with(Serdes.String(), kafkaUtilities.<TableMetadataEntity>specificSerde(false)));
  }

  /**
   * Process an ApplianceEvent record on the KStream to call the metadata service to create the datasource entity.
   *
   * @param applianceId          Appliance id.
   * @param table ApplianceEvent record serialized as a GenericRecord.
   */
  public KeyValue<String, TableMetadataEntity> processRecord(String applianceId, Table table) {
    logger.debug("Message consumed -- Key: {}; Type: {}", applianceId, table.get("tableType"));
    return new KeyValue<>(applianceId, toMetadataEntityRecord(table));
  }

  private TableMetadataEntity toMetadataEntityRecord(Table table) {
    TableMetadataEntity.Builder builder = TableMetadataEntity.newBuilder();
    String tableQualifiedName = table.getName();

    return builder.setName(table.getName())
        .setQualifiedName(tableQualifiedName)
        .setAttributes(TableAttributes.newBuilder()
            .setFullName(String.format("%s.%s", table.getSchemaName(), table.getName()))
            .setSchemaName(table.getSchemaName())
            .setTableType(table.getTableType().name())
            .setColumns(table.getColumns().stream()
                .map(col -> ColumnMetadataEntity.newBuilder()
                    .setName(col.getName())
                    .setQualifiedName(col.getName() + "@" + tableQualifiedName)
                    .setAttributes(ColumnAttributes.newBuilder()
                        .setType(col.getType().name())
                        .setSize(col.getSize())
                        .setNullable(col.getNullable())
                        .setTable(EntityRef.newBuilder()
                            .setName(table.getName())
                            .setQualifiedName(tableQualifiedName)
                            .setTypeName("Table")
                            .build())
                        .build())
                    .build())
                .collect(Collectors.toList())
            )
            .build()
        )
        .build();
  }

}
