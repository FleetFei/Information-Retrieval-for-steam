window.onload = function() {
	 $(".dropdown-toggle").dropdown('toggle');
//	$("#genre").click(function(){
//		alert("genre button");
//		var genre =[];
//		var name =$('#User_Sch_input').val();
//		$("input[type=checkbox]").each(function() {
//			//由于复选框一般选中的是多个,所以可以循环输出 
//			alert($(this).val());
//			genre.push($(this).val());
//		});
//		$.post("http://localhost:8080/SteamGame/NameSearch", {
//				Genre: genre,
//				Name: name,
//			},
//			function(data, status) {
//				alert("Data: " + data + "\nStatus: " + status);
//				NameSearchShow(data);
//			});
//	});
//	
//	$('#action').change(function() {
//		if($(this).attr("checked")) {
//			$(this).removeAttr("checked");
//		} else {
//			$(this).attr("checked", "true");
//		}
//	});
//
//	$('#roleplay').change(function() {
//		if($(this).attr("checked")) {
//			$(this).removeAttr("checked");
//		} else {
//			$(this).attr("checked", "true");
//		}
//	});
};

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
	alert("click button");
	//先删除以前查找的内容
	$("#show_Sch_Rlt").empty();
	var name = $('#User_Sch_input').val();
	alert("input search -->" + name);
	var xmlhttp;
	var txt, x, i;
	
	$.post("http://localhost:8080/SteamGame/NameSearch", {
				Genre: genre,
				Name: name,
			},
			function(data, status) {
				alert("Data: " + data + "\nStatus: " + status);
				NameSearchShow(data);
			});
}



function NameSearchShow(response) {
//	var myobj = JSON.parse(response);;
	var myobj = response;
	alert("jsonText===" + myobj);
	var txt = "";
	for(var i in myobj.data) {
		var name = myobj.data[i].Rname;
		var id = myobj.data[i].Rid;
		var descript =  myobj.data[i].Description;
		var genre = myobj.data[i].Genre;
		var rate = myobj.data[i].rate;
		var a = $("<a href='#' class='list-group-item' id='" + id + "'></a>");
		var h4= $("<h4 class='list-group-item-heading'>"+name+"</h4>");
		var p= $("<p class='list-group-item-text'></p>");
		$("#show_Sch_Rlt").append(a).append(h4).append(p);
	}
}

   


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