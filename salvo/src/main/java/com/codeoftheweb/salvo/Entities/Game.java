package com.codeoftheweb.salvo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {

  // ID automatico para la tabla "games"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  // Relacion con la tabla "gamePlayers"
  @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
  private Set<GamePlayer> gamePlayers;

  private Date creationDate;

  public Game() {
    this.creationDate = new Date();
  }

  public Game(long seconds) {
    seconds = (Math.abs(seconds) > 11*3600 ? 0 : seconds);
    this.creationDate = Date.from(new Date().toInstant().plusSeconds(seconds));
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public long getId() {
    return this.id;
  }

  public List<Player> getPlayers() {
    return gamePlayers.stream().map(gp -> gp.getPlayer()).collect(toList());
  }

  public Set<GamePlayer> getGamePlayers(){
    return gamePlayers;
  }

  // Salida DTO para los objetos Game
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("id", this.id);
    dto.put("created", this.creationDate);
    dto.put("gamePlayers", this.gamePlayers.stream().map(gp -> gp.toDTO()));
    return dto;
  }

}
