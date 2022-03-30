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
		
		$.ajax({
			url: "inquiryBoardRecomment",
			type: "post",
			data: {
				idx : $("#boardIdx").text(),
				comment: editor.getData()
			},
			success: function(response) {
				console.log("정상");
				console.log(response);
				const recomment = response.recomment;
				if(recomment.length>0){
					var addListHtml = "";
					$("#commentArea").empty();
					for (var i = 0; i < recomment.length; i++) {
						var comment = recomment[i].comment;
						addListHtml += '<div class="title">';
						addListHtml += '<h1>답변</h1></div>';
						addListHtml += '<div class="comment">'+comment+'</div>';
					}
				$("#commentArea").append(addListHtml);
				}
			},
			error: function() {
				console.log("error");
			}
		})
	});
})
