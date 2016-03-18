var Socket;

$(document).ready(function() {
    $('#submit').tooltip('hide')
    // Socket anlegen
    var url = window.location.pathname;
    var path = url.lastIndexOf("/");
    var result = url.substring(0,path);
    url = 'ws://' + window.location.hostname + (location.port ? ':' + location.port :'') + result +'/bude';
    Socket = new WebSocket(url);
    Socket.onopen = openedAdmin;
    Socket.onclose = closedAdmin;
    Socket.onerror = errorHandlerAdmin;
    Socket.onmessage = receiveMessageAdmin;

    $("#submit").click(function() {
        var request = {
        "Type": "8",
        "Length" : "1",
        "Password" : $('#passwordField').val()
        };
        Socket.send(JSON.stringify(request));
    });
});

function openedAdmin() {

}

function closedAdmin() {
    
}

function errorHandlerAdmin() {
    
}

function receiveMessageAdmin(message) {
    var msgServer = JSON.parse(message.data);
    //console.log(msgServer);
    var msgServerTyp2 = "";
    if(msgServer.Type == 9) {
        if(msgServer.Login) {
            $('.Login').empty();
            var request = {
            "Type": "1",
            "Length" : "1",
            "Content" : "requestUsers"
            };
            Socket.send(JSON.stringify(request));
        } else {
         $('.modal').modal("show")
        }
    } else if(msgServer.Type == 2) {
        $('.Login').append('<select class="selectpicker"></select><br><br>');
        for (var i = 0; i < msgServer.Users.length; i++) {
            $('.selectpicker').append('<option>' + msgServer.Users[i].Username + '</option');
            msgServerTyp2 = msgServer;
        };

        $('.Login').append('<br><button id="bezahltBtn" type="button" class="btn btn-primary" data-dismiss="modal">bezahlen</button>');

        $("#bezahltBtn").click(function() {
            //console.log($(".selectpicker").val());
            var request = {
            "Type": "10",
            "Length" : "1",
            "Username" : $(".selectpicker").val()
            };
            Socket.send(JSON.stringify(request));

            alert($(".selectpicker").val() + " hat bezahlt!");
        });

        $('.Login').append('<br><button id="btnPDF" type="button" class="btn btn-primary" data-dismiss="modal">PDF export</button>');

        $("#btnPDF").click(function() {
            var doc = new jsPDF();
            doc.text(20, 20, 'Strichliste Bude Beira');
            doc.text(150, 20, new Date().getDate() + "." + (new Date().getMonth()+1) + "." + new Date().getFullYear());
            var spacing = 10;
            doc.text(60, 40, 'AntiAlk');
            doc.text(80, 40, 'Bier');
            doc.text(120, 40, 'Schnaps');
            doc.text(150, 40, 'Shots');
            doc.text(180, 40, 'Gesamt');
            var sum2 = 0;
            for (var i = 0; i < msgServer.Users.length; i++) { 
                doc.text(20, (50 + (i *spacing)), msgServer.Users[i].Username.toString());
                doc.text(60, (50 + (i * spacing)), msgServer.Users[i].AntiAlk.toString());
                doc.text(80, (50 + (i * spacing)), msgServer.Users[i].Beer.toString());
                doc.text(120, (50 + (i * spacing)), msgServer.Users[i].Schnaps.toString());
                doc.text(150, (50 + (i * spacing)), msgServer.Users[i].Shot.toString());
                var sum = ((msgServer.Users[i].AntiAlk*1)+(msgServer.Users[i].Beer*1.5)+(msgServer.Users[i].Schnaps*2)+(msgServer.Users[i].Shot*1));
                sum2 = sum2 + sum;
                doc.text(180, (50 + (i * spacing)), sum.toString());
            };
                doc.text(180, (55 + (i * spacing)), sum2.toString());
            doc.save('Strichliste' + new Date().getDate() + "." + (new Date().getMonth()+1) + "." + new Date().getFullYear() + '.pdf');
        });
    }
}