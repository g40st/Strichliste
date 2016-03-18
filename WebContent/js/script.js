var Socket;

$(document).ready(function() {
    // Socket anlegen
    var url = window.location.pathname;
    var path = url.lastIndexOf("/");
    var result = url.substring(0,path);
    url = 'ws://' + window.location.hostname + (location.port ? ':' + location.port :'') + result +'/bude';
    Socket = new WebSocket(url);
    Socket.onopen = opened;
    Socket.onclose = closed;
    Socket.onerror = errorHandler;
    Socket.onmessage = receiveMessage;

    $("#btnAdd").click(function() {
        var request = {
            "Type": "1",
            "Length" : "1",
            "Content" : "requestUsers"
        };
        Socket.send(JSON.stringify(request));
        $(".container").empty();
        $('.container').append("<h2>Strichliste Bude Beira</h2>");
        $('.container').append('<button type="button" id="btnBack" class="btn btn-primary">Back</button><br><br><br>');

        $("#btnBack").click(function() { 
            location.reload();
        });
    });
});

function opened() {}

function closed() {Socket.close();}

function errorHandler(Event) {console.log("Fehler: " + Event.data);}

function receiveMessage(message) {
    var msgServer = JSON.parse(message.data);
    //console.log(msgServer);
    if(msgServer.Type == 1) {   // Ausgabe der Tabelle
        $('#userList').empty();
        var sumCost = 0;
        for (var i = 0; i < msgServer.Users.length; i++) {
            // Gessamtbetrag ermitteln
            var cost = 0;
            
            cost = (msgServer.Users[i].AntiAlk * msgServer.PriceAntiAlk) + (msgServer.Users[i].Beer * msgServer.PriceBeer) + 
                    (msgServer.Users[i].Schnaps * msgServer.PriceSchnaps) + (msgServer.Users[i].Shot * msgServer.PriceShot);         
            sumCost = sumCost + cost;
            $('#userList').append(  "<tr>" +
                                    "<td>" + msgServer.Users[i].Username + "</td>" + 
                                    "<td>" + msgServer.Users[i].AntiAlk + "</td>"+
                                    "<td>" + msgServer.Users[i].Beer + "</td>"+
                                    "<td>" + msgServer.Users[i].Schnaps + "</td>"+
                                    "<td>" + msgServer.Users[i].Shot + "</td>"+
                                    "<td>" + cost + " €</td></tr>");
                                
        };

            $('#userList').append("<tr><td></td><td></td><td></td><td></td><td></td><td>" + sumCost + " €</td></tr></table>"); 
    } else if(msgServer.Type == 2) {    // Button gedrueckt
        
        $('.container').append('<select class="selectpicker"></select><br><br>');
        for (var i = 0; i < msgServer.Users.length; i++) {
            $('.selectpicker').append('<option>' + msgServer.Users[i].Username + '</option');
        };
        $('.container').append('<div class="data"><button id="AntiAlk" class="btn btn-success" type="button">' +
                                    'AntiAlk <span class="badge">' + msgServer.Users[0].AntiAlk +'</span></button><br>' +
                                    '<button id="Beer" class="btn btn-success" type="button">' +
                                    'Bier <span class="badge">' + msgServer.Users[0].Beer +'</span></button><br>' +
                                    '<button id="Schnaps" class="btn btn-success" type="button">' +
                                    'Schnaps <span class="badge">' + msgServer.Users[0].Schnaps +'</span></button><br>' + 
                                    '<button id="Shot" class="btn btn-success" type="button">' +
                                    'Shot <span class="badge">' + msgServer.Users[0].Shot +'</span></button><br></div>');
        
        $(".btn-success").click(function(event){
            var request = {
                "Type": "3",
                "Length" : "1",
                "Content" : event.target.id,
                "Username" : msgServer.Users[0].Username
            };
            Socket.send(JSON.stringify(request));
        });
            
        $('select.selectpicker').on('change', function(){
            var selected = $('.selectpicker option:selected').val();
            $(".data").remove();
            for(var i = 0; i < msgServer.Length; i++) {
                if(selected == msgServer.Users[i].Username) {
                    $('.container').append('<div class="data"><button id="AntiAlk" class="btn btn-success" type="button">' +
                                    'AntiAlk <span class="badge">' + msgServer.Users[i].AntiAlk +'</span></button><br>' +
                                    '<button id="Beer" class="btn btn-success" type="button">' +
                                    'Bier <span class="badge">' + msgServer.Users[i].Beer +'</span></button><br>' +
                                    '<button id="Schnaps" class="btn btn-success" type="button">' +
                                    'Schnaps <span class="badge">' + msgServer.Users[i].Schnaps +'</span></button><br>' + 
                                    '<button id="Shot" class="btn btn-success" type="button">' +
                                    'Shot <span class="badge">' + msgServer.Users[i].Shot +'</span></button><br></div>');
                }
            };
            $(".btn-success").click(function(event){ 
                var request = {
                    "Type": "3",
                    "Length" : "1",
                    "Content" : event.target.id,
                    "Username" : selected
                };
                Socket.send(JSON.stringify(request));
            });
        });
    } else if(msgServer.Type == 3) {
        $(".data").remove();
        $('.container').append('<div class="data"><button id="AntiAlk" class="btn btn-success" type="button">' +
                                'AntiAlk <span class="badge">' + msgServer.Users[0].AntiAlk +'</span></button><br>' +
                                '<button id="Beer" class="btn btn-success" type="button">' +
                                'Bier <span class="badge">' + msgServer.Users[0].Beer +'</span></button><br>' +
                                '<button id="Schnaps" class="btn btn-success" type="button">' +
                                'Schnaps <span class="badge">' + msgServer.Users[0].Schnaps +'</span></button><br>' + 
                                '<button id="Shot" class="btn btn-success" type="button">' +
                                'Shot <span class="badge">' + msgServer.Users[0].Shot +'</span></button><br></div>');
            $(".btn-success").click(function(event){
                //console.log(event.target.id);   
                var request = {
                    "Type": "3",
                    "Length" : "1",
                    "Content" : event.target.id,
                    "Username" : msgServer.Users[0].Username
                };
                Socket.send(JSON.stringify(request));
            });
    }
}
