$(document).ready(function() {
	
	// 문의게시판 게시글 클릭시
	$("#inquiryBoardList tr").on("click", function() {
		var idx = $(this).attr("id");
		$.ajax({
			url: "inquiryBoardView",
			type: "post",
			dataType : "json",
			data: {
				idx : idx
			},
			success: function(response) {
				console.log("정상");
				console.log(response["inquriry"]);
				location.href="inquiryBoardView";
			},
			error: function() {
				console.log("error");
			}
		})
		
	});
	
	//문의게시판 검색
	$("#inquirySearch").click(function() {
		$.ajax({
			url: "inquiryBoardSearch",
			type: "get",
			data: {
				search : $("#inquirySearchValue").val()
			},
			success: function(response) {
				console.log("정상")
			},
			error: function() {
				console.log("error");
			}
		})
	});
	
})

//삭제
function deleteBoard(boardIdx){
		console.log("클릭됨")
		$.ajax({
			url: "inquiryBoardDelete",
			type: "post",
			data: {
				boardIdx : boardIdx
			},
			success: function(response) {
				console.log("정상")
				location.href = "inquiryBoard"
			},
			error: function() {
				console.log("error");
			}
		})
}

