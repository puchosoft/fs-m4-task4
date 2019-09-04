package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api") // Todos los controladores cuelgan de /api
public class SalvoController {

  @Autowired
  private GameRepository gRepository;

  @Autowired
  private GamePlayerRepository gpRepository;

  // Genera un JSON con la informacion de los games en la URL /api/games
  @RequestMapping("/games")
  public List<Object> getGameInfo() {
    return gRepository.findAll().stream().map(g -> makeGameDTO(g)).collect(toList());
  }

  // Formato de salida para los objetos Game
  private Map<String, Object> makeGameDTO(Game game) {
    Map<String, Object> dto = new LinkedHashMap<String, Object>();
    dto.put("id", game.getId());
    dto.put("created", game.getCreationDate());
    dto.put("gamePlayers", game.getGamePlayers().stream().map(gp -> makeGamePlayerDTO(gp)).collect(toList()));
    return dto;
  }

  // Formato de salida para los objetos GamePlayer
  private Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
    Map<String, Object> dto = new LinkedHashMap<String, Object>();
    dto.put("id", gamePlayer.getId());
    dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
    return dto;
  }

  // Formato de salida para los objetos Player
  private Map<String, Object> makePlayerDTO(Player player) {
    Map<String, Object> dto = new LinkedHashMap<String, Object>();
    dto.put("id", player.getId());
    dto.put("email", player.getUsername());
    return dto;
  }

  // Genera un JSON con la informacion de un game especifico en la URL /api/game_view/nn
  @RequestMapping("/game_view/{gamePlayerId}")
  public Map<String, Object> getGameView(@PathVariable long gamePlayerId) {
    GamePlayer gp = gpRepository.getOne(gamePlayerId);
    Map<String, Object> game = makeGameDTO(gp.getGame());
    game.put("ships", gp.getShips().stream().map(s -> makeShipDTO(s)).collect(toList()));
    return game;
  }

  // Formato de salida para los objetos Ship
  private Map<String, Object> makeShipDTO(Ship ship) {
    Map<String, Object> dto = new LinkedHashMap<String, Object>();
    dto.put("type", ship.getShipType());
    dto.put("locations", ship.getLocations());
    return dto;
  }

}
