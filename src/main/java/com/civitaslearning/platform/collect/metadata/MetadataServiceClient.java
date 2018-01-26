/*
 * Copyright (c) 2017, Civitas Learning Incorporated.
 * All Rights Reserved.
 *
 * This software is protected by U.S. Copyright Law and International Treaties.
 * Unauthorized use, duplication, reverse engineering, any form of redistribution,
 * or use in part or in whole other than by prior, express, printed and signed license
 * for use is subject to civil and criminal prosecution.
 */

package com.civitaslearning.platform.collect.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Base64;
import java.util.UUID;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MetadataServiceClient {

  private static Logger logger = LoggerFactory.getLogger(MetadataServiceClient.class);

  Client client;
  private MetadataServiceProperties mdsProperties;


  public MetadataServiceClient(Client client, MetadataServiceProperties mdsProperties) {
    this.client = client;
    this.mdsProperties = mdsProperties;
  }

  public MetadataEntity getEntity(UUID id) {
    return client.target(mdsProperties.getMdsUrl()).path("/metadata/entity/" + id.toString())
        .request(MediaType.APPLICATION_JSON)
        .header("Authorization", Base64.getEncoder().encode("admin:admin".getBytes()))
        .get(MetadataEntity.class);
  }

  /**
   * Calls the Meta Data Service to create a new entity.
   *
   * @param jsonEntity String in JSON format with entity fields. id, typeName, name, qualifiedName, updated, created,
   *                   and version are set on the MetadataEntity, and then all of the other fields are stored into the
   *                   attributes map.
   */
  public void createMetadataEntity(String jsonEntity) {
    WebTarget target = getWebTargetForUrl("/entity");
    logger.debug("Calling: {} with entity: {}", target.getUri(), jsonEntity);
    Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(jsonEntity));
    processResponse(response);
  }

  private WebTarget getWebTargetForUrl(String url) {
    return client.target(mdsProperties.getMdsUrl() + url);
  }

  void processResponse(Response response) {
    if (response.getStatus() == HttpStatus.OK.value()) {
      //TODO define what to do when an entity is successfully created
      logger.debug("Entity provisioned successfully");
    } else {
      //TODO define what to do when an error prevents an entity from being created
      logger.error("Request error: {} {}", response.getStatus(), response.getStatusInfo());
    }
  }

}
