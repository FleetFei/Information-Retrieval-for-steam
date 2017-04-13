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
		String inputSearch = request.getHeader("MyHeader");
		System.out.println("用户输入："+inputSearch);
//		String inputSearch = request.getParameter
		//return Search result
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/SearchGameList.txt");
		/*
		 * 返回3种搜索模式
		 * 1.包含首字母
		 * 2.不含首字母，但是包含字符串
		 */
		
		ArrayList<String> lianxiang = new searchQuery(path).searchTyping(inputSearch).initialResult;
		System.out.println("联想内容："+lianxiang);
		ArrayList<String> test = new ArrayList<String>();
		ArrayList<String> allrelated = new searchQuery(path).searchEntering(inputSearch,test, "","").initialResult;
		//联想内容
		JSONArray array = new JSONArray();
		JSONObject son = new JSONObject();
		for(int i=0; i<lianxiang.size(); i++){
			son.put("Rid", i);
			son.put("Rname", lianxiang.get(i));
			array.add(son);
		}
		JSONObject root = new JSONObject();
		root.put("data", array);
		//全部相关内容
		JSONArray array2 = new JSONArray();
		JSONObject son2 = new JSONObject();
		for(int i=0; i<allrelated.size(); i++){
			son2.put("Rid", i);
			son2.put("Rname", allrelated.get(i));
			array.add(son2);
		}
		JSONObject root2 = new JSONObject();
		root2.put("allrelatedData", array);
		//创建JsonObject
		
		
		//设置response
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		//传输json
		PrintWriter out = response.getWriter();
		out.write(root.toString());
//		out.write(root2.toString());
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
