window.onload = function() {
	//	alert("web ready");
}
//name search button
$('#User_Sch_input').click(function() {
	var content = $('#User_Sch_input').val();
	$.post("http://localhost:8080/SteamGame/GameResult", {
			name: content,
		},
		function(data, status) {
			alert("Data: " + data + "\nStatus: " + status);
			NameSearchShow(data);
		});
});

function nameSearchBtn() {
	var inputSearch = $('#User_Sch_input').val();
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
	xmlhttp.open("GET", "http://localhost:8080/SteamGame/GameResult", true);
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