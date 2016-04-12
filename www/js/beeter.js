$(function(){
   getCurrentUserProfile(function(user){
      $("#aProfile").text(user.fullname + ' ');
      $("#aProfile").append('<span class="caret"></span>');
   });

   var authToken = JSON.parse(sessionStorage["auth-token"]);
   var currentStingsUri = authToken["links"]["current-stings"].uri;
   loadStings(currentStingsUri, function(stings){
      $("#stings-list").empty();
      processStingCollection(stings);
   });
});

function previousStings(){
  loadStings($('#formPrevious').attr('action'), function(stings){
    processStingCollection(stings);
  });
}

function processStingCollection(stings){
  var lastIndex = stings["stings"].length - 1;
  $.each(stings["stings"], function(i,sting){
      sting.links=linksToMap(sting.links);
      var edit = sting.userid ==JSON.parse(sessionStorage["auth-token"]).userid;
      $("#stings-list").append(listItemHTML(sting.links["self"].uri, sting.subject, sting.creator, edit));
      if(i==0)
        $("#buttonUpdate").click(function(){alert("I don't do anything, implement me!")});
      if(i==lastIndex){
        $('#formPrevious').attr('action', sting["links"].previous.uri);
      }
  });

   $("#formPrevious").submit(function(e){
      e.preventDefault();
      e.stopImmediatePropagation();
      previousStings();
      $("#buttonPrevious").blur();
    });

  $("a.list-group-item").click(function(e){
    e.preventDefault();
    e.stopImmediatePropagation();
    var uri = $(this).attr("href");
    getSting(uri, function(sting){
      // In this example we only log the sting
      console.log(sting);
    });
  });
  $(".glyphicon-pencil").click(function(e){
    e.preventDefault();
    alert("This should open a sting editor. But this is only an example.");});
}

$("#aCloseSession").click(function(e){
  e.preventDefault();
  logout(function(){
    window.location.replace('login.html');
  });
});

$("#buttonGrupos").click(function(e) {
	e.preventDefault();
	getGrupos();
});
$("#button_get_grupo").click(function(e) {
	e.preventDefault();
	getGrupobyid($("#grupoid").val());
});
$("#buttoneliminar").click(function(e) {
	e.preventDefault();
	eliminar($("#grupoid").val());
});




function listItemHTML(uri, subject, creator, edit){
  var a = '<a class="list-group-item" href="'+ uri +'">';
  var p = '<p class="list-group-item-text unclickable">' + subject + '</p>';
  var h = (edit) ? '<h6 class="list-group-item-heading unclickable" align="right">'+creator+' <span class="glyphicon glyphicon-pencil clickable"></span></h6>' : '<h6 class="list-group-item-heading unclickable" align="right">'+creator+'</h6>';;
  return a + p +  h + '</a>';
}