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

})