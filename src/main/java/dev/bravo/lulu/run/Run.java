package dev.bravo.lulu.run;

import java.time.LocalDateTime;

public record Run(
  Integer id,
  String title,
  LocalDateTime starteOn,
  LocalDateTime completedOn,
  Integer miles,
  Location location
) {

}
