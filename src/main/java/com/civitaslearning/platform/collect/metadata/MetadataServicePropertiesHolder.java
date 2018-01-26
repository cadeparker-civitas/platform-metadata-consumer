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

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("civ.platform-metadata-service")
public class MetadataServicePropertiesHolder implements MetadataServiceProperties {

  private String mdsUrl;
  private int timeout;

  @Override
  public String getMdsUrl() {
    return mdsUrl;
  }

  public void setMdsUrl(String mdsUrl) {
    this.mdsUrl = mdsUrl;
  }

  @Override
  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }

}
