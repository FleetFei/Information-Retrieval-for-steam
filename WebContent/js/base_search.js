window.onload = function() {
	$("#Tag").click(function(){
		var tag =[];
		$("input[type=checkbox]").each(function() {
			//由于复选框一般选中的是多个,所以可以循环输出 
			alert($(this).val());
			tag.push($(this).val());
		});
		alert("tag-->"+tag);
		$.post("http://localhost:8080/SteamGame/TagSearch", {
				name: tag,
			},
			function(data, status) {
				alert("Data: " + data + "\nStatus: " + status);
				NameSearchShow(data);
			});
	});
	
	$('#action').change(function() {
		if($(this).attr("checked")) {
			$(this).removeAttr("checked");
		} else {
			$(this).attr("checked", "true");
		}
		var content = $('#User_Sch_input').val();
		$.post("http://localhost:8080/SteamGame/NameSearch", {
				name: content,
			},
			function(data, status) {
				alert("Data: " + data + "\nStatus: " + status);
				NameSearchShow(data);
			});
	});

	$('#roleplay').change(function() {
		if($(this).attr("checked")) {
			$(this).removeAttr("checked");
			var content = $('#User_Sch_input').val();
			$.post("http://localhost:8080/SteamGame/TagSearch", {
					name: content,
				},
				function(data, status) {
					alert("Data: " + data + "\nStatus: " + status);
					NameSearchShow(data);
				});
		} else {
			$(this).attr("checked", "true");
		}
	});
}

//name search button
function nameSearchBtn() {
	alert("click button");
	//先删除以前查找的内容
	$("#show_Sch_Rlt").empty();
	var inputSearch = $('#User_Sch_input').val();
	alert("input search -->" + inputSearch);
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
		var li = $("<button class='list-group-item' onclick='review()' id='" + id + "'>" + name + "</button>");
		$("#show_Sch_Rlt").append(li);
	}
}

function test123() {
	alert("click button");
	var inputSearch = $('#test').val();
	alert("input search -->" + inputSearch);
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

//	if(document.getElementById("action").checked) {
//		alert("checkbox is checked");
//	}

//relocation to GameInf by click game
function relocation() {
	var id = $(this).attr('id');
	$.post("http://localhost:8080/SteamGame/NameSearch", {
			id: id,
		},
		function(data, status) {
			alert("Data: " + data + "\nStatus: " + status);
			NameSearchShow(data);
			window.location.href = "http://www.baidu.com";
		});
}

function review() {
	var id = $(this).attr('id');
	var review = "显示review";
	var a = $("<a >" + review + "</a>");
	$("#show_review").append(a);
	//	$.post("http://localhost:8080/SteamGame/NameSearch", {
	//			id: id,
	//		},
	//		function(data, status) {
	//			alert("Data: " + data + "\nStatus: " + status);
	//			NameSearchShow(data);
	//			window.location.href="http://www.baidu.com";
	//		});
}

//});