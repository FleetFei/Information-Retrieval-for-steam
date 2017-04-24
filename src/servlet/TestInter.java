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
 * Servlet implementation class TestInter
 */
@WebServlet("/TestInter")
public class TestInter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestInter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("GBK");
		//前端你输入的测试搜索
		String inputSearch = request.getParameter("Name");
		//你需要改路径
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/SearchGameList.txt");
		ArrayList<String> test = new ArrayList<String>();
		test.add("action");
		test.add("free to play");
		ArrayList<String> lianxiang = new searchQuery(path).searchEntering(inputSearch,test,"valve","Oct 10 2007").initialResult;
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
