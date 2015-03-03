
<!DOCTYPE html>
<%@page import="com.sun.xml.internal.ws.wsdl.writer.document.xsd.Import"%>
<html lang="en">
<head>
<title>DATA WAREHOUSE | OLAP</title>
<meta charset="utf-8">
<link type="text/css" rel="stylesheet" href="styles/style.css" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<!--[if IE 6]>
<style>body { behavior: url("styles/ie6-hover-fix.htc"); }</style>
<script type="text/javascript" src="js/ie6-transparency.js"></script>
<script>DD_belatedPNG.fix('#contact-form .submit');</script>
<link type="text/css" rel="stylesheet" href="styles/ie6.css" />
<![endif]-->
<!--[if IE 7]><link type="text/css" rel="stylesheet" href="styles/ie7.css" /><![endif]-->
</head>
<body class="page">
	<div id="wrap">
		<div id="header">
			<h1>
				<a href="#">DATA WAREHOUSE</a>
			</h1>
			<div id="nav">
				<ul class="menu">
					<li><a href="NewFile.jsp">Home</a></li>
					<li><a href="test.pdf">Schema</a></li>
					<li><a href="Documents.html">Documents</a></li>
					<li><a href="contact.html">Contact</a></li>
				</ul>
			</div>
			<!--end nav-->
		</div>
		<!--end header-->
		<div id="main">
			<div id="contact-details">

				<%
					// java.util.ArrayList;
					//String disease= request.getParameter("Select");
					 String disease=request.getParameter("Select");
					java.util.ArrayList<String> infor_gene = new java.util.ArrayList<String>();
					String qr;
					if(disease==null)
					{
						infor_gene = BAL.Stats.olap_oper("ALL");
						 qr = "Informative gene for patient with ALL";
					}
					else
					{
						infor_gene = BAL.Stats.olap_oper(disease);
						qr = "Informative gene for patient with "+disease;
					}
					

					

					out.println("<div id=\"menu\" style=\"height:510px;width:740px;;border:2px;float:left;\">");
					out.println("<b>QUERY :" + qr + "</b><br>");
					out.println("<div id=\"RelatedDoc1\" style=\"background-color:#EEEEEE;height:504px;width:740px;border:2px;float:left;overflow:auto;\">");

					//out.println("<table border=\"1\">");
					//out.println("<tr>");
					//out.println("<td>#</td>");
					//out.println("<td>UID</td>");
					//out.println("</tr>");

					int count = 1;
					for (String in : infor_gene) {
						//out.println(count+++"-----"+in+"<br>");
						out.println(count++ + " --  " + in +"<br>");
					}
					//out.println("</table>");
					out.println("</div>");
					out.println("</div>");
				%>

			</div>
			<!--end contact-details-->
			<div>
			<form method="POST" action="OLAP.jsp">
				Select Diseases : <select name="Select">
					<option value="ALL">ALL</option>
					<option value="AML">AML</option>
					<option value="colon tumor">colon tumor</option>
					<option value="breast tumor">breast tumor</option>
					<option value="Giloblastome">Giloblastomer</option>
					<option value="Flu">Flu</option>
				</select> <input type="submit" value="Knowledge Discovery"/>

			</form>
	</div>
		</div>
		<!--end main-->

		<!--end footer-->
	</div>
	<!--end wrap-->
	<div class="cache-images">
		<img src="images/red-button-bg.png" width="0" height="0" alt="" />
	</div>
	<!--end cache-images-->
</body>
</html>