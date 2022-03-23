$(document).ready(function() {
	
	//본문 글쓰기 에디터
	let editorContent;
	ClassicEditor
		.create(document.querySelector('#contents'), {
			language: 'ko',
			toolbar: ['bold', 'italic', 'link']
		})
		.then(newEditor => {
			editorContent = newEditor;
		})
		.catch(error => {
			console.error(error);
		});
	
		
	// 제출 버튼
	$("#write").click(function() {
		$.ajax({
			url: "writeBoard",
			type: "post",
			data: {
				content: editorContent.getData()
			},
			success: function() {
				console.log("success!")
				location.href = "messageBoard"
			},
			error: function() {
				console.log("error!");
			},
			complete: function() {
				console.log("complete!")
			}
		})
	});

	//리스트 출력+더보기
	if ($("#messageBoardList").children().length < 10) {
		$("#moreBtn").hide();
	}

	$("#moreBtn").click(function() {
		var totalPostDom = $("#messageBoardList").children().length;
		var startNum = Number(totalPostDom);
		var addListHtml = "";
		console.log(startNum)

		$.ajax({
			url: "messageBoard",
			type: 'post',
			dataType: 'json',
			data: {
				"startNum": startNum,
			},
			success: function(data) {
				console.log(data);
				if (data["boardList"].length > 0) {
					for (var i = 0; i < data["boardList"].length; i++) {
						addListHtml += "<li>";
						addListHtml += "<div><span>작성자</span><span>" + data["boardList"][i].userID + "</span></div>";
						var dateTime = "";
						var dateCommentTime = "";
						if (data["boardList"][i].writeDate == data["boardList"][i].modifiyDate) {
							dateTime = data["boardList"][i].writeDate
						} else {
							dateTime = data["boardList"][i].modifiyDate
						};
						addListHtml += "<div><span>시간(작성/갱신)</span><span>" + dateTime + "</span></div>";
						addListHtml += "<div><span>본문</span><span>" + data["boardList"][i].content + "</span></div>";
						addListHtml += "<div class='post-btns'>";
						if (data["boardList"][i].userID == data["loginMember"]) {
							addListHtml += "<div class='btn-group left'>";
							addListHtml += "<button class='btn' >수정</button>";
							addListHtml += "<button class='btn'>삭제</button>";
							addListHtml += "<button class='btn'>덧글쓰기</button>";
							addListHtml += "</div>";
						} else {
							addListHtml += "<div class='btn-group right'>";
							addListHtml += "<button class='btn'>덧글쓰기</button>";
							addListHtml += "</div>";
						}
						addListHtml += "</div>";
						addListHtml += "<div class='reply-write'>";
						addListHtml += "<div class='editor'>";
						addListHtml += "<textarea name='' id='' cols='30' rows='10'></textarea>";
						addListHtml += "</div>";
						addListHtml += "<div class='btn-group right'>";
						addListHtml += "<button class='btn small btn_write'>글쓰기</button>";
						addListHtml += "</div>";
						addListHtml += "</div>";

						if (data["boardCommentList"].length > 0) {
							for (var j = 0; j < data["boardCommentList"].length; j++) {
								if (data["boardList"][i].idx == data["boardCommentList"][j].idx) {
									addListHtml += "<ul class='reply-list'>";
									addListHtml += "<li class='reply'>";
									addListHtml += "<div class='reply-info'>";
									addListHtml += "<span>작성자 </span><span>" + data["boardCommentList"][j].userID + "</span>";
									addListHtml += "<div class='time'>";
									addListHtml += "<span>작성</span>";
									if (data["boardCommentList"][j].writeDate == data["boardCommentList"][j].modifiyDate) {
										dateCommentTime = data["boardCommentList"][j].writeDate
									} else {
										dateCommentTime = data["boardCommentList"][j].modifiyDate
									};
									addListHtml += "<span>" + dateCommentTime + "</span>";
									addListHtml += "</div>";
									addListHtml += "</div>";
									addListHtml += "<div class='reply-body'>";
									addListHtml += "<span>내용</span><span>" + data["boardCommentList"][j].comment + "</span>";
									addListHtml += "</div>";
									if (data["boardCommentList"][j].userID == data["loginMember"]) {
										addListHtml += "<div class='reply-btns'>";
										addListHtml += "<div class='btn-group left'>";
										addListHtml += "<button class='btn small'>수정</button>";
										addListHtml += "<button class='btn small'>삭제</button>";
										addListHtml += "</div>";
										addListHtml += "</div>";
									}
									addListHtml += "</li>";
									addListHtml += "</ul>";
									addListHtml += "</li>";
								}
							}
						}
					}
				}
				$("#messageBoardList").append(addListHtml);
				if (Number(data["totalListCnt"]) - startNum == 10 || data["boardList"].length < 10) {
					$("#moreBtn").hide();
				}

			}
		});
	});
	
})

// 수정버튼
function modifiyBoard(idx) {
	console.log("클릭됨");
	
	
}

//삭제
function deleteBoard(boardIdx) {
	console.log("클릭됨");
	$.ajax({
		url: "deleteMessageBoard",
		type: "post",
		data: {
			boardIdx: boardIdx
		},
		success: function() {
			console.log("정상")
		},
		error: function() {
			console.log("error");
		}
	})
}

// 덧글쓰기 버튼
function replyComment(idx) {
	console.log("덧글 쓰기");
	
	if($("#reply"+idx).css("display") == "none"){  
		$("#btnReplyComment"+idx).hide();
		$("#btnReplyCommentCancel"+idx).show(); 
		$("#reply"+idx).show();  
		
		/*if($("commentEditor"+idx) 여기 아래 자식 div class가 ck가 존재하는지 존재안하면 실행 ){
			let editorComment;
			ClassicEditor
			.create(document.querySelector('#comment'+idx), {
				language: 'ko',
				toolbar: ['bold', 'italic', 'link']
			})
			.then(newEditor => {
				editorComment = newEditor;
			})
			.catch(error => {
				console.error(error);
			});
		}*/
	};
		
}
// 덧글쓰기 취소
function replyCommentCancel(idx) {
	$("#btnReplyComment"+idx).show();
	$("#btnReplyCommentCancel"+idx).hide();
	$("#reply"+idx).hide();
}

//덧글 글쓰기 버튼
function commentWrite(idx) {
	console.log("댓글 글쓰기");
	
	$.ajax({
		url: "writeBoardComment",
		type: "post",
		data: {
			comment : editorComment.getData(),
			boardIdx : idx
		},
		success: function() {
			console.log("success!")
			location.href = "messageBoard"
		},
		error: function() {
			console.log("error!");
		},
		complete: function() {
			console.log("complete!")
		}
	});
}