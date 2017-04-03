//review_crawler.js

var request = require("request");
var cheerio = require("cheerio");
var URL = require("url-parse");
var http = require("http");
var fs = require("fs") ;
var path = require('path');
var async = require("async");


var fReadName = path.resolve(__dirname, "../src/gameIDlist.txt"); 
fs.readFile(fReadName, function(err, data) {
    	if(err) throw err;
    	//get array of ID
	    var rawarray = data.toString().split("\n");
	    //crawler
	    async.series(rawarray, callback){
	    	
	    
	    }
});


function docraw(url,cb){
	cb();
}