$(document).ready(function() {
	
	let editor;
	ClassicEditor
		.create(document.querySelector('#contents'), {
			language: 'ko',
			toolbar: ['bold', 'italic', 'link']
		})
		.then(newEditor => {
			editor = newEditor;
		})
		.catch(error => {
			console.error(error);
		});
	
	// 문의게시판 글쓰기 버튼
	$("#write").click(function() {
		$.ajax({
			url: "inquiryBoardWrite",
			type: "post",
			data: {
				title : $("#title").val(),
				content: editor.getData()
			},
			success: function(response) {
				console.log("정상")
				location.href = "inquiryBoard"
			},
			error: function() {
				console.log("error");
			}
		})
	});
})