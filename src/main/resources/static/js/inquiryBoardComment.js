$(document).ready(function() {

	//관리자가 문의글 읽었을 때 확인 중으로 업데이트
	if($("#state").val()=="1"){
		$.ajax({
			url: "inquiryBoardAdminCheck",
			type: "post",
			data: {
				idx : $("#boardIdx").text(),
			},
			success: function() {
				console.log("정상")
			},
			error: function() {
				console.log("error");
			}
		})
	}
	
	let editor;
	ClassicEditor
		.create(document.querySelector('#comment'), {
			language: 'ko',
			toolbar: ['bold', 'italic', 'link']
		})
		.then(newEditor => {
			editor = newEditor;
		})
		.catch(error => {
			console.error(error);
		});
	
	// 문의게시판 답변하기 버튼
	$("#recomment").click(function() {
		console.log("클릭함");
		var addListHtml = "";
		$.ajax({
			url: "inquiryBoardRecomment",
			type: "post",
			dataType:"json",
			data: {
				idx : $("#boardIdx").text(),
				comment: editor.getData()
			},
			success: function(response) {
				console.log("정상");
				console.log(response);
				for (var i = 0; i < response["recomment"].length; i++) {
					addListHtml += "<div class='title'>";
					addListHtml += "<h1>답변</h1>";
					addListHtml += "</div>";
					addListHtml += "<div class='comment'>"+response[recomment][i].comment+"</div>";
				}
				$("#inquiry-body").append(addListHtml);
			},
			error: function() {
				console.log("error");
			}
		});
	});
	
})

