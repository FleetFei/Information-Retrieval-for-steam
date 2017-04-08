window.onload = function() {
	alert("web ready");
}
//name search button
//$('#NameSch_btn').click(function() {
//	alert("点击成功");
//	var content = $('#User_Sch_input').val();
//	$.post("http://localhost:8080/SteamGame/NameSearch", {
//			name: content,
//		},
//		function(data, status) {
//			alert("Data: " + data + "\nStatus: " + status);
//			NameSearchShow(data);
//		});
//});


function nameSearchBtn() {
	alert("click button");
	//先删除以前查找的内容
	$("#show_Sch_Rlt").empty();
	var inputSearch = $('#User_Sch_input').val();
	alert("input search -->"+inputSearch);
	var xmlhttp;
	var txt, x, i;
	if(window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			xmlDoc = xmlhttp.responseText;
			txt = "";
			NameSearchShow(xmlDoc);
		}
	}
	xmlhttp.open("GET", "http://localhost:8080/SteamGame/NameSearch", true);
	xmlhttp.setRequestHeader("MyHeader", inputSearch);
	xmlhttp.send();
}

function NameSearchShow(response) {
	var myobj = JSON.parse(response);;
	alert("jsonText===" + myobj);
	var txt = "";
	for(var i in myobj.data) {
		var name = myobj.data[i].Rname;
		var id = myobj.data[i].Rid;
		var li = $("<li class=\"list-group-item\">" + name + "</li>");
		$("#show_Sch_Rlt").append(li);
	}
}


function test() {
	alert("click button");
	//先删除以前查找的内容
	$("#testResult").empty();
	var inputSearch = $('#test').val();
	alert("input search -->"+inputSearch);
	var xmlhttp;
	var txt, x, i;
	if(window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			xmlDoc = xmlhttp.responseText;
			txt = "";
			alert(xmlDoc);
		}
	}
	xmlhttp.open("GET", "http://localhost:8080/SteamGame/TestInter", true);
	xmlhttp.setRequestHeader("MyHeader", inputSearch);
	xmlhttp.send();
}