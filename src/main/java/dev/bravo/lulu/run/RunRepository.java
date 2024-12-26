package dev.bravo.lulu.run;

// import java.time.LocalDateTime;
// import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import dev.bravo.lulu.Application;
// import jakarta.annotation.PostConstruct;

@Repository
public class RunRepository {
  	private static final Logger log = LoggerFactory.getLogger(Application.class);

  private final JdbcClient jdbcClient;

  public RunRepository(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  public List<Run> findAll() {
    return jdbcClient.sql("select * from run")
                      .query(Run.class)
                      .list();
  }

  public Optional<Run> findById(Integer id) {
    return jdbcClient.sql("select * from run where id = :id")
                      .param("id", id)
                      .query(Run.class)
                      .optional();
  }

  public void create(Run run) {
    var update = jdbcClient.sql("INSERT INTO Run (id,title,started_on,completed_on,miles,location) values(?,?,?,?,?,?)")
                      .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(), run.miles(), run.location().toString()))
                      .update();

        Assert.state(update == 1, "Erreur dans la création" + run.title());
  }

  public void update(Run run, Integer id) {
    var update = jdbcClient.sql("update run set title=?,started_on=?,completed_on=?,miles=?,location=? where id=?")
                      .params(List.of(run.title(),run.startedOn(),run.completedOn(), run.miles(), run.location().toString(), id))
                      .update();

      Assert.state(update == 1, "Erreur dans la mis à jour" + run.title());

  }

  public void delete(Integer id) {
    var update = jdbcClient.sql("delete from run where id = :id")
                            .param("id", id)
                            .update();

    Assert.state(update == 1, "Erreur dans la création" + id);

  }


}
