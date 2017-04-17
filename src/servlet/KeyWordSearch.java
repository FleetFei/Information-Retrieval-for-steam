package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ObjectsandTools.relativeName;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.searchQuery;

import service.*;

/**
 * Servlet implementation class tipSearch
 */
@WebServlet("/tipSearch")
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
		String[] Tag = request.getParameterValues("Tag");
		String publisher = request.getParameter("Publisher");
		String releasing = request.getParameter("Year");
		System.out.println("用户输入keyword："+keyword);
		ArrayList<String> tag = new ArrayList<>();
		for(int i=0;i<Tag.length;i++){
			System.out.println("用户选择的genre："+Tag[i]);
			tag.add(Tag[i]);
		}
		System.out.println("用户输入publisher："+publisher);
		System.out.println("用户输入releasing："+releasing);
		
		//确认路径
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/SearchGameList.txt");
		String indexpath = this.getServletContext().getRealPath("/WEB-INF/classes/LuceneProcess");
	
		
		//Typing内容
		ArrayList<String> queryTping= new searchKeywords(path).search(indexpath,keyword,tag,publisher,releasing).Result;
		System.out.println("queryTping内容："+queryTping);
		JSONArray Typingarray = new JSONArray();
		JSONObject son = new JSONObject();
		for(int i=0; i<queryTping.size(); i++){
			son.put("Rid", i);
			son.put("Rname", queryTping.get(i));
			Typingarray.add(son);
		}
		//Entering内容
		ArrayList<String> queryEntering = new searchQuery(path).searchEntering(inputSearch,tag, publisher,releasing).initialResult;
		JSONArray EnterArray = new JSONArray();
		JSONObject son2 = new JSONObject();
		for(int i=0; i<queryEntering.size(); i++){
			son2.put("Rid", i);
			son2.put("Rname", queryEntering.get(i));
			EnterArray.add(son2);
		}
		
		//创建JsonObject
		JSONObject root = new JSONObject();
		root.put("TypingData", Typingarray);
		root.put("EnterData", EnterArray);
		
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