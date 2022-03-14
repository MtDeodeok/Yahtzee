$(document).ready(function() {
	let editor;
	ClassicEditor
	.create( document.querySelector( '#contents' ), {
		language: 'ko'
	})
	.then(newEditor => {
		editor = newEditor;
	})
	.catch( error => {
		console.error( error );
	});
	
	// 제출 버튼
	$("#write").click(function() {
		$.ajax({
			url: "writeBoard",
			type: "post",
			data: {
				title: $("#title").val(),
				content: editor.getData()
			},
			success: function(response) {
				console.log("정상")
			},
			error: function() {
				console.log("error");
			}
		})
	});
	
	if($("#messageBoardList").children().length<10){
		$("#moreBtn").hide();
	}
	
	$("#moreBtn").click(function() {
		var totalPostDom = $("#messageBoardList").children().length;
		var startNum = Number(totalPostDom);
		var addListHtml = "";
		console.log(startNum)
		
		$.ajax({
		url : "messageBoard",
		type : 'post',
		dataType : 'json',
		data : {
			"startNum" : startNum,
		},
		success : function(data){
			console.log(data);
			if(data["boardList"].length > 0){
				for (var i = 0; i < data["boardList"].length; i++) {
					addListHtml += "<li>";
					addListHtml += "<div><span>제목</span><span>"+data["boardList"][i].title+"</span></div>";
					addListHtml += "<div><span>작성자</span><span>"+data["boardList"][i].userID+"</span></div>";
					var dateTime = "";
					if(data["boardList"][i].writeDate==data["boardList"][i].modifiyDate){
						dateTime=data["boardList"][i].writeDate
					} else {
						dateTime=data["boardList"][i].modifiyDate
					};
					addListHtml += "<div><span>시간(작성/갱신)</span><span>"+dateTime+"</span></div>";
					addListHtml += "<div><span>본문</span><span>"+data["boardList"][i].content+"</span></div>";
					addListHtml += "</li>";
				}
			$("#messageBoardList").append(addListHtml);
			if(Number(data["totalListCnt"])-startNum==10||data["boardList"].length<10){
				$("#moreBtn").hide();
			}
			}
		}
	});
	});
})