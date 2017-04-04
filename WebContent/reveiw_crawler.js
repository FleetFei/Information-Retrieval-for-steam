//review_crawler.js

var request = require("request");
var cheerio = require("cheerio");
var URL = require("url-parse");
var http = require("http");
var fs = require("fs") ;
var path = require('path');
var async = require("async");

function reqWeb(url,id,callback){
	var html="";
	http.get(url,function(res){
		res.on("data", function(data){
			html+=data;
		});
		res.on("end",function(){
			console.log("suceed linking to ----- >> "+url);
			 var content =html; //采用cheerio模块解析html
// 			 console.log(content);
			 fs.writeFile(fWriteName+"/"+id+".txt",content,function (err) {
				 if (err) throw err ;
				 console.log("File Saved !"); //文件被保存
				 callback();
			 }); 
		
		});
		
		res.on("err",function(err){
			console.log(err);
		});
	});

}

//main 函数
var fReadName = path.resolve(__dirname, "../src/gameIDlist.txt"); 
var fWriteName = path.resolve(__dirname, "../src/Data_review/"); 
fs.readFile(fReadName, function(err, data) {
    	if(err) throw err;
    	//get array of ID
	    var array = data.toString().split("\n");
	    //crawler
	    var rawarray =[];
	    for(var i=12000, j=0; i<array.length; i++,j++){
			rawarray[j]=array[i];
		}
		
	    async.eachSeries(rawarray,function (id,callback){
	    	var url = "http://store.steampowered.com//appreviews/"+id+"?start_offset=0&day_range=30&filter=summary&language=english&review_type=positive&purchase_type=all&review_beta_enabled=0";
	    	reqWeb(url,id,callback);
	    });
});



