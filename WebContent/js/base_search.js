window.onload = function() {
	var publisherList = ["action","Strategy","Racing","RPG","Education","Sports","Short","Adventure"];
	for(i in publisherList){
		var a = $("<li class='item' >"+
					"<label class='checkbox'>"+
					"<input type='checkbox'  id="+ publisherList[i]+ "value='acton'>"+
					"</label>"+ publisherList[i]+"</li>");
		$("#genreDiv").append(a);
	}
	$("input[type=checkbox]:checked").each(function() {
		//由于复选框一般选中的是多个,所以可以循环输出 
		alert("you choose : "+$(this).val());
		tag.push($(this).val());
	}); 
};


function KeywordSearch() {
	/*
	 * 搜索内容：name, tag, publisher, releasingdate, sort
	 */
	alert("高级搜索启动");
	var name = $('#User_Sch_input').val();
	var tag =[];
	var publisher =$("input[name='pub']:checked").val();
	var releasingdate=$("#releasingYear").val();
	$("input[type=checkbox]:checked").each(function() {
		//由于复选框一般选中的是多个,所以可以循环输出 
		alert("you choose : "+$(this).val());
		tag.push($(this).val());
	}); 
	alert("启动ajax");
	 $.ajax({  
         type:'post',  
         dataType:'json',
         traditional :true,  
         url:"http://localhost:8080/SteamGame/KeyWordSearch",  
         data:{'Name':name,'Tag':tag,'Publisher':publisher,'ReleasingDate':releasingdate},  
         success:function(data){  
//       	alert("success---data-->"+JSON.stringify(data));
			alert("success---back>");
       	 	SearchDisplay(data);
         }  
     });  
}

function NameSearch() {
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
	alert("启动ajax");
	 $.ajax({  
         type:'post',  
         dataType:'json',
         traditional :true,  
         url:"http://localhost:8080/SteamGame/NameSearch",  
         data:{'Name':name,'Tag':tag,'Publisher':publisher,'ReleasingDate':releasingdate},  
         success:function(data){  
//       	alert("success---data-->"+JSON.stringify(data));
			alert("success---back>");
       	 	SearchDisplay(data);
         }  
     });  
}



function SearchDisplay(response) {
	
	//先删除以前查找的内容
	$("#EnterResult1").empty();
	$("#EnterResult2").empty();
	
	var myobj = response;
//	alert("test"+JSON.stringify(response));
	
	var typingResult = myobj.TypingData;
	var enterResult1 = myobj.EnterData1;
	var enterResult2 = myobj.EnterData2;
	//enterResult1
	for(var i in enterResult1) {
		var name = enterResult1[i].Rname;
		var id = enterResult1[i].Rid;
		var descript =  enterResult1[i].Description;
		var genre = enterResult1[i].Genre;
		var rate = enterResult1[i].rate;
		var a = $("<a href='#' class='list-group-item' id=namesearch" + id + ">"
		+"<h4 class='list-group-item-heading'>"+name+"</h4>"
		+"<p class='list-group-item-text'></p>"+
		+"</a>");
		$("#EnterResult1").append(a);
	}
	//enterResult2
	for(var i in enterResult2) {
		var name = enterResult2[i].Rname;
		var id = enterResult2[i].Rid;
		var descript =  enterResult2[i].Description;
		var genre = enterResult2[i].Genre;
		var rate = enterResult2[i].rate;
		var a = $("<a href='#' class='list-group-item' id=namesearch" + id + ">"
		+"<h4 class='list-group-item-heading'>"+name+"</h4>"
		+"<p class='list-group-item-text'></p>"+
		+"</a>");
		$("#EnterResult2").append(a);
	}

}
