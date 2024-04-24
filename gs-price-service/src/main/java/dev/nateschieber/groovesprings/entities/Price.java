package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.enums.EntityType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "prices")
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private LocalDateTime at;

  private EntityType entityType;
  private long entityId;

  @NotNull
  private long usdCents;

  public Price() {};

  public Price(EntityType entityType, LocalDateTime at, long entityId, long usdCents) {
    this.entityType = entityType;
    this.at = at;
    this.entityId = entityId;
    this.usdCents = usdCents;
  }

  public long getId() {
    return id;
  }

  public LocalDateTime getAt() {
    return at;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public long getEntityId() {
    return entityId;
  }

  public long getUsdCents() {
    return usdCents;
  }

}
