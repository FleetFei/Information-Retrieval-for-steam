window.onload = function() {
	//	alert("web ready");
}

function gameSearchBtn() {
	var inputSearch = $('#User_Sch_input').val();
	//		alert("inputSearch is"+inputSearch);
	/*
	 * Ajax
	 */
	var xmlhttp;
	var txt, x, i;
	if(window.XMLHttpRequest) {
		// IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
		xmlhttp = new XMLHttpRequest();
	} else {
		// IE6, IE5 浏览器执行代码
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			xmlDoc = xmlhttp.responseText;
			txt = "";
			DisplaySch(xmlDoc);
		}
		//		else {
		//			alert("not valid path");
		//		}
	}
	xmlhttp.open("GET", "http://localhost:8080/SteamGame/GameResult", true);
	xmlhttp.setRequestHeader("MyHeader", inputSearch);
	xmlhttp.send();
}

function DisplaySch(response) {
	//	$("#show_Sch_Rlt").text(data);
//	var jsonText = JSON.stringify(jsonmsg);
	var myobj = JSON.parse(response);;
	alert("jsonText==="+myobj);
	var txt = "";
	for(var i in myobj.data) {
		var name = myobj.data[i].Rname;
		var id = myobj.data[i].Rid;
		var li = $("<li class=\"list-group-item\">" + name + "</li>");
		$("#show_Sch_Rlt").append(li);
	}
	
//	$("#show_Sch_Rlt").innerHTML = data;
}