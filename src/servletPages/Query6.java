package servletPages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Query6
 */
@WebServlet("/Query6")
public class Query6 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Query6() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		double[] d= BAL.Stats.query6();
		
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
		
		String qr= "6";  
		
		//---------------query result----------------
		out.println("<div id=\"menu\" style=\"background-color:#EEEEEE;height:510px;width:740px;float:left;\">");
		out.println("<br><b>QUERY :"+qr+"</b><br>");
		out.println("<div id=\"RelatedDoc1\" style=\"background-color:#EEEEEE;height:504px;width:740px;border:2px solid #ccc;float:left;overflow:auto;\">");
//		for (Document document : lstDocs) {
//		out.println("<a href=Description?id=" +document.id + ">"+ document.title +"</a><br>");
//		}
//		for (Integer in : arrq3) {
//			out.println("<b>"+in+"</b><br>");
//		}
		out.println("<b>"+d[0]+"</b><br>");
		out.println("<b>"+d[1]+"</b><br>");
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
