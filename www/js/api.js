var BASE_URI="http://127.0.0.1:8080/grouptalk"

function linksToMap(links){
	var map = {};
	$.each(links, function(i, link){
		$.each(link.rels, function(j, rel){
			map[rel] = link;
		});
	});

	return map;
}

function loadAPI(complete){
	$.get(BASE_URI)
		.done(function(data){
			var api = linksToMap(data.links);
			sessionStorage["api"] = JSON.stringify(api);
			complete();
		})
		.fail(function(data){
		});
}


function login(loginid, password, complete){
	loadAPI(function(){
		var api = JSON.parse(sessionStorage.api);
		var uri = api.login.uri;
		$.post(uri,
			{
				login: loginid,
				password: password
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
				complete();
			}).fail(function(jqXHR, textStatus, errorThrown){
				var error = jqXHR.responseJSON;
				alert(error.reason);
			}
		);
	});
}
function register(user, pass, em, f){
    loadAPI(function(){
		var url = BASE_URI +'/users';
		$.ajax({
                headers: {
                    'Content-Type' : 'application/x-www-form-urlencoded',

                },
                url : url,
                type : 'POST',
                crossDomain : true,
                dataType : 'json',
                data : {login: user,
                       password: pass,
                       email: em,
                       fullname: f
                       }
            //JSON.stringify(user)
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
                window.location.replace('beeter.html');
			}).fail(function(jqXHR, textStatus, errorThrown){
				window.alert("Error");
			}
		);
    });
}


function logout(complete){
	var authToken = JSON.parse(sessionStorage["auth-token"]);
	var uri = authToken["links"]["logout"].uri;
	console.log(authToken.token);
	$.ajax({
    	type: 'DELETE',
   		url: uri,
    	headers: {
        	"X-Auth-Token":authToken.token
    	}
    }).done(function(data) { 
    	sessionStorage.removeItem("api");
    	sessionStorage.removeItem("auth-token");
    	complete();
  	}).fail(function(){});
}

function getCurrentUserProfile(complete){
	var authToken = JSON.parse(sessionStorage["auth-token"]);
	var uri = authToken["links"]["user-profile"].uri;
	$.get(uri)
		.done(function(user){
			user.links = linksToMap(user.links);
			complete(user);
		})
		.fail(function(){});
}

function loadStings(uri, complete){
	// var authToken = JSON.parse(sessionStorage["auth-token"]);
	// var uri = authToken["links"]["current-stings"].uri;
	$.get(uri)
		.done(function(stings){
			stings.links = linksToMap(stings.links);
			complete(stings);
		})
		.fail(function(){});
}

function getSting(uri, complete){
	$.get(uri)
		.done(function(sting){
			complete(sting);
		})
		.fail(function(data){
		});
}
function getGrupos(repository_name) {
	var url = BASE_URI + '/grupo';
	$("#stings-list").text('');
    
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
                var grupo = data;
				$("#stings-list").text('');
                var gru = grupo.grupos
                $.each(gru, function(i,v){
                   $('<br><strong> Name: ' + v['nombre'] + '</strong><br>').appendTo($('#stings-list'));
					$('<strong> ID: </strong> ' + v['id'] + '<br>').appendTo($('#stings-list'));
                });

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> Repository not found </div>').appendTo($("#get_repo_result"));
	});

}
function getGrupobyid(id) {
    var url = BASE_URI + '/grupo/' + id;
	$("#stings-list").text('');
    
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
                var grupo = data;
				$("#stings-list").text('');
                   $('<br><strong> Name: ' + grupo.nombre + '</strong><br>').appendTo($('#stings-list'));
					$('<strong> ID: </strong> ' + grupo.id + '<br>').appendTo($('#stings-list'));

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> Repository not found </div>').appendTo($("#get_repo_result"));
	});

}
function update(id, user){
    loadAPI(function(){
		var url = BASE_URI +'/users/' + id;
		$.ajax({
                headers: {
                    'Content-Type' : 'application/vnd.dsa.grouptalk.user+json',

                },
                url : url,
                type : 'PUT',
                crossDomain : true,
                dataType : 'json',
                data : {login: user,
                       password: pass,
                       email: em,
                       fullname: f
                       }
            //JSON.stringify(user)
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
                window.location.replace('beeter.html');
			}).fail(function(jqXHR, textStatus, errorThrown){
				window.alert("Error");
			}
		);
    });
}
function eliminar(id){
    loadAPI(function(){
		var url = BASE_URI +'/grupo/' + id;
		$.ajax({
                url : url,
                type : 'DELETE',
                crossDomain : true,
                dataType : 'json'
            //JSON.stringify(user)
			}).done(function(authToken){
				authToken.links = linksToMap(authToken.links);
				sessionStorage["auth-token"] = JSON.stringify(authToken);
                window.location.replace('beeter.html');
			}).fail(function(jqXHR, textStatus, errorThrown){
				window.alert("Error");
			}
		);
    });
}
