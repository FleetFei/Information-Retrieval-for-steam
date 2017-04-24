window.onload = function() {
	var tag1List = ["action","Strategy","Racing","RPG","Education","Sports","Short","Adventure"];
	for(i in tag1List){
		var a = $("<li class='item' >"+
					"<label class='checkbox'>"+
					"<input type='checkbox' id="+ tag1List[i]+ " value="+ tag1List[i]+">"+
					"</label> "+ tag1List[i]+"</li>");
		$("#genreDiv").append(a);
	}
	var tag2List = ["Early Access","Free to play","Violent","Sexual Content","Casual"];
	for(i in tag2List){
		var a = $("<li class='item' >"+
					"<label class='checkbox'>"+
					"<input type='checkbox' id="+ tag2List[i]+ "value="+ tag2List[i]+">"+
					"</label>"+ tag2List[i]+"</li>");
		$("#genreDiv2").append(a);
	}
	var publisherList=["dovetail","devolver","degica","lionsgate","telltale","ubisoft","sega","paradox","valve","others"];
	for(i in publisherList){
		var a = $("<li class='list-group'>"+
							"<label class='radio'>"+
							"<input type='radio' name='pub' id="+publisherList[i]+ " value="+publisherList[i]+ ">"+ publisherList[i]+
							"</label>"+
				  "</li>");
		$("#publisherDiv").append(a);
	}
};


function KeywordSearch() {
	/*
	 * 搜索内容：name, tag, publisher, releasingdate, sort
	 */
	var keyword = $('#keyword').val();
	var tag =[];
	var publisher =$("input[name='pub']:checked").val();
	var releasingYear=$("#releasingYear").val();
	var sort = $("#bysort").val();
	$("input[type=checkbox]:checked").each(function() {
		//由于复选框一般选中的是多个,所以可以循环输出 
		alert("you choose : "+$(this).val());
		tag.push($(this).val());
	}); 
	 $.ajax({  
         type:'post',  
         dataType:'json',
         traditional :true,  
         url:"http://localhost:8080/SteamGame/KeyWordSearch",  
         data:{'keyword':keyword,'Tag':tag,'Publisher':publisher,'releasingYear':releasingYear,"sort":sort},  
         success:function(data){  
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
	var releasingYear=$("#releasingYear").val();
	var sort = $("#bysort").val();
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
         data:{'Name':name,'Tag':tag,'Publisher':publisher,'releasingYear':releasingYear,'sort':sort},  
         success:function(data){  
       	 	SearchDisplay(data);
         }  
     });  
}



function SearchDisplay(response) {
	
	//先删除以前查找的内容
	$("#EnterResult1").empty();
	$("#EnterResult2").empty();
	$("#RecommendGame").empty();
	var myobj = response;
//	alert("test"+JSON.stringify(response));
	
	var typingResult = myobj.TypingData;
	var enterResult1 = myobj.EnterData1;
	var enterResult2 = myobj.EnterData2;
	var RecommendGame = myobj.RecommendData;
	//enterResult1
	for(var i in enterResult1) {
		var name = enterResult1[i].Rname;
		var id = enterResult1[i].Rid;
		var descript =  enterResult1[i].Description;
		var genre = enterResult1[i].Genre;
		var rate = enterResult1[i].rate;
		
		var b="123";
		for(i in genre){
			b = b + "<li class='list-group-item'>"+genre[i]+"</li>"; 
		}
		var a = $("<a href='#' class='list-group-item' id=namesearch" + id + ">"
		+"<h4 class='list-group-item-heading text-primary'>"+name+"</h4>"
		+"<p class='list-group-item-text text-success'>"+ descript +"</p>"
		+"<ul class='list-group'>"+b+"</ul>"
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
		
		var b="123";
		for(i in genre){
			b = b + "<li class='list-group-item'>"+genre[i]+"</li>"; 
		}
		var a = $("<a href='#' class='list-group-item' id=namesearch" + id + ">"
		+"<h4 class='list-group-item-heading text-primary'>"+name+"</h4>"
		+"<p class='list-group-item-text  text-success'>"+ descript +"</p>"
		+"<ul class='list-group'>"+b+"</ul>"
		+"</a>");
		$("#EnterResult2").append(a);
	}
	//Recommend Game
	for(var i in RecommendGame) {
		var name = RecommendGame[i].Rname;
		var id = RecommendGame[i].Rid;
		var descript =  enterResult2[i].Description;
		var genre = enterResult2[i].Genre;
		var rate = enterResult2[i].rate;
		
		var b="123";
		for(i in genre){
			b = b + "<li class='list-group-item'>"+genre[i]+"</li>"; 
		}
		var a = $("<a href='#' class='list-group-item' id=namesearch" + id + ">"
		+"<h4 class='list-group-item-heading text-primary'>"+name+"</h4>"
		+"<p class='list-group-item-text  text-success'>"+ descript +"</p>"
		+"<ul class='list-group'>"+b+"</ul>"
		+"</a>");
		$("#RecommendGame").append(a);
	}
}
