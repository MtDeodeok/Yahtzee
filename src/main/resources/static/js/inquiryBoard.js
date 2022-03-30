$(document).ready(function() {
	
	//문의게시판 검색
	$("#inquirySearch").click(function() {
		const searchKeyword =  $("#inquirySearchValue").val();
		$.ajax({
			url: "inquiryBoardSearchKeyword",
			type: "post",
			data: {
				searchKeyword : searchKeyword,
			},
			success: function(response) {
				console.log("success!");
				console.log(response);
				
				const listData = response.inquiry;
				const loginMember = response.loginUser;
				const pagination = response.pagination;
				console.log(listData);
				
				$("#inquiryBoardList > tbody").empty();
				if (listData.length > 0) {
					var addListHtml = "";
					for (var i = 0; i < listData.length; i++) {
						var state = "";
						var className = "";
						
						if( listData[i].state==1){
								state = "대기";
						} else if(listData[i].state==2){
								state = "확인 중";
						} else {
								state = "답변 완료";
						};
						
						if(loginMember.userState==1){
							if( listData[i].state==2){
								className = "check";
							} else if(listData[i].state==3){
								className = "CA";
							}; 
						}
						
						addListHtml += '<tr id="'+listData[i].idx+'" class="'+className+'">';
						addListHtml += '<td>'+state+'</td>';
						addListHtml += '<td>'+'<a href="inquiryBoardView?idx='+listData[i].idx+'"><span>'+listData[i].title+'</span></td>';
						addListHtml += '<td>'+listData[i].userID+"</td>";
						addListHtml += '<td>'+listData[i].writeDate+'</td>';
						addListHtml += '</tr>';
					}
					$('#inquiryBoardList').append(addListHtml);
					
					$(".pagination").empty();
					var addPaginationHtml = "";
	
					// 이전 페이지 체크
					if(pagination.startPage>1) {
						addPaginationHtml += '<a href="inquiryBoardSearch?searchKeyword='+searchKeyword+'&page=' + pagination.prevBlock + '">&lt;</a>';
					} else {
						addPaginationHtml += '<a class="disabled" href="#">&lt;</a>';
					}
					// 페이지 목록
					for (var i = pagination.startPage; i <= pagination.endPage; i++) {
						addPaginationHtml += '<a '; 
						if(i == pagination.page) {
							addPaginationHtml += 'class="on"'; 
						}
						addPaginationHtml += ' href="inquiryBoardSearch?searchKeyword='+searchKeyword+'&page=' + i + '">' + i + '</a>';
					}
					// 다음 페이지 체크
					if(pagination.endPage) {
						addPaginationHtml += '<a href="inquiryBoardSearch?searchKeyword='+searchKeyword+'&page=' + pagination.nextBlock + '">&gt;</a>';
					} else {
						addPaginationHtml += '<a class="disabled" href="#">&gt;</a>';
					}
	
					$('.pagination').append(addPaginationHtml);
				}
			},
			error: function() {
				console.log("error!");
			}
		})
	});
	
})
// 문의게시판 select option 선택
function stateSelect(){
	const searchState = $("#inquiry-filter option:selected").val();
	console.log(searchState);
	$.ajax({
		url: "inquiryBoardSearchState",
		type: "post",
		data: {
			searchState : searchState,
		},
		success: function(response) {
			console.log("success!");
			console.log(response);
			const listData = response.inquiry;
			const loginMember = response.loginUser;
			const pagination = response.pagination;
			console.log(listData);
			
			$("#inquiryBoardList > tbody").empty();
			if (listData.length > 0) {
				var addListHtml = "";
				for (var i = 0; i < listData.length; i++) {
					var searchState = "";
					var className = "";
					
					if( listData[i].state==1){
							state = "대기";
					} else if(listData[i].state==2){
							state = "확인 중";
					} else {
							state = "답변 완료";
					};
					
					if(loginMember.userState==1){
						if( listData[i].state==2){
							className = "check";
						} else if(listData[i].state==3){
							className = "CA";
						}; 
					}
					
					addListHtml += '<tr id="'+listData[i].idx+'" class="'+className+'">';
					addListHtml += '<td>'+state+'</td>';
					addListHtml += '<td>'+'<a href="inquiryBoardView?idx='+listData[i].idx+'"><span>'+listData[i].title+'</span></td>';
					addListHtml += '<td>'+listData[i].userID+"</td>";
					addListHtml += '<td>'+listData[i].writeDate+'</td>';
					addListHtml += '</tr>';
				}
				$('#inquiryBoardList').append(addListHtml);
				
				$(".pagination").empty();
				var addPaginationHtml = "";
	
				// 이전 페이지 체크
				if(pagination.startPage>1) {
					addPaginationHtml += '<a href="inquiryBoardSearch?searchState='+searchState+'&page=' + pagination.prevBlock + '">&lt;</a>';
				} else {
					addPaginationHtml += '<a class="disabled" href="#">&lt;</a>';
				}
				// 페이지 목록
				for (var i = pagination.startPage; i <= pagination.endPage; i++) {
					addPaginationHtml += '<a '; 
					if(i == pagination.page) {
						addPaginationHtml += 'class="on"'; 
					}
					addPaginationHtml += ' href="inquiryBoardSearch?searchState='+searchState+'&page=' + i + '">' + i + '</a>';
				}
				// 다음 페이지 체크
				if(pagination.endPage) {
					addPaginationHtml += '<a href="inquiryBoardSearch?searchState='+searchState+'&page=' + pagination.nextBlock + '">&gt;</a>';
				} else {
					addPaginationHtml += '<a class="disabled" href="#">&gt;</a>';
				}
	
				$('.pagination').append(addPaginationHtml);
			} else {
				location.href = "inquiryBoard"	
			}
		},
		error: function() {
			console.log("error!");
		}
	});
}


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


	