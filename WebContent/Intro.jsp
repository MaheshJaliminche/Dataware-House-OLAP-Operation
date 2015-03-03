<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DataWarehouse</title>
</head>
</head>
<body>
<div id="header"style="background-color:#A0A0A0;clear:both;text-align:center;">
<h1>DATA WAREHOSUE PROJECT </h1>
<hr>
</div>
<div id="frm" style="background-color:#FFFFFF;height:100%;width:100%;clear:both;text-align:center">

<table border="2" style="height:100%">
<tr >
<td style="height:100%;width: 90%;text-align:center ">

<p>This is a paragraph</p>
<p>This is another paragraph</p>



<form action="Query1" >
<input type="submit" value="Query1" />

</form>
       
       <form action="Query2" >
<input type="submit" value="Query2" />

</form>
<form action="Query3" >
<input type="submit" value="Query3" />

</form>
<form action="Query4" >
<input type="submit" value="Query4" />

</form>
<form action="Query5" >
<input type="submit" value="Query5" />

</form>
<form action="Query6" >
<input type="submit" value="Query6" />

</form>  


<form action="OLAP_oper" >
 Select Diseases :
<select name="Select" >
<option value="ALL">ALL</option>
<option value="AML">AML</option>
<option value="colon tumor">colon tumor</option>
<option value="breast tumor">breast tumor</option>
<option value="Giloblastome">Giloblastomer</option>
<option value="Flu">Flu</option>
</select>

<input type="submit" value="OLAP Operations" />

</form>

<form action="Classify" >
 Select Diseases :
<select name="Select" >
<option value="ALL">ALL</option>
<option value="AML">AML</option>
<option value="colon tumor">colon tumor</option>
<option value="breast tumor">breast tumor</option>
<option value="Giloblastome">Giloblastomer</option>
<option value="Flu">Flu</option>
</select>

<input type="submit" value="Classify" />
</form>    
              
</td>

<td>
jals;dkjfa;lsdkfja;lsdkfj
</td>

</tr>
</table>

</div>
</body>
</html>