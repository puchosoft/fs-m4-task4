package com.codeoftheweb.salvo.Entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class GamePlayer {

  // ID automatico para la tabla "gamePlayers"
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private long id;

  // Relacion con la tabla "games"
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  private Game game;

  // Relacion con la tabla "players"
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "player_id")
  private Player player;

  // Relacion con la tabla "ships"
  @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
  private Set<Ship> ships;

  // Relacion con la tabla "salvoes"
  @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
  private Set<Salvo> salvoes;

  private Date joinDate;

  public GamePlayer() {
  }

  public GamePlayer(Game game, Player player) {
    this.game = game;
    this.player = player;
    joinDate = new Date();
  }

  public long getId() {
    return this.id;
  }

  public Game getGame() {
    return this.game;
  }

  public Player getPlayer() {
    return this.player;
  }

  public Date getJoinDate() {
    return this.joinDate;
  }

  public Set<Ship> getShips(){
    return this.ships;
  }

  public Set<Salvo> getSalvoes(){
    return this.salvoes;
  }

  // Salida DTO para los objetos GamePlayer
  public Map<String, Object> toDTO() {
    Map<String, Object> dto = new LinkedHashMap<>();
    dto.put("id", this.id);
    dto.put("player", this.player.toDTO());
    return dto;
  }

}
