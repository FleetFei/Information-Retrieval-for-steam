package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONException;
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
		String releasing = request.getParameter("Year");
		System.out.println("用户输入："+inputSearch);
		ArrayList<String> tag = new ArrayList<>();
		for(int i=0;i<genre.length;i++){
			System.out.println("用户选择的genre："+genre[i]);
			tag.add(genre[i]);
		}
		System.out.println("用户输入："+publisher);
		System.out.println("用户输入："+releasing);
		
		//return Search result
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/SearchGameList.txt");
		/*
		 * 返回4种搜索模式
		 * 1.包含首字母
		 * 2.不含首字母，但是包含字符串
		 */
		
		//Typing内容
		ArrayList<String> queryTping= new searchQuery(path).searchTyping(inputSearch).initialResult;
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
