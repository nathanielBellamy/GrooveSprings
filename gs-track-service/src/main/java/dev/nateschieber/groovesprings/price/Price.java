package dev.nateschieber.groovesprings.price;

import dev.nateschieber.groovesprings.enums.EntityType;
import java.time.LocalDateTime;
import org.antlr.v4.runtime.misc.NotNull;

public class Price {

  private long id;
  private LocalDateTime at;
  private EntityType entityType;
  private Long entityId;
  @NotNull
  private long usdCents;

  public Price() {}

  public Price(
      long id,
      LocalDateTime at,
      EntityType entityType,
      long entityId,
      long usdCents
  ) {
    this.id = id;
    this.at = at;
    this.entityType = entityType;
    this.entityId = entityId;
    this.usdCents = usdCents;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getAt() {
    return at;
  }

  public void setAt(LocalDateTime at) {
    this.at = at;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public long getUsdCents() {
    return usdCents;
  }

  public void setUsdCents(long usdCents) {
    this.usdCents = usdCents;
  }
}
