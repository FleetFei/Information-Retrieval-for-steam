package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ObjectsandTools.EnterSearchResult;
import ObjectsandTools.KeywordsResult;
import ObjectsandTools.TypingResult;
import ObjectsandTools.relativeName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONException;
import service.SortResults;
import service.findResultDetails;
import service.searchKeywords;
import service.searchQuery;

/**
 * Servlet implementation class GameResult
 */
@WebServlet("/NameGameSearch")
public class NameSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NameSearch() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
//		String inputSearch = request.getHeader("MyHeader");
		String inputSearch = request.getParameter("Name");
		String[] genre = request.getParameterValues("Tag");
		String publisher = request.getParameter("Publisher");
		String releasing = request.getParameter("releasingYear");
		String sort = request.getParameter("sort");
		System.out.println("用户输入inputSearch："+inputSearch);
		
		ArrayList<String> tag = new ArrayList<>();
		
		SortResults sr = new SortResults();
		findResultDetails frd = new findResultDetails();
		//判断用户是否选择tag
		if(genre!=null){
			for(int i=0;i<genre.length;i++){
				System.out.println("用户选择的genre："+genre[i]);
				tag.add(genre[i]);
			}
		}
		else{
			System.out.println("用户选择的genre:"+genre);
		}
		System.out.println("用户输入publisher："+publisher);
		System.out.println("用户输入releasing："+releasing);
		
		//return Search result
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/SearchGameList.txt");
		/*
		 * 返回4种搜索模式
		 * 1.包含首字母
		 * 2.不含首字母，但是包含字符串
		 */
		
		//Typing内容
		System.out.println("1");
		TypingResult typingresult = new searchQuery(path).searchTyping(inputSearch);
		ArrayList<String> queryTyping= typingresult.initialResult;

		
		//Entering内容
		/*
		 * 第二个方法： searchEntering
	目标：
	input：用户的输入(String), tag(ArrayList<String>), Publisher(String) , ReleaseDate(String)(年份表示)
	output: EnterSearchResult类，保存4种结果
			1.allMatchResult:所有输入，选择全部符合
			2.initailResult:不管怎么选择，都符合query
			noninitialResult: 首字母出错，后面都对
			suggestionResult: 有小错误，eg：拼写错误，会修正
		 */
		System.out.println("2");
		EnterSearchResult enterresult = new searchQuery(path).searchEntering(inputSearch,tag, publisher,releasing);
		ArrayList<relativeName> queryEntering1 = enterresult.originAllMatchResult;
		ArrayList<relativeName> queryEntering2 = enterresult.originNmatchResult;
		ArrayList<relativeName> queryEntering3 = enterresult.originSuggestResult;
		
		System.out.println("!!!!!!!!!!!!!"  + enterresult.suggestionResult.toString());

	
		String despath = this.getServletContext().getRealPath("/WEB-INF/classes/descriptionforSearch.trectext");
		String indexpath = this.getServletContext().getRealPath("/WEB-INF/classes/LuceneProcess");
		String pathStopword = this.getServletContext().getRealPath("/WEB-INF/classes/stopword.txt");
		
		JSONArray RecommendArray = new JSONArray();
		try {
			System.out.println("3");
			KeywordsResult  recArray=new searchKeywords(path).search(pathStopword,indexpath,null, tag, publisher, releasing);
			ArrayList<relativeName> result3 = recArray.originResult;
			
			if (!sort.equals("bydefault")) {
				
				ArrayList<relativeName> enterallmatch = enterresult.originAllMatchResult;
				ArrayList<relativeName> enterNmatch = enterresult.originNmatchResult;
				ArrayList<relativeName> enterSuggest = enterresult.originSuggestResult;
				ArrayList<relativeName> recommend = recArray.originResult;

				if (sort.equals("byrating")) {
					enterresult.setoriginAllmatchResult(sr.sortByRating(enterallmatch));
					enterresult.setoriginNmatchResult(sr.sortByRating(enterNmatch));
					enterresult.setoriginSuggestResult(sr.sortByRating(enterSuggest));
					recArray.setOriginResult((sr.sortByRating(recommend)));
				}
				else {
					enterresult.setoriginAllmatchResult(sr.sortByReleasedate(enterallmatch));
					enterresult.setoriginNmatchResult(sr.sortByReleasedate(enterNmatch));
					enterresult.setoriginSuggestResult(sr.sortByReleasedate(enterSuggest));
					recArray.setOriginResult((sr.sortByReleasedate(recommend)));
					
				}
				
				queryEntering1 = enterresult.originAllMatchResult;
				queryEntering2 = enterresult.originNmatchResult;
				queryEntering3 = enterresult.originSuggestResult;
				result3 = recArray.originResult;

			}
			
			
			JSONObject son3 = new JSONObject();
			HashMap<String, String> result3Des = frd.getDescription(despath, result3);
			for(int i=0; i<20; i++){	
				if ( i >= result3.size()) {
					break;
				}
				son3.put("Rid", i+1);
				son3.put("Rname", result3.get(i).name);
				son3.put("Description", result3Des.get(result3.get(i).appid));
				son3.put("Genre", result3.get(i).tags);
				son3.put("rate", result3.get(i).rating);
				son3.put("year", result3.get(i).releasedate);
				System.out.println(result3.size());
				RecommendArray.add(son3);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("queryTping内容："+queryTyping);
		JSONArray Typingarray = new JSONArray();
		JSONObject son = new JSONObject();
		for(int i=0; i<queryTyping.size(); i++){
			son.put("Rid", i);
			son.put("Rname", queryTyping.get(i));
			Typingarray.add(son);
		}
		System.out.println(queryEntering1.size());
		System.out.println(queryEntering2.size());
		System.out.println(queryEntering3.size());
		//1.所有选择全部符合
		JSONArray EnterArray1 = new JSONArray();
		JSONObject son1 = new JSONObject();
		HashMap<String, String> qeresult1 = frd.getDescription(despath, queryEntering1);
		for(int i=0; i<20; i++){			
			if ( i >= queryEntering1.size()) {
				break;
			}
			son1.put("Rid", i+1);
			son1.put("Rname", queryEntering1.get(i).name);
			son1.put("Description", qeresult1.get(queryEntering1.get(i).appid));
			son1.put("Genre", queryEntering1.get(i).tags);
			son1.put("rate", queryEntering1.get(i).rating);
			son1.put("year", queryEntering1.get(i).releasedate);

			EnterArray1.add(son1);
		}
		//2.只符合query
		JSONArray EnterArray2 = new JSONArray();
		JSONObject son2 = new JSONObject();
		HashMap<String, String> qeresult2 = frd.getDescription(despath, queryEntering2);

		for(int i=0; i<20; i++){			
			if ( i >= queryEntering2.size()) {
				break;
			}
			son2.put("Rid", i+1);
			son2.put("Rname", queryEntering2.get(i).name);
			son2.put("Description", qeresult2.get(queryEntering2.get(i).appid));
			son2.put("Genre", queryEntering2.get(i).tags);
			son2.put("rate", queryEntering2.get(i).rating);
			son2.put("year", queryEntering2.get(i).releasedate);

			EnterArray2.add(son2);
		}
		
		//2.只符合query
		JSONArray EnterArray3 = new JSONArray();
		JSONObject son4 = new JSONObject();
		HashMap<String, String> qeresult3 = frd.getDescription(despath, queryEntering3);

		for(int i=0; i<20; i++){			
			if ( i >= queryEntering3.size()) {
				break;
			}
			son4.put("Rid", i+1);
			son4.put("Rname", queryEntering3.get(i).name);
			son4.put("Description", qeresult3.get(queryEntering3.get(i).appid));
			son4.put("Genre", queryEntering3.get(i).tags);
			son4.put("rate", queryEntering3.get(i).rating);
			son4.put("year", queryEntering3.get(i).releasedate);

			EnterArray3.add(son4);
		}

		
		/*
		 * Recommend by tag
		 * input: 路径indexpath(String),keyword(String), tag(ArrayList<String>), Publisher(String) , ReleaseDate(String)(年份表示)
		 */
//		new searchKeywords(path).search()
//		JSONArray RecommendArray = new JSONArray();
//		JSONObject son3 = new JSONObject();
//		for(int i=0; i<queryEntering2.size(); i++){			
//			son3.put("Rid", i+1);
//			son3.put("Rname", queryEntering2.get(i));
//			EnterArray2.add(son3);
//		}
		
		//创建JsonObject
		JSONObject root = new JSONObject();
		root.put("TypingData", Typingarray);
		root.put("EnterData1", EnterArray1);
		root.put("EnterData2", EnterArray2);
		root.put("EnterData3", EnterArray3);
		root.put("RecommendData", RecommendArray);
		//设置response
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		
		System.out.println("TypingData:"+Typingarray);
		System.out.println("EnterData1:"+EnterArray1);
		System.out.println("EnterData2:"+EnterArray2);
		System.out.println("EnterData3:"+EnterArray3);

		System.out.println("RecommendData:"+RecommendArray);
		System.out.println("后端产给前端－－>传回的结果"+root);
		//传输json
		PrintWriter out = response.getWriter();
		out.write(root.toString());
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
//	public static void main(String args[]){
//		JSONArray EnterArray2 = new JSONArray();
//		JSONObject son2 = new JSONObject();
//		for(int i=0; i<10; i++){			
//			son2.put("R_id", i+1);
//			EnterArray2.add(son2);
//		}
//		System.out.println(son2);
//	}
}
