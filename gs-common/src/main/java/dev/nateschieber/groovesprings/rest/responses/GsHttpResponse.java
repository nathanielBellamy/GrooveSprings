package dev.nateschieber.groovesprings.rest.responses;

import enums.ApiVersion;
import java.time.LocalDateTime;

public abstract class GsHttpResponse {
  public ApiVersion getApiVersion() {
    return apiVersion;
  }

  public LocalDateTime getResponseAt() {
    return responseAt;
  }

  private ApiVersion apiVersion = ApiVersion.V1;
  private LocalDateTime responseAt = LocalDateTime.now();
}
