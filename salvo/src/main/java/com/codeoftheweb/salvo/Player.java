package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {

  // ID automatico para la tabla "players"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  private String username;

  // Relacion con la tabla "gamePlayers"
  @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
  private Set<GamePlayer> gamePlayers;

  public Player() {
  }

  public Player(String username) {
    this.username = username;
  }

  public long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  @JsonIgnore
  public List<Game> getGames() {
    return gamePlayers.stream().map(gp -> gp.getGame()).collect(toList());
  }

  // Salida DTO para los objetos Player
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<String, Object>();
    dto.put("id", this.id);
    dto.put("email", this.username);
    return dto;
  }

}
