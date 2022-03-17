//삭제
function deleteBoard(boardIdx){
		console.log("클릭됨")
		$.ajax({
			url: "deleteMessageBoard",
			type: "post",
			data: {
				boardIdx : boardIdx
			},
			success: function(response) {
				console.log("정상")
			},
			error: function() {
				console.log("error");
			}
		})
}

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

	// 제출 버튼
	$("#write").click(function() {
		$.ajax({
			url: "writeInquiryBoard",
			type: "post",
			data: {
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
})