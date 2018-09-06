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

    $(".nav a").on("click", function(event) {
        console.log();
        if(event.target.text == "Home") {
            location.reload();
        } else if(event.target.text == "Drink") {
            var request = {
                "Type": "1",
                "Length" : "1",
                "Content" : "requestUsers"
            };
            $(".contentList").empty();
            $(".contentList").append('<div class="row contentCheckout"></div>');
            Socket.send(JSON.stringify(request));

        }
        $(".nav").find(".active").removeClass("active");
        $(this).parent().addClass("active");
    });
});

function opened() {}

function closed() {Socket.close();}

function errorHandler(Event) {console.log("Fehler: " + Event.data);}

function receiveMessage(message) {
    var msgServer = JSON.parse(message.data);
    //console.log(msgServer);

    // Ausgabe der Tabelle
    if(msgServer.Type == 1) {
        $('#userList').empty();
        var sumCost = 0;
        for (var i = 0; i < msgServer.Users.length; i++) {
            // Gessamtbetrag ermitteln
            var cost = 0;
            cost = (msgServer.Users[i].AntiAlk * msgServer.PriceAntiAlk) + (msgServer.Users[i].Beer * msgServer.PriceBeer) +
                    (msgServer.Users[i].Schnaps * msgServer.PriceSchnaps) + (msgServer.Users[i].Shot * msgServer.PriceShot);
            sumCost = sumCost + cost;

            // jede zweite Zeile gruener Hintergrund
            var colorClassTable;
            if(i%2 == 0) {
                colorClassTable = "class='success'"
            } else {
                colorClassTable = ""
            }

            $('#userList').append(  "<tr " + colorClassTable + ">" +
                                    "<td>" + msgServer.Users[i].Username + "</td>" +
                                    "<td>" + msgServer.Users[i].AntiAlk + "</td>"+
                                    "<td>" + msgServer.Users[i].Beer + "</td>"+
                                    "<td>" + msgServer.Users[i].Schnaps + "</td>"+
                                    "<td>" + msgServer.Users[i].Shot + "</td>"+
                                    "<td>" + cost + " €</td></tr>");

        };
        $('#userList').append("<tr class='info'><td></td><td></td><td></td><td></td><td></td><td><b>" + sumCost + " €<b></td></tr></table>");
    } else if(msgServer.Type == 2) {    // Reiter "Drink"
        for (var i = 0; i < msgServer.Users.length; i++) {
            $('.contentCheckout').append('<div class="col-xs-6 col-sm-3 col-md-2 col-lg-2 col-xl-1">' +
                                            '<div class="thumbnail">' +
                                                '<div class="caption">' +
                                                    '<h5><b>' + msgServer.Users[i].Username + '</b></h5><hr>' +
                                                    '<button class="btn btn-primary spacingButton AntiAlk ' + msgServer.Users[i].Username + '" type="button" data-toggle="tooltip" title="Anti Alk">' +
                                                    '<i class="fa fa-tint fa-lg"></i> <span class="badge">' + msgServer.Users[i].AntiAlk +'</span></button>' +
                                                    '<button class="btn btn-primary spacingButton Beer ' + msgServer.Users[i].Username + '" type="button" data-toggle="tooltip" title="Bier">' +
                                                    '<i class="fa fa-beer fa-lg"></i> <span class="badge">' + msgServer.Users[i].Beer +'</span></button><br>' +
                                                    '<button class="btn btn-primary spacingButton Schnaps ' + msgServer.Users[i].Username + '" type="button" data-toggle="tooltip" title="Schnaps">' +
                                                    '<i class="fa fa-cocktail fa-lg"></i> <span class="badge">' + msgServer.Users[i].Schnaps +'</span></button>' +
                                                    '<button class="btn btn-primary spacingButton Shot ' + msgServer.Users[i].Username + '" type="button" data-toggle="tooltip" title="Shot">' +
                                                    '<i class="fa fa-bullseye fa-lg"></i> <span class="badge">' + msgServer.Users[i].Shot +'</span></button>' +
                                                '</div>' +
                                            '</div>' +
                                        '</div>');
        };

        $(".Beer").click(function(event) {
                var request = {
                    "Type": "3",
                    "Length" : "1",
                    "Content" : "Beer",
                    "Username" : $(this).attr('class').split(" ")[4]
                };
                Socket.send(JSON.stringify(request));

        });

        $(".AntiAlk").click(function(event) {
                var request = {
                    "Type": "3",
                    "Length" : "1",
                    "Content" : "AntiAlk",
                    "Username" : $(this).attr('class').split(" ")[4]
                };
                Socket.send(JSON.stringify(request));
        });

        $(".Schnaps").click(function(event) {
                var request = {
                    "Type": "3",
                    "Length" : "1",
                    "Content" : "Schnaps",
                    "Username" : $(this).attr('class').split(" ")[4]
                };
                Socket.send(JSON.stringify(request));
        });

        $(".Shot").click(function(event) {
                var request = {
                    "Type": "3",
                    "Length" : "1",
                    "Content" : "Shot",
                    "Username" : $(this).attr('class').split(" ")[4]
                };
                Socket.send(JSON.stringify(request));
        });
    } else if(msgServer.Type == 3) {    // Antwort nach Click auf AntiAlk, Beer, Schnaps oder Shot
        $('.AntiAlk').each(function(index) {
            if($(this).attr('class').split(" ")[4] == msgServer.Users[0].Username) {
                $(this).html('<i class="fa fa-tint fa-lg"></i> <span class="badge">' + msgServer.Users[0].AntiAlk +'</span>');
            }
        });
        $('.Beer').each(function(index) {
            if($(this).attr('class').split(" ")[4] == msgServer.Users[0].Username) {
                $(this).html('<i class="fa fa-beer fa-lg"></i> <span class="badge">' + msgServer.Users[0].Beer +'</span>');
            }
        });

        $('.Schnaps').each(function(index) {
            if($(this).attr('class').split(" ")[4] == msgServer.Users[0].Username) {
                $(this).html('<i class="fa fa-cocktail fa-lg"></i> <span class="badge">' + msgServer.Users[0].Schnaps +'</span>');
            }
        });

        $('.Shot').each(function(index) {
            if($(this).attr('class').split(" ")[4] == msgServer.Users[0].Username) {
                $(this).html('<i class="fa fa-bullseye fa-lg"></i> <span class="badge">' + msgServer.Users[0].Shot +'</span>');
            }
        });
    }
}
