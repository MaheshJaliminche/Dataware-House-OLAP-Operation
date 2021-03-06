package servletPages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OLAP_oper
 */
@WebServlet("/OLAP_oper")
public class OLAP_oper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OLAP_oper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		String disease= request.getParameter("Select");
		ArrayList<String> infor_gene = new ArrayList<>();
		infor_gene = BAL.Stats.olap_oper(disease);

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head>");
		out.println("<title>Project 1</title>");
		out.println("</head>");
		out.println("<body>");
		//--------------header----------------
		out.println("<div id=\"container\">");
		out.println("<div id=\"header\"style=\"background-color:#A0A0A0;clear:both;text-align:center;\">");
		out.println("<h1>DATA WAREHOSUE PROJECT </h1><hr></div>");

		String qr= "OLAP Operations";  

		//---------------query result----------------
		out.println("<div id=\"menu\" style=\"background-color:#EEEEEE;height:510px;width:740px;float:left;\">");
		out.println("<br><b>QUERY :"+qr+"</b><br>");
		out.println("<div id=\"RelatedDoc1\" style=\"background-color:#EEEEEE;height:504px;width:740px;border:2px solid #ccc;float:left;overflow:auto;\">");
		//		for (Document document : lstDocs) {
		//		out.println("<a href=Description?id=" +document.id + ">"+ document.title +"</a><br>");
		//		}

		out.println("<table border=\"3\">");
		out.println("<tr>");
		out.println("<td>#</td>");
		out.println("<td>UID</td>");
		out.println("</tr>");


		int count=1;
		for (String in : infor_gene) {
			//out.println(count+++"-----"+in+"<br>");
			out.println("<tr><td>"+count+++"<td>"+in+"</td></tr>");
		}
		out.println("</table>");
		out.println("</div>");	
		out.println("</div>");	


		out.println("</body");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
