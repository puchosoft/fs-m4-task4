$(function(){

  columns = ['1','2','3','4','5','6','7','8','9','10'];
  rows = ['A','B','C','D','E','F','G','H','I','J'];
  gridLocation = Array();

  for(i=0;i<10;i++){
    gridLocation.push([0,0,0,0,0,0,0,0,0,0]);
  }

  function showShipGrid(ships){
    // Marca con (1) las ubicaciones de los ships en la grilla de visibilidad
    ships.forEach(s => {
      s.locations.forEach(l => {
        row = l.slice(0,1).charCodeAt(0) - 'A'.charCodeAt(0);
        col = l.slice(1)-1;
        gridLocation[row][col] = 1;
      });
    });

    // Muestra los headers de las columnas de la grilla
    var tr ="<tr><th class='py-1 border border-success'></th>";
    columns.forEach(c => {
      tr+="<th class='py-1 border border-success'>" + c + "</th>";
    });
    tr+="</tr>";
    $('#table-headers').html(tr);

    // Muestra cada fila de la grilla
    tr = "";
    rows.forEach((r, rix) => {
      tr+= "<tr>";
      tr+= "<td class='py-1 border border-success'>" + r + "</td>";
      columns.forEach((c, cix) => {
        var visibilidad = (gridLocation[rix][cix]?'block':'none');
        tr+= '\
          <td class="p-0 w-2 border border-success">\
            <div class="py-2 badge badge-info border border-dark d-'+ visibilidad +'">\
              &nbsp&nbsp\
            </div></td>';
      });
      tr+= "</tr>";
    });
    $('#table-rows').html(tr);
  }

  //Muestra la informacion de los Players
  function showGameInfo(id, gamePlayers){
    you = (gamePlayers[0].id == id)?gamePlayers[0].player.email:gamePlayers[1].player.email;
    other = (gamePlayers[0].id == id)?gamePlayers[1].player.email:gamePlayers[0].player.email;
    h4 = you + '(you) vs ' + other;
    $('#game-info').html(h4);
  }

  function loadData(){
    if(location.search.startsWith("?gp=")){
      id = location.search.slice(4);
      $.getJSON("/api/game_view/"+id)
            .done(function(data) {
              showGameInfo(id,data.gamePlayers);
              showShipGrid(data.ships);
            });
    }
  }

  loadData();

});
