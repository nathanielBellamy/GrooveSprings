package dev.nateschieber.groovesprings.repositories;

import dev.nateschieber.groovesprings.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> { }
