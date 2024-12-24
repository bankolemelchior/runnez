package dev.bravo.lulu.run;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class RunRepository {
  private List<Run> runs = new ArrayList<>();

  List<Run> findAll() {
    return runs;
  }

  Optional<Run> findById(Integer id) {
    return runs.stream()
                .filter(run -> run.id() == id)
                .findFirst();

  }

  void create(Run run) {
    runs.add(run);
  }

  void update(Run run, Integer id) {
    Optional<Run> existingRun = findById(id);
    if (existingRun.isPresent()) {
      runs.set(runs.indexOf(existingRun.get()), run);
    }
  }

  // void delete(Integer id) {
  //   Optional<Run> existingRun = findById(id);
  //   if (existingRun.isPresent()) {
  //     runs.remove(existingRun);
  //   }

  // }

  @PostConstruct
  private void init() {
    runs.add(new Run(1," Monday Morning run", LocalDateTime.now(), LocalDateTime.now().plus(30, ChronoUnit.MINUTES), 3, Location.INDOOR));

    runs.add(new Run(2," Saturday Morning run", LocalDateTime.now(), LocalDateTime.now().plus(2, ChronoUnit.HOURS), 5, Location.OUTDOOR));
  }
}
