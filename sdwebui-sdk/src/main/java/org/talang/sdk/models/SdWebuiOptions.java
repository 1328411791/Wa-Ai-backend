package org.talang.sdk.models;

import lombok.Getter;

@Getter
public class SdWebuiOptions {

  private final String endpoint;

  public SdWebuiOptions(String endpoint) {
    this.endpoint = endpoint;
  }

}
