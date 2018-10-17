function getEventList() {

    var url="/api/getAllEvents";

    $.ajax({
        type: "GET",
        url: url
    }).done( function (response) {
    var eventListPanel = document.getElementById("accordion");
    //response = JSON.parse(response);
        eventListPanel.innerHTML = "";

        response.forEach(function (item,i,arr) {
        var needRoles = '<br><b>Нам нужны</b>' +
            '<ul class="list">';
        item['roles'].forEach(function (role,i,arr) {
            if(role!=null)
            needRoles+='<li class="list-cell text-left">'+role['name'] +'</li>';

        });
        needRoles += '</ul>';
        var registerButton = '<button type="button" class="btn btn-primary" data-toggle="modal" ' +
            'data-target="#successReg" data-id="'+item['id']+'"">Показать участников</button>';
        var panel_item = '<div class="panel panel-default">'
            +'<div class="panel-heading" role="tab" id="heading'+item['id']+'">'
            +'<h4 class="panel-title">'
            + '<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#event' +item['id']+'" aria-expanded="false"'
            + 'aria-controls="event' +item['id']+'">'
            + item['nameEvent']
            + '</a>'
            + '</h4>'
            + '</div>'
            + '<div id="event' +item['id']+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading'+item['id']+'">'
            + "<b>Дата проведения: </b>"+ item['date']+" <br>" +item['description'] + needRoles + registerButton
            + '</div>'
            + '</div>';
        eventListPanel.innerHTML += panel_item;

    });
    window.localStorage.setItem('events', JSON.stringify(response));


    }).fail(function () {
        console.log('fail');
    })
}
function getParticipants(id) {
    var url="/api/getParticipants";
    var result;
    $.ajax({
        type: "GET",
        url: url,
        data: {'eventId':id}
    }).done( function (response) {
        result =  response;

        console.log(response)
        var modal = $('#successReg');
        //var participants = getParticipants(recipient);
        modal.find('.modal-body').html(showParticipants(id,response));
    });
    return result;
}

$('#successReg').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var recipient = button.data('id');

    var modal = $(this);
    var participants = getParticipants(recipient);
    console.log(participants);


    //modal.find('.modal-body').html(showParticipants(recipient,participants));

});
function showParticipants(id,participants){
    var text ="<h2>Список студентов</h2><br>";
    var list = '<ul class="list-group">\n' ;
    participants.forEach(function (item) {
        list+='  <li class="list-group-item">'+ item['firstName'] +' '+item['secondName'] +'' +
            '<button id="deleteUser" onclick="deleteUser(this);" class="btn-danger" type="button" data-stud="'+item['id']+'" data-event="'+id+'">Удалить</button></li>';
    });
    list+= '</ul>';
    text+=list;
    text+='<button id="endEvent" type="button" class="btn-success" data-id="'+id+'" onclick="endEvent(this);">Закончить мероприятие</button>';
    return text;
}

function deleteUser(obj) {
    var button = $(obj);
    var recipient = button.data('event');
    var user = button.data('stud');
    var url="/api/removeParticipantStudent";

    $.ajax({
        type: "GET",
        url: url,
        data: {"studentId":user,
        "eventId":recipient}
    }).done( function (response) {

        var modal = $('#successReg');
        var participants = getParticipants(recipient);
        modal.find('.modal-body').html(showParticipants(recipient,participants));
    });
};

function endEvent(obj) {
    var button = $(obj);
    var url="/api/successEvent";
    $.ajax({
        type: "GET",
        url: url,
        data: {"eventId":button.data('id')}
    }).done( function (response) {
        var url="/api/removeEventById";

        $.ajax({
            type: "GET",
            url: url,
            data: {"eventId":button.data('id')}
        }).done( function (response) {
            $('#successReg').modal('toggle');


            getEventList();
        })
    });
};

function getMyEventList() {

    var url="api/getStudentEventList";
    var user = JSON.parse(window.localStorage.getItem('user'));
    $.ajax({
        type: "GET",
        url: url,
        data: {"studentId":user['id']}
    }).done( function (response) {
        var eventListPanel = document.getElementById("accordion");
        eventListPanel.innerHTML = "";
        //response = JSON.parse(response);
        response.forEach(function (item,i,arr) {

            var needRoles = '<br><b>Моя роль:</b>' +
                '<ul class="list">';
            item['roles'].forEach(function (role,i,arr) {
                needRoles+='<li class="list-cell text-left">'+role['name'] +'</li>';

            });
            needRoles += '</ul>';
            var registerButton = '<button type="button" class="btn btn-primary" data-toggle="modal" ' +
                'data-target="#exampleModal" data-id="'+item['id']+'"">Зарегистрироваться</button>';
            var panel_item = '<div class="panel panel-default">'
                +'<div class="panel-heading" role="tab" id="heading'+item['id']+'">'
                +'<h4 class="panel-title">'
                + '<a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#event' +item['id']+'" aria-expanded="false"'
                + 'aria-controls="event' +item['id']+'">'
                + item['nameEvent']
                + '</a>'
                + '</h4>'
                + '</div>'
                + '<div id="event' +item['id']+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading'+item['id']+'">'
                + "<b>Дата проведения: </b>"+ item['date']+" <br>" +item['description'] + needRoles
                + '</div>'
                + '</div>';
            eventListPanel.innerHTML += panel_item;

        });
        window.localStorage.setItem('events', JSON.stringify(response));


    }).fail(function () {
        console.log('fail');
    })
}

