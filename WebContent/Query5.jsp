
<!DOCTYPE html>
<%@page import="com.sun.xml.internal.ws.wsdl.writer.document.xsd.Import"%>
<html lang="en">
<head>
<title>DATA WAREHOUSE | Query5</title>
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
					<li ><a href="contact.html">Contact</a></li>
				</ul>
			</div>
			<!--end nav-->
		</div>
		<!--end header-->
		<div id="main">
			<div id="contact-details">

				<% 
				// java.util.ArrayList;
				double d= BAL.Stats.query5();
				String qr= "For probes belonging to GO with id='0007154', calculate F statistics of the expression values "
						+ "among patient with 'ALL' , 'AML' , 'colon tumor' , 'breast tumor";  
				
				out.println("<div id=\"menu\" style=\"height:510px;width:740px;;border:2px;float:left;\">");
				out.println("<b>QUERY :"+qr+"</b><br>");
				out.println("<div id=\"RelatedDoc1\" style=\"background-color:#EEEEEE;height:504px;width:740px;border:2px;float:left;overflow:auto;\">");

				out.println("<b> F Statistics : "+d+"</b><br>");
				out.println("</div>");	
				out.println("</div>");
				%>

			</div>
			<!--end contact-details-->


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