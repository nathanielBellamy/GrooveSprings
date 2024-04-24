package dev.nateschieber.groovesprings.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dev.nateschieber.groovesprings.enums.EntityType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "prices")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Price {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private EntityType entityType;
  private long entityId;

  @NotNull
  private long usdCents;

  public Price(EntityType entityType, long entityId, long usdCents) {
    this.entityType = entityType;
    this.entityId = entityId;
    this.usdCents = usdCents;
  }
}
