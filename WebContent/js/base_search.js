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
//	$('#action').change(function() {
//		if($(this).attr("checked")) {
//			$(this).removeAttr("checked");
//		} else {
//			$(this).attr("checked", "true");
//		}
//	});


//name search button
//function nameSearchBtn() {
//	alert("click button");
//	//先删除以前查找的内容
//	$("#show_Sch_Rlt").empty();
//	var inputSearch = $('#User_Sch_input').val();
//	alert("input search -->" + inputSearch);
//	var xmlhttp;
//	var txt, x, i;
//	if(window.XMLHttpRequest) {
//		xmlhttp = new XMLHttpRequest();
//	} else {
//		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
//	}
//	xmlhttp.onreadystatechange = function() {
//		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
//			xmlDoc = xmlhttp.responseText;
//			txt = "";
//			NameSearchShow(xmlDoc);
//		}
//	}
//	xmlhttp.open("GET", "http://localhost:8080/SteamGame/NameSearch", true);
//	xmlhttp.setRequestHeader("MyHeader", inputSearch);
//	xmlhttp.send();
//}

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
	
	var name = $('#User_Sch_input').val();
	//genre
	var genre =[];
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
         data:{'Name':name,'Genre':genre,},  
         success:function(data){  
        	 	SearchDisplay(data);
         }  
     });  
}



function SearchDisplay(response) {
//	var myobj = JSON.parse(response);;
	var myobj = response;
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
		$("#show_Sch_Rlt").append(a);
	}
}

//$(".dropdown-toggle").dropdown('toggle');  


//	if(document.getElementById("action").checked) {
//		alert("checkbox is checked");
//	}

//relocation to GameInf by click game
//function relocation() {
//	var id = $(this).attr('id');
//	$.post("http://localhost:8080/SteamGame/NameSearch", {
//			id: id,
//		},
//		function(data, status) {
//			alert("Data: " + data + "\nStatus: " + status);
//			NameSearchShow(data);
//			window.location.href = "http://www.baidu.com";
//		});
//}

//});