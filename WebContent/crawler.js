var request = require("request");
var cheerio = require("cheerio");
var URL = require("url-parse");
var http = require("http");
var fs = require("fs") ;
var path = require('path');
var async = require("async");

//get gameIdlist, stored as array
var array=new Array();
function gIdlist(){
	var fReadName = path.resolve(__dirname, "../src/gameIDlist.txt"); 
	fs.readFile(fReadName, function(err, data) {
    	if(err) throw err;
	    var rawarray = data.toString().split("\n");
		var raw_url = "http://store.steampowered.com/app/";
		// for(var i=1; i <array.length;i++){
// 			//crawler
// 			(function(i) {
// 				visitPage(raw_url,array[i]);	
// 			})(i);
// 		}


		// async.eachSeries(array, function (item) {
// 		    console.log(item + " ==> " + item);
// 			visitPage(raw_url,item);
// 		},function(err){
// 			console.log("err: " + err);
// 		});
		var array=[];
		for(var i=25000, j=0; i<rawarray.length; i++,j++){
			array[j]=rawarray[i];
		}
		
		
		async.eachSeries(array, function (id,callback) {
			 var html = "";
			 var url =raw_url+id;
			 console.log(url);
			 //request by get
			 http.get(url, function(res) {
				 res.on("data",function(data){
					 html+=data;
				 });

				 res.on("end",function(){
					 console.log("suceed linking to ----- >> "+url);
					 var $ = cheerio.load(html); //采用cheerio模块解析html
					 var content = $("#game_area_description").text();
					 // write to file
					 var fWriteName = path.resolve(__dirname, "../src/"); 
					//  console.log(fWriteName);
					 fs.writeFile(fWriteName+"Description.txt",content,function (err) {
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

//request on websites
function visitPage(raw_url,id,cb) {
	var html = "";
	var url =raw_url+id;
	console.log(url);
	//request by get
  	http.get(url, function(res) {
  		res.on("data",function(data){
  			html+=data;
  		});
  		
  		res.on("end",function(){
  			console.log("suceed linking to ----- >> "+url);
  			var $ = cheerio.load(html); //采用cheerio模块解析html
  			var content = $("#game_area_description").text();
  			//write to file
  			var fWriteName = path.resolve(__dirname, "../src/"); 
  			// fs.writeFile(fWriteName+id+".txt",content,function (err) {
// 				if (err) throw err ;
// 				console.log(content);
// 				console.log("File Saved !"); //文件被保存
// 			}); 
			    fs.writeFile(fWriteName+"Description.txt",content,function (err) {
				if (err) throw err ;
				console.log(content);
				console.log("File Saved !"); //文件被保存
			}); 
  		});
  		
  		res.on('error', function(err) {  
  			console.log(err);
  		});  
  	});
  
}
//write content to file
function write(fWriteName,content){
	fs.writeFile(fWriteName,content,function (err) {
		if (err) throw err ;
		console.log("File Saved !"); //文件被保存
	}); 
}

/**
//main 
**/
// var a="http://store.steampowered.com/app/";
gIdlist();
// visitPage(a,1000);

	



