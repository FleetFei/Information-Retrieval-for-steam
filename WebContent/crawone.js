// crawone

var request = require("request");
var cheerio = require("cheerio");
var URL = require("url-parse");
var http = require("http");
var fs = require("fs") ;
var path = require('path');
var async = require("async");

var html = "";
 var url = "http://store.steampowered.com/api/appdetails?appids=251290";
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
		 var fWriteName = path.resolve(__dirname, "../"); 
		//  console.log(fWriteName);
		 fs.writeFile(fWriteName+"/test.txt",content,function (err) {
			 if (err) throw err ;
			 console.log("File Saved !"); //文件被保存
		 }); 
		
	 });

	 res.on('error', function(err) {  
		 console.log(err);
	 });  
 });			