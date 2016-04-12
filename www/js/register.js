$( "#form-register" ).submit(function( event ) {
  event.preventDefault();
        var nuevousuario = new Object();
        register($("#inputLoginid").val(),
        $("#inputPassword").val(),
        $("#inputemail").val(),
        $("#inputname").val());
});