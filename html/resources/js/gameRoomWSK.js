var webSocket = {
    init: function(param) {
        console.log(param);
        this._url = param.url;
        this._socket = new SockJS(this._url);
        this._socket.onopen = function(evt) {
            webSocket.sendEnter();
        };
        this._socket.onmessage = function(evt) {
            webSocket.receiveMessage(JSON.parse(evt.data));
        };
        this._socket.onclose = function(evt) {
            webSocket.closeMessage(JSON.parse(evt.data));
        }
        console.log('#webSocket.init');
    },
    _sendMessage: function(bang_id, cmd, msg) {
        if (bang_id & cmd) {
            console.log('###_sendMessage::error - bang_id 혹은 cmd 값 누락');
            return;
        }
        const msgData = {
            bang_id: bang_id,
            cmd: cmd,
            msg: msg ? msg : null,
        };
        let jsonData = JSON.stringify(msgData);
        this._socket.send(jsonData);
    },
    gameStart: function(){
        console.log('##webSocket.gameStart');
        this._sendMessage('${param.bang_id}', 'GAME_START');
    },
    rollingDice: function() {
        console.log('##webSocket.rollingDice');
        const fixedDiceData = {
            dices_lock: 9
        }
        this._sendMessage('${param.bang_id}', 'FIXED_DICE_ROLLING', fixedDiceData);
    },
    selectCell:function(){
        console.log('##webSocket.selectCell');
        const tempCell = "ONES";
        let selectCell = {
            sheet_choice: tempCell
        }
        this._ones('${param.bang_id}', 'SELECT_CELL', selectCell);
    },
    sendChat: function() {
        console.log('##webSocket.sendChat');
        console.log('${param.bang_id}');
        this._sendMessage('${param.bang_id}', 'USER_CHAT_MSG', $('#message').val());
        $('#message').val('');
    },
    sendEnter: function() {
        console.log('##webSocket.sendEnter');
        this._sendMessage('${param.bang_id}', 'GAME_ROOM_ENTER', $('#message').val());
        $('#message').val('');
    },
    receiveMessage: function(msgData) {
        console.log(msgData);
        if (msgData.cmd == 'USER_CHAT_MSG') {
            $('#divChatData').append('<div>' + msgData.msg + '</div>');
        }
        // 입장
        else if (msgData.cmd == 'GAME_ROOM_ENTER') {
            $('#divChatData').append('<div>' + msgData.msg + '</div>');
        }
        // 퇴장
        else if (msgData.cmd == 'GAME_ROOM_EXIT') {
            $('#divChatData').append('<div>' + msgData.msg + '</div>');
        }
        // 주사위 전달받기
        else if (msgData.cmd == 'DICE_ROLLING' && 'GAME_START'){
            console.log(msgData.diceList);
            $('#dicebtn1').val(msgData.diceList[0]);
            $('#dicebtn2').val(msgData.diceList[1]);
            $('#dicebtn3').val(msgData.diceList[2]);
            $('#dicebtn4').val(msgData.diceList[3]);
            $('#dicebtn5').val(msgData.diceList[4]);
        }
        else if(msgData.cmd == 'ONES'){
            console.log(msgData.score);
            $('#sheet_ones').val(msgData.score);
            $('#sheet_ones').prop("type","text");
            $('#sheet_ones').prop("readonly","readonly");
            
        }
        else if(msgData.cmd == 'TWOS'){
            console.log(msgData.score);
            $('#sheet_twos').val(msgData.score);
            $('#sheet_twos').prop("type","text");
            $('#sheet_twos').prop("readonly","readonly");
        }
        else if(msgData.cmd == 'THREES'){
            console.log(msgData.score);
            $('#sheet_threes').val(msgData.score);
            $('#sheet_threes').prop("type","text");
            $('#sheet_threes').prop("readonly","readonly");
        }
        else if(msgData.cmd == 'FOURS'){
            console.log(msgData.score);
            $('#sheet_fours').val(msgData.score);
            $('#sheet_fours').prop("type","text");
            $('#sheet_fours').prop("readonly","readonly");
        }
        else if(msgData.cmd == 'FIVES'){
            console.log(msgData.score);
            $('#sheet_fives').val(msgData.score);
            $('#sheet_fives').prop("type","text");
            $('#sheet_fives').prop("readonly","readonly");
        }
        else if(msgData.cmd == 'SIXS'){
            console.log(msgData.score);
            $('#sheet_sixs').val(msgData.score);
            $('#sheet_sixs').prop("type","text");
            $('#sheet_sixs').prop("readonly","readonly");
        }
    },
    closeMessage: function(str) {
        $('#divChatData').append('<div class="system">연결 끊김 : ' + str + '</div>');
    },
    disconnect: function() {
        this._socket.close();
    },
};

$(window).on('load', function() {
    // const webSocketURL = window.location.pathname;
    // webSocket.init({ url: webSocketURL });

    $('.btnCreateRoom').on('click', toggleDialog);
    $('.btnCreateRoomSubmit').on('click', btnCreateRoomSubmit);
    $('.dialog-area .btn_close').on('click', function (){
        toggleDialog();
        dialogInit();
    })
});

function btnCreateRoomSubmit() {
    const title = document.querySelector('#gameRoomName').value;
    $.ajax({
        url: "/gameRoom",
        type: 'method',
        dataType: 'json',
        data: {
            "gameRoomName": title,
        },
        success: function(response) {
            console.log('## btnCreateRoomSubmit(): ', response);
            toggleDialog();
            dialogInit();
        },
        error: function(e) {
            console.log(e);
            $('.gameRoomName-msg').html(`"${e.statusText}"<br/>방 생성에 실패하였습니다.`);
        },
        complete: function() {
            console.log('## btnCreateRoomSubmit() complete');
        }
    });
}

function toggleDialog() {
    document.querySelector('.layout').classList.toggle('is-dialog-open');
}

function dialogInit() {
    document.querySelector('#gameRoomName').value = '';
}

function getRandom(max, min) {
    return (Math.floor(Math.random() * (max-min)) + min) * 90;
}

function btnPostModify(seq) {
    const body = $('[data-seq=' + seq + ']').find($('.post-body'));
    const content = body.find($('.ckeditor-content'));
    const editor = body.find($('.write'));

    content.hide();
    editor.show();
}

function btnReplyModify(seq) {
    const body = $('[data-seq=' + seq + ']').find($('.reply-body'));
    const content = body.find($('.ckeditor-content'));
    const editor = body.find($('.reply-write'));

    content.hide();
    editor.show();
}

function btnPostModifyCancel(seq) {
    const body = $('[data-seq=' + seq + ']').find($('.post-body'));
    const content = body.find($('.ckeditor-content'));
    const editor = body.find($('.write'));

    editor.hide();
    content.show();
}

function btnReplyModifyCancel(seq) {
    const body = $('[data-seq=' + seq + ']').find($('.reply-body'));
    const content = body.find($('.ckeditor-content'));
    const editor = body.find($('.reply-write'));

    editor.hide();
    content.show();
}

function rollDice() {
    const targets = document.querySelectorAll('.dice');
    const min = 1;
    const max = 6;

    for(let i = 0; i < targets.length; i++) {
        var xRand = getRandom(max, min);
        var yRand = getRandom(max, min);
        console.log(xRand, yRand);
        targets[i].style.webkitTransform = 'rotateX('+xRand+'deg) rotateY('+yRand+'deg)';
        targets[i].style.transform = 'rotateX('+xRand+'deg) rotateY('+yRand+'deg)';
    }

    return ;
}