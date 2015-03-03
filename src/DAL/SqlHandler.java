package DAL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.linear.RRQRDecomposition;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class SqlHandler {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/project1";
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "mahesh";
	static Connection conn = null;
	static Statement stmt = null;

	public static void initConnection()
	{
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
stmt.setFetchSize(1000);
		}catch(SQLException | ClassNotFoundException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}

	}

	public static void CloseConnection()
	{
		try{
			if(stmt!=null)
				stmt.close();
		}catch(SQLException se2){
		}// nothing we can do
		try{
			if(conn!=null)
				conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}//end finally try
	}

	public static String query_1()
	{
		try {
			initConnection();
			//select count(`p_id`) as 'p_count' from `diagnosis_measure` where `ds_id` IN (select `ds_id` from disease where description='tumor' );
			StringBuilder sb = new StringBuilder();
			String sql1 = "select count(p_id) as 'p_count' from diagnosis_measure where ds_id IN (select ds_id from disease where description='tumor')";
			ResultSet rs1 = stmt.executeQuery(sql1);
			//		rs1.next();
			//		System.out.print("query 1 ---- " + rs1.getInt("p_count"));
			while(rs1.next())
			{
				System.out.println("query 1 ---- " + rs1.getInt("p_count"));
				sb.append(rs1.getInt("p_count")+",");
			}

			String sql2 = "select count(`p_id`) as 'p_count' from `diagnosis_measure` where `ds_id` IN(select `ds_id` from disease where type='leukemia' );";
			ResultSet rs2 = stmt.executeQuery(sql2);

			while(rs2.next())
			{
				System.out.println("query 1 ---- " + rs2.getInt("p_count"));
				sb.append(rs2.getInt("p_count")+",");
			}

			String sql3 = "select count(`p_id`) as 'p_count' from `diagnosis_measure` where `ds_id` IN(select `ds_id` from disease where name='ALL' );";
			ResultSet rs3 = stmt.executeQuery(sql3);

			while(rs3.next())
			{
				System.out.print("query 1 ---- " + rs3.getInt("p_count"));
				sb.append(rs3.getInt("p_count"));
			}
			return sb.toString();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return null;

	}

	public static ArrayList<String> query_2()
	{
		try {
			initConnection();
			String sql = "select distinct drug.type" +
					" from drug" +
					" join drug_usage_measure" +
					" on drug.dr_id=drug_usage_measure.dr_id" +
					" join diagnosis_measure" +
					" on drug_usage_measure.p_id= diagnosis_measure.p_id" +
					" join disease" +
					" on diagnosis_measure.ds_id= disease.ds_id" +
					" where disease.description='tumor'";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<String> arrq2= new ArrayList<>();
			while(rs.next())
			{
				System.out.println("query 2 ---- " + rs.getString("type"));
				arrq2.add( rs.getString("type"));
			}

			return arrq2;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return null;
	}

	public static ArrayList<Integer> query_3()
	{
		try {
			initConnection();
			String sql = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.cl_id='2' and microarray_fact.mu_id='001' and disease.name='ALL'" ;

			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Integer> arrq3= new ArrayList<>();
			while(rs.next())
			{
				System.out.println("query 3 ---- " + rs.getInt("exp"));
				arrq3.add(rs.getInt("exp"));

			}

			return arrq3;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return null;
	}

	public static ArrayList<double[]> query_4()
	{
		ArrayList<double[]> final_resultset = new ArrayList<double[]>();
		try {
			initConnection();
			String sql1 = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='12502' and disease.name='ALL'";
			ResultSet rs1 = stmt.executeQuery(sql1);

			//			while(rs1.next())
			//			{
			//				System.out.println("query 4 (withALL)---- " + rs1.getInt("exp"));
			//			}
			rs1.beforeFirst();
			int count1 = 0;

			while(rs1.next()) {
				count1++;
			}
			double[] result1 = new double[count1];
			int i = 0;
			rs1.beforeFirst();
			while(i < count1) {
				rs1.next();
				result1[i] = Double.parseDouble(rs1.getString("exp"));
				i++;
			}

			//--------------------------------------------------------------------------------------------            

			String sql2 = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='12502' and disease.name<>'ALL'";
			ResultSet rs2 = stmt.executeQuery(sql2);

			//			while(rs2.next())
			//			{
			//				System.out.println("query 4 (withoutALL)---- " + rs2.getInt("exp"));
			//			}
			rs2.beforeFirst();
			int count2 = 0;

			while(rs2.next()) {
				count2++;
			}
			double[] result2 = new double[count2];
			i = 0;
			rs2.beforeFirst();
			while(i < count2) {
				rs2.next();
				result2[i] = Double.parseDouble(rs2.getString("exp"));
				i++;
			}

			//--------------------------------------------------------------------------------------------    
			final_resultset.add(result1);
			final_resultset.add(result2);
			return final_resultset;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return final_resultset;



	}

	public static ArrayList<double[]> query_5()
	{

		ArrayList<double[]> final_resultset = new ArrayList<double[]>();

		try {
			initConnection();
			String sql1 = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='7154' and disease.name='ALL'";

			ResultSet rs1 = stmt.executeQuery(sql1);

			//			while(rs1.next())
			//			{
			//				System.out.println("query 5 (with ALL)---- " + rs1.getInt("exp"));
			//			}
			rs1.beforeFirst();
			int count1 = 0;

			while(rs1.next()) {
				count1++;
			}
			double[] result1 = new double[count1];
			int i = 0;
			rs1.beforeFirst();
			while(i < result1.length) {
				rs1.next();
				result1[i] = Double.parseDouble(rs1.getString("exp"));
				i++;
			}
			//--------------------------------------------------------------------------------------------
			String sql2 = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='7154' and disease.name='AML'";
			ResultSet rs2 = stmt.executeQuery(sql2);

			//			while(rs2.next())
			//			{
			//				System.out.println("query 5 (with AML) ---- " + rs2.getInt("exp"));
			//			}
			rs2.beforeFirst();
			int count2 = 0;

			while(rs2.next()) {
				count2++;
			}
			double[] result2 = new double[count2];
			i = 0;
			rs2.beforeFirst();
			while(i < result2.length) {
				rs2.next();
				result2[i] = Double.parseDouble(rs2.getString("exp"));
				i++;
			}
			//--------------------------------------------------------------------------------------------
			String sql3 = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='7154' and disease.name='colon tumor'";
			ResultSet rs3 = stmt.executeQuery(sql3);

			//			while(rs3.next())
			//			{
			//				System.out.println("query 5 (with colon tumor) ---- " + rs3.getInt("exp"));
			//			}
			rs3.beforeFirst();
			int count3 = 0;

			while(rs3.next()) {
				count3++;
			}
			double[] result3 = new double[count3];
			i = 0;
			rs3.beforeFirst();
			while(i < result3.length) {
				rs3.next();
				result3[i] = Double.parseDouble(rs3.getString("exp"));
				i++;
			}
			//--------------------------------------------------------------------------------------------
			String sql4 = "select exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='7154' and disease.name='breast tumor'";
			ResultSet rs4 = stmt.executeQuery(sql4);
			//
			//			while(rs4.next())
			//			{
			//				System.out.println("query 5 (with breast tumor) ---- " + rs4.getInt("exp"));
			//			}

			int count4 = 0;
			rs4.beforeFirst();
			while(rs4.next()) {
				count4++;
			}
			double[] result4 = new double[count4];
			i = 0;
			rs4.beforeFirst();
			while(i < result4.length) {
				rs4.next();
				result4[i] = Double.parseDouble(rs4.getString("exp"));
				i++;
			}
			//--------------------------------------------------------------------------------------------

			final_resultset.add(result1);
			final_resultset.add(result2);
			final_resultset.add(result3);
			final_resultset.add(result4);
			return final_resultset;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return final_resultset;

	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> query_6()
	{
		HashMap<String , ArrayList<Double>> ALL_Map= new HashMap<>();
		HashMap<String , ArrayList<Double>> AML_Map= new HashMap<>();
		try {
			initConnection();
			String sql1 = "select c.p_id, exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='7154' and disease.name='ALL' ";


			ResultSet rs1 = stmt.executeQuery(sql1);



			int count1 = 0;

			while(rs1.next()) {

				if (ALL_Map.containsKey(rs1.getString("p_id"))) {

					ALL_Map.get(rs1.getString("p_id")).add(Double.parseDouble(rs1.getString("exp")));

				}
				else
				{
					ArrayList<Double> a= new ArrayList<>();
					a.add(Double.parseDouble(rs1.getString("exp")));
					ALL_Map.put(rs1.getString("p_id"),a);
				}

			}

			//--------------------------------------------------------------------------------------------
			String sql2 = "select c.p_id, exp from microarray_fact"
					+" join probe"
					+" on microarray_fact.pb_id=probe.pb_id"
					+" join gene_fact"
					+" on probe.UID= gene_fact.UID"
					+" join clinical_sample_measure c"
					+" on c.s_id=microarray_fact.s_id"
					+" join diagnosis_measure"
					+" on c.p_id=diagnosis_measure.p_id"
					+" join disease"
					+" on diagnosis_measure.ds_id= disease.ds_id"
					+" where gene_fact.go_id='7154' and disease.name='AML' ";


			ResultSet rs2 = stmt.executeQuery(sql2);





			while(rs2.next()) {

				if (AML_Map.containsKey(rs2.getString("p_id"))) {

					AML_Map.get(rs2.getString("p_id")).add(Double.parseDouble(rs2.getString("exp")));

				}
				else
				{
					ArrayList<Double> a= new ArrayList<>();
					a.add(Double.parseDouble(rs2.getString("exp")));
					AML_Map.put(rs2.getString("p_id"),a);
				}

			}
			//--------------------------------------------------------------------------------------------

			ArrayList<HashMap<String , ArrayList<Double>>> finalresult= new ArrayList<>();
			finalresult.add(ALL_Map);
			finalresult.add(AML_Map);

			return finalresult;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		//return final_resultset;
		return null;

	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> olap_oper(String disease)
	{
		HashMap<String , ArrayList<Double>> ALL_Map= new HashMap<>();
		HashMap<String , ArrayList<Double>> Other_Map= new HashMap<>();
		
		ArrayList<HashMap<String , ArrayList<Double>>> finalresult= new ArrayList<>();
		
		try {
			initConnection();

			String sql1 = "select g.UID , exp from microarray_fact"+
					" join probe"+
					" on microarray_fact.pb_id= probe.pb_id"+
					" join gene g "+
					" on probe.UID= g.UID"+
					" join clinical_sample_measure"+
					" on microarray_fact.s_id= clinical_sample_measure.s_id"+
					" join diagnosis_measure"+
					" on clinical_sample_measure.p_id= diagnosis_measure.p_id"+
					" join disease"+
					" on diagnosis_measure.ds_id= disease.ds_id"+
					" where disease.name='"+ disease+"'";
			
			ResultSet rs1 = stmt.executeQuery(sql1);
			
			
			while(rs1.next()) {

				if (ALL_Map.containsKey(rs1.getString("UID"))) {

					ALL_Map.get(rs1.getString("UID")).add(Double.parseDouble(rs1.getString("exp")));

				}
				else
				{
					ArrayList<Double> a= new ArrayList<>();
					a.add(Double.parseDouble(rs1.getString("exp")));
					ALL_Map.put(rs1.getString("UID"),a);
				}

			}

			//--------------------------------------------------------------------------------------------

			String sql2 = "select g.UID , exp from microarray_fact"+
					" join probe"+
					" on microarray_fact.pb_id= probe.pb_id"+
					" join gene g "+
					" on probe.UID= g.UID"+
					" join clinical_sample_measure"+
					" on microarray_fact.s_id= clinical_sample_measure.s_id"+
					" join diagnosis_measure"+
					" on clinical_sample_measure.p_id= diagnosis_measure.p_id"+
					" join disease"+
					" on diagnosis_measure.ds_id= disease.ds_id"+
					" where disease.name<>'"+ disease+"'";
			
			ResultSet rs2 = stmt.executeQuery(sql2);
			
			
			while(rs2.next()) {

				if (Other_Map.containsKey(rs2.getString("UID"))) {

					Other_Map.get(rs2.getString("UID")).add(Double.parseDouble(rs2.getString("exp")));

				}
				else
				{
					ArrayList<Double> a= new ArrayList<>();
					a.add(Double.parseDouble(rs2.getString("exp")));
					Other_Map.put(rs2.getString("UID"),a);
				}

			}

			//--------------------------------------------------------------------------------------------

			
			finalresult.add(ALL_Map);
			finalresult.add(Other_Map);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return finalresult;
		//return null;

	}

	public static ArrayList<HashMap<String, HashMap<String, Double>>> ClassifyQuery(String disease)
	{
		HashMap<String , HashMap<String, Double>> info_All_gene= new HashMap<>();
		HashMap<String , HashMap<String , Double>>  info_notALL_gene= new HashMap<>();
		
		HashMap<String , HashMap<String , Double>> All_gene= new HashMap<>();
		HashMap<String , HashMap<String , Double>> notALL_gene= new HashMap<>();
		
		
		ArrayList<HashMap<String , HashMap<String, Double>>> finalresult= new ArrayList<>();
//		
		ArrayList<ResultSet> R= new ArrayList<>();
		
		try {
			initConnection();

			String sql1 = "select c.p_id, g.UID , exp from microarray_fact"+
					" join probe"+
					" on microarray_fact.pb_id= probe.pb_id"+
					" join gene g "+
					" on probe.UID= g.UID"+
					" join clinical_sample_measure c"+
					" on microarray_fact.s_id= c.s_id"+
					" join diagnosis_measure"+
					" on c.p_id= diagnosis_measure.p_id"+
					" join disease"+
					" on diagnosis_measure.ds_id= disease.ds_id"+
					" where disease.name='"+ disease+"' limit 55000";
			
			ResultSet rs1 = stmt.executeQuery(sql1);
			
			
			while(rs1.next()) {

				if (info_All_gene.containsKey(rs1.getString("UID"))) {

					info_All_gene.get(rs1.getString("UID")).put(rs1.getString("p_id"), Double.parseDouble(rs1.getString("exp")));

				}
				else
				{
					HashMap<String, Double> innerMap= new HashMap<>();
					innerMap.put(rs1.getString("p_id"), Double.parseDouble(rs1.getString("exp")));
					info_All_gene.put(rs1.getString("UID"),innerMap);
				}
				
				if (All_gene.containsKey(rs1.getString("p_id"))) {

					All_gene.get(rs1.getString("p_id")).put(rs1.getString("UID"), Double.parseDouble(rs1.getString("exp")));

				}
				else
				{
					HashMap<String, Double> innerMap= new HashMap<>();
					innerMap.put(rs1.getString("UID"), Double.parseDouble(rs1.getString("exp")));
					All_gene.put(rs1.getString("p_id"),innerMap);
				}

			}

			//--------------------------------------------------------------------------------------------

			String sql2 =  "select c.p_id, g.UID , exp from microarray_fact"+
					" join probe"+
					" on microarray_fact.pb_id= probe.pb_id"+
					" join gene g "+
					" on probe.UID= g.UID"+
					" join clinical_sample_measure c"+
					" on microarray_fact.s_id= c.s_id"+
					" join diagnosis_measure"+
					" on c.p_id= diagnosis_measure.p_id"+
					" join disease"+
					" on diagnosis_measure.ds_id= disease.ds_id"+
					" where disease.name<>'"+ disease+"' limit 55000";
			
			ResultSet rs2 = stmt.executeQuery(sql2);
			
			
			while(rs2.next()) {

				if (info_notALL_gene.containsKey(rs2.getString("UID"))) {

					info_notALL_gene.get(rs2.getString("UID")).put(rs2.getString("p_id"), Double.parseDouble(rs2.getString("exp")));

				}
				else
				{
					HashMap<String, Double> innerMap= new HashMap<>();
					innerMap.put(rs2.getString("p_id"), Double.parseDouble(rs2.getString("exp")));
					info_notALL_gene.put(rs2.getString("UID"),innerMap);
				}
				
				if (notALL_gene.containsKey(rs2.getString("p_id"))) {

					notALL_gene.get(rs2.getString("p_id")).put(rs2.getString("UID"), Double.parseDouble(rs2.getString("exp")));

				}
				else
				{
					HashMap<String, Double> innerMap= new HashMap<>();
					innerMap.put(rs2.getString("UID"), Double.parseDouble(rs2.getString("exp")));
					notALL_gene.put(rs2.getString("p_id"),innerMap);
				}

			}

			//--------------------------------------------------------------------------------------------

			
			finalresult.add(info_All_gene);
			finalresult.add(info_notALL_gene);
			finalresult.add(All_gene);
			finalresult.add(notALL_gene);
			
			
			
			
			//return rs1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return finalresult;
		//return null;

	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> ClassifyQuery1(String disease,String whrCls)
	{
		HashMap<String , ArrayList<Double>> ALL_Map= new HashMap<>();
		HashMap<String , ArrayList<Double>> Other_Map= new HashMap<>();
		ArrayList<HashMap<String , ArrayList<Double>>> finalresult= new ArrayList<>();
			
		try {
			initConnection();

			String sql1 = "select c.p_id, exp from microarray_fact"+
					" join probe"+
					" on microarray_fact.pb_id= probe.pb_id"+
					" join gene g "+
					" on probe.UID= g.UID"+
					" join clinical_sample_measure c"+
					" on microarray_fact.s_id= c.s_id"+
					" join diagnosis_measure"+
					" on c.p_id= diagnosis_measure.p_id"+
					" join disease"+
					" on diagnosis_measure.ds_id= disease.ds_id"+
					" where disease.name='"+ disease+"' and g.UID in ("+whrCls+")";
			
			ResultSet rs1 = stmt.executeQuery(sql1);
			
			
			while(rs1.next()) {
				if (ALL_Map.containsKey(rs1.getString("p_id"))) {

					ALL_Map.get(rs1.getString("p_id")).add(Double.parseDouble(rs1.getString("exp")));

				}
				else
				{
					ArrayList<Double> a= new ArrayList<>();
					a.add(Double.parseDouble(rs1.getString("exp")));
					ALL_Map.put(rs1.getString("p_id"),a);
				}
				
			}

			
			for (Entry<String, ArrayList<Double>> hashMap : ALL_Map.entrySet()) {
				System.out.println(hashMap.getKey());
				System.out.println(hashMap.getValue().toString());
			}
			//--------------------------------------------------------------------------------------------

			String sql2 =  "select c.p_id, exp from microarray_fact"+
					" join probe"+
					" on microarray_fact.pb_id= probe.pb_id"+
					" join gene g "+
					" on probe.UID= g.UID"+
					" join clinical_sample_measure c"+
					" on microarray_fact.s_id= c.s_id"+
					" join diagnosis_measure"+
					" on c.p_id= diagnosis_measure.p_id"+
					" join disease"+
					" on diagnosis_measure.ds_id= disease.ds_id"+
					" where disease.name<>'"+ disease+"' and g.UID in ("+whrCls+")";
			
			ResultSet rs2 = stmt.executeQuery(sql2);
			
			
			while(rs2.next()) {

				if (Other_Map.containsKey(rs2.getString("p_id"))) {

					Other_Map.get(rs2.getString("p_id")).add(Double.parseDouble(rs2.getString("exp")));

				}
				else
				{
					ArrayList<Double> a= new ArrayList<>();
					a.add(Double.parseDouble(rs2.getString("exp")));
					Other_Map.put(rs2.getString("p_id"),a);
				}
			
			}

			//--------------------------------------------------------------------------------------------

			
			finalresult.add(ALL_Map);

			finalresult.add(Other_Map);
			
			
			
			
			//return rs1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		return finalresult;
		//return null;

	}

	
	public static ArrayList<Double> getPatient(String Patient, String infoGene)
	{
		
		try {
			initConnection();
			String sql = "Select "+Patient+" from test_samples where UID in ("+infoGene+")";
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<Double> arrq2= new ArrayList<>();
			while(rs.next())
			{
				arrq2.add( Double.parseDouble(rs.getString(Patient)));
			}

			return arrq2;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			CloseConnection();
		}
		//return null;
		return null;
		
		
	}

}
