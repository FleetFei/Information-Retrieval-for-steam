//review_crawler.js

var request = require("request");
var cheerio = require("cheerio");
var URL = require("url-parse");
var http = require("http");
var fs = require("fs") ;
var path = require('path');
var async = require("async");

function docraw(url,cb){
	cb(url);
}

function reqWeb(url){
	var html="";
	http.get(url,function(res){
		res.on("data", function(data){
			html+=data;
		});
		res.on("end",function(){
			console.log("suceed linking to ----- >> "+url);
			 var $ = cheerio.load(html); //采用cheerio模块解析html
			 var review_box = $("#Reviews_summary0").html();
			 console.log(review_box);
			 console.log(html);
			//  review_box.each(function(){
// 			 	var i=0;
// 			 	console.log(i);
// 			 	i++;
// 			 	var persona_name=$(this).find(".persona_name").html();
// 			 	var content = $(this).find(".content").html();
// // 			 	console.log(review_box);
// 			 	console.log(content);
// 			 	console.log(persona_name);
// 			 });
// 			 var fWriteName = path.resolve(__dirname, "../src/Userreview/"+id); 
// 			 console.log(fWriteName);
			//  fs.writeFile(fWriteName+"/"+id+".txt",content,function (err) {
// 				 if (err) throw err ;
// 				 console.log("File Saved !"); //文件被保存
// 			 }); 
		
		});
		
		res.on("err",function(err){

		});
	});

}
// var fReadName = path.resolve(__dirname, "../src/gameIDlist.txt"); 
// fs.readFile(fReadName, function(err, data) {
//     	if(err) throw err;
//     	//get array of ID
// 	    var rawarray = data.toString().split("\n");
// 	    //crawler
// 	    async.eachSeries(rawarray,function (id,callback)){
// 	    	var url = "http://store.steampowered.com/app/"+id;
// 	    	docraw(url,function(){
// 	    		
// 	    	});
// 	    }
// });
var url = "http://store.steampowered.com//appreviews/50?start_offset=0&day_range=30&filter=summary&language=english&review_type=all&purchase_type=all&review_beta_enabled=0";
reqWeb(url);


