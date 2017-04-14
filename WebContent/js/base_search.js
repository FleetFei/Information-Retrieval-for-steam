window.onload = function() {
	$("#genre").click(function(){
		var name =$('#User_Sch_input').val();
		$("input[type=checkbox]:checked").each(function() {
			//由于复选框一般选中的是多个,所以可以循环输出 
			alert($(this).val());
			genre.push($(this).val());
		});   
	});
};

function NameSearch() {
//	var a = $("<a href='#' class='list-group-item' id='" + 10 + "'>"+
//		+"<h4 class='list-group-item-heading'>"+qifei+"</h4>"
//		+"<p class='list-group-item-text'></p>"+
//		+"</a>");
//	var a = $("<a href='#' class='list-group-item'>"+
//	"<h4 class='list-group-item-heading'> qifei</h4></a>");
//	alert(a);
//	$("#resultDisplay").append(a);

	//先删除以前查找的内容
	$("#show_Sch_Rlt").empty();
	/*
	 * 搜索内容：name, tag, publisher, releasingdate, sort
	 */
	var name = $('#User_Sch_input').val();
	var tag =[];
	var publisher =$("input[name='pub']:checked").val();
	var releasingdate=$("#releasingYear").val();
	$("input[type=checkbox]:checked").each(function() {
		//由于复选框一般选中的是多个,所以可以循环输出 
		alert("you choose : "+$(this).val());
		tag.push($(this).val());
	}); 
	 $.ajax({  
         type:'post',  
         dataType:'json',
         traditional :true,  
         url:"http://localhost:8080/SteamGame/NameSearch",  
         data:{'Name':name,'Tag':tag,'Publisher':publisher,'ReleasingDate':releasingdate},  
         success:function(data){  
         	alert("success---data-->"+JSON.stringify(data));
        	SearchDisplay(data);
         }  
     });  
}



function SearchDisplay(response) {
	var myobj = response;
//	alert("myobjJson-->"+myobj);
	alert("test"+JSON.stringify(response));
	
	var typingResult = myobj.TypingData;
	var enterResult = myobj.EnterData;
	for(var i in typingResult) {
		var name = typingResult[i].Rname;
		var id = typingResult[i].Rid;
		var descript =  typingResult[i].Description;
		var genre = typingResult[i].Genre;
		var rate = typingResult[i].rate;
		var a = $("<a href='#' class='list-group-item' id=namesearch" + id + ">"
		+"<h4 class='list-group-item-heading'>"+name+"</h4>"
		+"<p class='list-group-item-text'></p>"+
		+"</a>");
		$("#resultDisplay").append(a);
	}
	
	
//	for(var i in myobj.TypingData) {
//		var name = myobj.data[i].Rname;
//		var id = myobj.data[i].Rid;
//		var descript =  myobj.data[i].Description;
//		var genre = myobj.data[i].Genre;
//		var rate = myobj.data[i].rate;
//		var a = $("<a href='#' class='list-group-item' id='" + id + "'>"+
//		+"<h4 class='list-group-item-heading'>"+name+"</h4>"
//		+"<p class='list-group-item-text'></p>"+
//		+"</a>");
//		$("#resultDisplay").append(a);
//	}
}
