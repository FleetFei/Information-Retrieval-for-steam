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

function Search() {
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
	 * 搜索内容：name, genre, company, sort
	 */
	var name = $('#User_Sch_input').val();
	var genre =[];
	var publisher ="";
	var releasingdate="";
	$("input[type=checkbox]:checked").each(function() {
		//由于复选框一般选中的是多个,所以可以循环输出 
		alert("you choose : "+$(this).val());
		genre.push($(this).val());
	}); 
	 $.ajax({  
         type:'post',  
         dataType:'json',
         traditional :true,  
         url:"http://localhost:8080/SteamGame/NameSearch",  
         data:{'Name':name,'Genre':genre,'Publisher':publisher,'ReleasingDate':releasingdate},  
         success:function(data){  
        	 	SearchDisplay(data);
         }  
     });  
}



function SearchDisplay(response) {
	var myobj = JSON.parse(response);;
//	var myobj = response;
	var txt = "";
	
	
	for(var i in myobj.data) {
		var name = myobj.data[i].Rname;
		var id = myobj.data[i].Rid;
		var descript =  myobj.data[i].Description;
		var genre = myobj.data[i].Genre;
		var rate = myobj.data[i].rate;
		var a = $("<a href='#' class='list-group-item' id='" + id + "'>"+
		+"<h4 class='list-group-item-heading'>"+name+"</h4>"
		+"<p class='list-group-item-text'></p>"+
		+"</a>");
		$("#resultDisplay").append(a);
	}
}
