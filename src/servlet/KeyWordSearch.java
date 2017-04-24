package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ObjectsandTools.KeywordsResult;
import ObjectsandTools.relativeName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.*;

/**
 * Servlet implementation class tipSearch
 */
@WebServlet("/KeyWordSearch")
public class KeyWordSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeyWordSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
//		String inputSearch = request.getHeader("MyHeader");
		String keyword = request.getParameter("keyword");
		String[] genre = request.getParameterValues("Tag");
		String publisher = request.getParameter("Publisher");
		String releasing = request.getParameter("releasingYear");
		String sort = request.getParameter("sort");

		System.out.println("用户输入keyword："+keyword);
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
		
		//确认路径
		String despath = this.getServletContext().getRealPath("/WEB-INF/classes/descriptionforSearch.trectext");
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/SearchGameList.txt");
		String indexpath = this.getServletContext().getRealPath("/WEB-INF/classes/LuceneProcess");
		String pathStopword = this.getServletContext().getRealPath("/WEB-INF/classes/stopword.txt");
		
		//Typing内容
		ArrayList<relativeName> keywordResult = new ArrayList<>();
		try {
			KeywordsResult recArray = new searchKeywords(path).search(pathStopword,indexpath,keyword,tag,publisher,releasing);
			keywordResult = recArray.originResult;
			
			if (!sort.equals("bydefault")) {
				
				ArrayList<relativeName> recommend = recArray.originResult;

				if (sort.equals("byrating")) {

					recArray.setOriginResult((sr.sortByRating(recommend)));
				}
				else {

					recArray.setOriginResult((sr.sortByReleasedate(recommend)));
					
				}
				keywordResult = recArray.originResult;

				


			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray Typingarray = new JSONArray();
		JSONObject son = new JSONObject();
		for(int i=0; i<keywordResult.size(); i++){

			son.put("Rid", i+1);
			son.put("Rname", keywordResult.get(i).name);
			son.put("Description", frd.getDescription(despath, keywordResult.get(i)));
			son.put("Genre", keywordResult.get(i).tags);
			son.put("rate", keywordResult.get(i).rating);
			Typingarray.add(son);
		}
		System.out.println("keyword Search内容："+keywordResult.toString());

		
		//创建JsonObject
		JSONObject root = new JSONObject();
		root.put("keywordResult", keywordResult);

		//设置response
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		
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

}
