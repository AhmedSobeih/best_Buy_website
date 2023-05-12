const url = 'http://localhost:8080';
let stompClient;
let selectedUser;
let selectedChatId;
let newMessages = new Map();
let userNameSaved = localStorage.getItem('userName');

// let userName = document.getElementById("userName").value;

function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/user/" + userName, function (response) {
            console.log("subscribing to chat with session ID" + response.body);
            //            stompClient.subscribe("/cha")
            //subscribe to the chat session
            subscribeToChatSession(response.body);
        });
    });
}


function subscribeToChatSession(chatID) {
    if (!stompClient) {
        registration();
    }
    // chatID = 0;
    stompClient.subscribe("/topic/ws/chat/" + chatID, function (response) {
        console.log("subscribing to chat with session ID" + response);

        let data = JSON.parse(response.body);
        if (selectedUser === data.fromUser) {
            render(data.message, data.fromUser);
        } else {
            newMessages.set(data.fromUser, data.message);
            $('#userNameAppender_' + data.fromUser).append('<span id="newMessage_' + data.fromLogin + '" style="color: #ffffff">+1</span>');
        }

    });
}

function sendMsg(from, text) {
    if (!stompClient) {
        let socket = new SockJS(url + '/chat');
        stompClient = Stomp.over(socket);
    }
    stompClient.send("/app/ws/chat/" + selectedChatId, {}, JSON.stringify({
        senderId: from,
        content: text,
        chatId: selectedChatId,
    }));
}

function registration() {
    let userName = document.getElementById("userName").value;

    $.ajax({
        url: url + "/registration/" + userName,
        type: "GET",
        xhrFields: {
            withCredentials: false // set to false to avoid CORS error
        },
        success: function (response) {
            connectToChat(userName);
            userNameSaved = userName;
            localStorage.setItem('userName', userName);
            console.log(response);
            const username = userName;
            document.cookie = userName;
        },
        error: function (error) {
            if (error.status === 400) {
                alert("Login is already busy!");
                console.log(error);
            }
        }
    });

}


function intChat() {
    let userName = document.getElementById("userName").value;
    console.log("Sending int chat");
    $.ajax({
        url: url + "/api/chat/initiate",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ userName: userName }),
        success: function (response) {
            console.log("The chat initiated " + response);
            subscribeToChatSession(response);
        },
        error: function (error) {
           console.log(error);
        }
    });

}

function selectUser(userName, chatId) {
    console.log("selecting users: " + userName + " chat :" + chatId);
    selectedUser = userName;
    selectedChatId = chatId;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
        render(newMessages.get(userName), userName);
    }
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}

function fetchAll() {
    // let userName = document.getElementById("userName").value;
    // let userName = document.cookie;

    console.log(userName);
    $.get(url + "/api/chat/user/" + userNameSaved, function (response) {
        let sessions = response;
        console.log(sessions);
        let sessionsTemplateHTML = "";
        for (let i = 0; i < sessions.length; i++) {
            let chatSession = sessions[i];
            let user1 = chatSession.customerId;
            let user2 = chatSession.representativeId;
            let userToRender = user1 === userNameSaved ? user2 : user1;
            let chatId = chatSession.id;
            sessionsTemplateHTML = sessionsTemplateHTML + '<a href="#" onclick="selectUser(\'' + userToRender + '\', \'' + chatId + '\')"><li class="clearfix">\n' +
                '                <div class="about">\n' +
                '                    <div id="userNameAppender_' + userToRender + '" class="name">' + userToRender + '</div>\n' +
                '                    <div class="status">\n' +
                '                        <i class="fa fa-circle offline"></i>\n' +
                '                    </div>\n' +
                '                </div>\n' +
                '            </li></a>';
        }
        $('#usersList').html(sessionsTemplateHTML);
    });
}