$('#exampleModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var recipient = button.data('id')

    var modal = $(this)
    var events = JSON.parse(window.localStorage.getItem("events"));
    var myEvent;
    events.forEach(function (item) {
        if(item['id']==recipient)
            myEvent = item;
    });
    modal.find('.modal-title').text(myEvent['nameEvent'])
    var text = myEvent['description'] +"<br>";

    myEvent['roles'].forEach(function (item,i) {
        text+='<div class="form-check">\n' +
            '        <input class="form-check-input" type="radio" name="exampleRadios" id="'+myEvent['id']+'"' +
            ' value="'+item['id']+'"';
        if(i==0)
            text+='checked';
        text+='>\n' +
            '    <label class="form-check-label" for="'+myEvent['id']+'">\n' +
            item['name'] +
            '    </label>\n' +
            '    </div>\n' +
            '    '

    });

    modal.find('.modal-body').html(text);

})

$('#regToEvent').click(function () {
    var roleId = $('input[name=exampleRadios]:checked').val();
    var eventId = $('input[name=exampleRadios]:checked').attr('id');
    var userId = JSON.parse(window.localStorage.getItem("user"));
    if(userId!=null)
        userId = userId['id'];
    else
        userId = 1;
    $.ajax({
        type: "POST",
        url: "/api/participantStudent",
        data: {"studentId":userId,
        "roleId": roleId,
        "eventId":eventId}
    }).done(function (response) {
        $('#exampleModal').modal('hide');
        $('#successReg').modal('show');


    });




});



function showUserData(){
    var user = window.localStorage.getItem('user');
    user = JSON.parse(user);
    if(user!=undefined && user!=null){
        document.getElementById("name").innerText = user["firstName"]+" "+user["lastName"];
        document.getElementById("score").innerHTML = "<h2>" + user["score"] + "</h2><br><br>";

    }
}


$(function() {
    $('#auth').submit(function(e) {
        var $form = $(this);
        $.ajax({
            type: $form.attr('method'),
            url: $form.attr('action'),
            data: $form.serialize()
        }).done(function(response) {
            if(response['id']!=undefined && response['id']!=null && response['admin']===true) {
                window.localStorage.setItem('user', JSON.stringify(response));
                location.href = 'index.html';
            }
            else{
                document.getElementById("fail").innerHTML = "Введены некорректные данные";
            }
        }).fail(function() {
            console.log('fail');
        });
        //отмена действия по умолчанию для кнопки submit
        e.preventDefault();
    });
});
var counter=2;

$(function() {
    $('#newEventForm').submit(function(e) {
        var $form = $(this);
        var roles = [{}]
        /*var rolesTMP = "[";
        for(var i=1;i<counter;i++){
            if(i!=1)
                rolesTMP+=',';
            rolesTMP+='{"id":  0,' +
            '"name": "' + document.getElementById('roleName'+i).value+'",' +
            '"count" : '+document.getElementById('score'+i).value+'}';
        }
        rolesTMP +=']';
        console.log(rolesTMP);*/
        var eventName = document.getElementById('eventName').value;
        var description = document.getElementById('description').value;



        $.ajax({
            type: $form.attr('method'),
            url: "/api/addEvent" ,

            data:{"title": eventName ,
                "description":description
            },
        }).done(function(response) {
            getEventList();
            $('#newEvent').modal('toggle');

        }).fail(function() {
            console.log('fail');
        });

        //отмена действия по умолчанию для кнопки submit
        e.preventDefault();
    });
});


function addRow(id) {
    var tbody = document.getElementById(id).getElementsByTagName("TBODY")[0];

    var row = document.createElement("TR")
    var td1 = document.createElement("TD")
    td1.innerHTML = "<input  id=\"roleName"+counter+"\"/>";

    var td2 = document.createElement("TD")
    td2.innerHTML = "<input  id=\"score"+counter+"\"/>";
    row.appendChild(td1);
    row.appendChild(td2);
    tbody.appendChild(row);
    counter++;

}

