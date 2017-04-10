package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.searchQuery;

/**
 * Servlet implementation class tipSearch
 */
@WebServlet("/tipSearch")
public class TipSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TipSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		String inputSearch = request.getHeader("MyHeader");
		//return Search result
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/lowercaseGameList.txt");
		/*
		 * 返回3种搜索模式
		 * 1.包含首字母
		 * 2.不含首字母，但是包含字符串
		 */
		ArrayList<String> allrelated = new searchQuery(path).search3(inputSearch).initialResult;
		//提示搜索
		JSONArray array = new JSONArray();
		JSONObject son = new JSONObject();
		for(int i=0; i<allrelated.size(); i++){
			son.put("Rid", i);
			son.put("Rname", allrelated.get(i));
			array.add(son);
		}
		JSONObject root = new JSONObject();
		root.put("data", array);
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
