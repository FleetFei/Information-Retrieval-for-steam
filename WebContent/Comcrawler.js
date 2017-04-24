//Comdcpt

var request = require("request");
var cheerio = require("cheerio");
var URL = require("url-parse");
var http = require("http");
var fs = require("fs") ;
var path = require('path');
var async = require("async");

//get gameIdlist, stored as array

function gIdlist(){
	var fReadName = path.resolve(__dirname, "../src/gameIDlist.txt"); 
	fs.readFile(fReadName, function(err, data) {
    	if(err) throw err;
	    var rawarray = data.toString().split("\n");
	    //爬虫所有信息
// 		var raw_url = "http://store.steampowered.com/api/appdetails/?appids=";
		//爬虫news
		var raw_url = "http://api.steampowered.com/ISteamNews/GetNewsForApp/v0002/?appid=";
		var array=[];
		for(var i=1, j=1; i<rawarray.length; i++,j++){
			array[j]=rawarray[i];
		}
		
		
		async.eachSeries(array, function (id,callback) {
			 var html = "";
			 var url =raw_url+id+"&count=3&maxlength=300&format=json";
			 console.log(url);
			 //request by get
			 http.get(url, function(res) {
				 res.on("data",function(data){
					 html+=data;
				 });

				 res.on("end",function(){
					 console.log("suceed linking to ----- >> "+url);
					 var content =html;
					 // write to file
					 var fWriteName = path.resolve(__dirname, "../src/go"); 
					//  console.log(fWriteName);
					 fs.writeFile(fWriteName+"/"+id+".txt",content,function (err) {
						 if (err) throw err ;
						 console.log("File Saved !"); //文件被保存
					 }); 
					 callback();
				 });

				 res.on('error', function(err) {  
					 console.log(err);
				 });  
			 });			
		},function(err){
			console.log("err: " + err);
		   
		});
	});	
}

/**
//main 
**/
// var a="http://store.steampowered.com/app/";
gIdlist();
// visitPage(a,1000);

	



