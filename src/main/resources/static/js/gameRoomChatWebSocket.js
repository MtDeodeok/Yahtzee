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
		console.log('rollingDice call!');
		this._fixedDiceRolling('${param.bang_id}', 'FIXED_DICE_ROLLING',9);	
	},
	ones:function(){
		console.log('ones call!');
		var dices = [
			Number($('#dicebtn1').val()),
			Number($('#dicebtn2').val()),
			Number($('#dicebtn3').val()),
			Number($('#dicebtn4').val()),
			Number($('#dicebtn5').val())
			];
		console.log(dices);
		this._ones('${param.bang_id}', 'ONES', dices);
	},
	twos:function(){
		console.log('twos call!');
		var dices = [
			Number($('#dicebtn1').val()),
			Number($('#dicebtn2').val()),
			Number($('#dicebtn3').val()),
			Number($('#dicebtn4').val()),
			Number($('#dicebtn5').val())
			];
		console.log(dices);
		this._twos('${param.bang_id}', 'TWOS', dices);
	},
	threes:function(){
		console.log('threes call!');
		var dices = [
			Number($('#dicebtn1').val()),
			Number($('#dicebtn2').val()),
			Number($('#dicebtn3').val()),
			Number($('#dicebtn4').val()),
			Number($('#dicebtn5').val())
			];
		console.log(dices);
		this._threes('${param.bang_id}', 'THREES', dices);
	},
	fours:function(){
		console.log('fours call!');
		var dices = [
			Number($('#dicebtn1').val()),
			Number($('#dicebtn2').val()),
			Number($('#dicebtn3').val()),
			Number($('#dicebtn4').val()),
			Number($('#dicebtn5').val())
			];
		console.log(dices);
		this._fours('${param.bang_id}', 'FOURS', dices);
	},
	fives:function(){
		console.log('fives call!');
		var dices = [
			Number($('#dicebtn1').val()),
			Number($('#dicebtn2').val()),
			Number($('#dicebtn3').val()),
			Number($('#dicebtn4').val()),
			Number($('#dicebtn5').val())
			];
		console.log(dices);
		this._fives('${param.bang_id}', 'FIVES', dices);
	},
	sixs:function(){
		console.log('sixs call!');
		var dices = [
			Number($('#dicebtn1').val()),
			Number($('#dicebtn2').val()),
			Number($('#dicebtn3').val()),
			Number($('#dicebtn4').val()),
			Number($('#dicebtn5').val())
			];
		console.log(dices);
		this._sixs('${param.bang_id}', 'SIXS', dices);
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
	_fixedDiceRolling: function(bang_id, cmd, fixedDice) {
		var fixedDiceData =
			{
				dices_lock: fixedDice
			}
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
	},
	_ones: function(bang_id,cmd, dices){
		var diceListData=
		{
			dices: dices
		};
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			diceList: diceListData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_twos: function(bang_id,cmd, dices){
		var diceListData=
		{
			dices: dices
		};
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			diceList: diceListData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_threes: function(bang_id,cmd, dices){
		var diceListData=
		{
			dices: dices
		};
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			diceList: diceListData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_fours: function(bang_id,cmd, dices){
		var diceListData=
		{
			dices: dices
		};
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			diceList: diceListData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_fives: function(bang_id,cmd, dices){
		var diceListData=
		{
			dices: dices
		};
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			diceList: diceListData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	},
	_sixs: function(bang_id,cmd, dices){
		var diceListData=
		{
			dices: dices
		};
		var msgData = {
			bang_id: bang_id,
			cmd: cmd,
			diceList: diceListData
		};
		var jsonData = JSON.stringify(msgData);
		this._socket.send(jsonData);
	}
};

$(window).on('load', function() {
	webSocket.init({ url: "/webSocket" });
});