var webSocket = {
	init: function(param) {
		console.log(param);
		this._url = param.url;
		this._initSocket();
	},
	gameStart: function(){
		console.log('GameStart');
		this._gameStart('${param.bang_id}', 'GAME_START');
	},
	rollingDice: function() {
		console.log('testDice call!');
		this._fixedDiceRolling('${param.bang_id}', 'FIXED_DICE_ROLLING',11);	
	},
	sendChat: function() {
		console.log('${param.bang_id}');
		this._sendMessage('${param.bang_id}', 'USER_CHAT_MSG', $('#message').val());
		$('#message').val('');
	},
	sendEnter: function() {
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
	},
	closeMessage: function(str) {
		$('#divChatData').append('<div>' + '연결 끊김 : ' + str + '</div>');
	},
	disconnect: function() {
		this._socket.close();
	},
	_initSocket: function() {
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
	},
	_sendMessage: function(bang_id, cmd, msg) {
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			msg: msg
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_fixedDiceRolling: function(bang_id, cmd, dices_lock) {
		var fixedDiceData = JSON.stringify(
				 dices_lock
			);
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			dices_lock: fixedDiceData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_gameStart: function(bang_id, cmd) {
		var msgData = {
			bang_id: bang_id,
			cmd: cmd
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	}
};

$(window).on('load', function() {
	webSocket.init({ url: "/webSocket" });
});