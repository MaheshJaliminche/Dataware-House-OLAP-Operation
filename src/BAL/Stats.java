package BAL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.inference.TTest;
import org.apache.commons.math3.stat.inference.TestUtils;

import servletPages.OLAP_oper;

import com.sun.org.apache.bcel.internal.generic.RETURN;


public class Stats {

	public static double cal_Tstatiatics(double a[], double b[])
	{

		Mean m = new Mean();
		double m_a = m.evaluate(a);
		double m_b = m.evaluate(b);

		Variance v = new Variance();
		double v_a = v.evaluate(a);
		double v_b = v.evaluate(b);

		double t_stat = 0;

		t_stat = (m_a - m_b)/Math.sqrt(v_a/a.length + v_b/b.length);

		return  Math.abs(t_stat);

	}

	public static double AverageCorrelation(double a[], double b[])
	{
		Variance v = new Variance();
		double v_a = v.evaluate(a);
		double v_b = v.evaluate(b);
		Covariance cov= new Covariance();
		double c_v=cov.covariance(a, b);
		double correlation= c_v/Math.sqrt(v_a*v_b);
		//return Math.abs(correlation);
		PearsonsCorrelation p= new PearsonsCorrelation();
		
		
		return p.correlation(a, b);

	}

	public static double cal_Fstatiatics(double a[], double b[], double c[], double d[] )
	{
		double k = 4;

		ArrayList<double[]> overall_arr = new ArrayList<double[]>();    

		overall_arr.add(a);
		overall_arr.add(b);
		overall_arr.add(c);
		overall_arr.add(d);

		double N = a.length+b.length+c.length+d.length;
		//double[] overall_elements = new double[n1+n2+n3+n4];

		ArrayList<Double> arr_mean = new ArrayList<Double>();

		for (double[] es : overall_arr) {

			Mean m = new Mean();    
			double mean = m.evaluate(es);
			arr_mean.add(mean);
		}

		double overall_mean = (arr_mean.get(0)+arr_mean.get(1)+arr_mean.get(2)+arr_mean.get(3))/k; ;

		double SS_cond = 0;

		for (double am : arr_mean) {

			SS_cond = SS_cond +Math.pow(am - overall_mean, 2);

		}

		double SS_error = 0;

		int i =0;
		for (double[] oa : overall_arr) {

			for (double e : oa) {

				SS_error = SS_error +Math.pow(e - arr_mean.get(i), 2);

			}
			i++;
		}

		double dof_for_condition = k-1;

		double MS_cond = SS_cond /dof_for_condition; 

		double dof_for_error = N-k;

		double MS_error = SS_error/dof_for_error;


		double f_Statistics = MS_cond / MS_error;

		return TestUtils.oneWayAnovaFValue(overall_arr);

	}

	public static String query1()
	{
//		double[] a1={3,4,1,2};
//		double[] a2={7,10,8,9};
//		System.out.println(AverageCorrelation(a1, a2));
		return DAL.SqlHandler.query_1();
	}
	
	

	public static ArrayList<String> query2()
	{
		return DAL.SqlHandler.query_2();
	}

	public static ArrayList<Integer> query3()
	{
		return DAL.SqlHandler.query_3();
	}

	public static double query4()
	{
		ArrayList<double[]> arrq4= new ArrayList<>();
		arrq4= DAL.SqlHandler.query_4();
		double tstat= cal_Tstatiatics(arrq4.get(0), arrq4.get(1));

		//TestUtils.homoscedasticT(arrq4.get(0), arrq4.get(1));
		//return TestUtils.homoscedasticT(arrq4.get(0), arrq4.get(1));
		return tstat;
	}

	public static double query5()
	{
		ArrayList<double[]> arrq5= new ArrayList<>();
		arrq5= DAL.SqlHandler.query_5();
		double fstat= cal_Fstatiatics(arrq5.get(0), arrq5.get(1), arrq5.get(2), arrq5.get(3));
		//return tstat;

		return fstat;


	}

	public static double[] query6()
	{
		double[] avgCorr= new double[2]; 
		ArrayList<HashMap<String, ArrayList<Double>>> arrq6= new ArrayList<>();
		arrq6= DAL.SqlHandler.query_6();

		HashMap<String, ArrayList<Double>> ALL= new HashMap<>();
		ALL= (HashMap<String, ArrayList<Double>>) arrq6.get(0).clone();
		HashMap<String, ArrayList<Double>> AML= new HashMap<>();
		AML= (HashMap<String, ArrayList<Double>>) arrq6.get(1).clone();

		Set<String> keyset= ALL.keySet();
		double Avg_corr=0;
		for (int i = 0; i < keyset.size(); i++) {
			for (int j = i+1; j < keyset.size(); j++) {
				String s1 =(String) keyset.toArray()[i];
				String s2 =(String) keyset.toArray()[j];

				ArrayList<Double> p1= new ArrayList<>();
				p1=(ArrayList<Double>) ALL.get(s1).clone();

				
				//Collections.sort(p1);
//				TreeSet<Double> ts_1 = new TreeSet<>();
//				ts_1.addAll(p1);
				
				ArrayList<Double> p2= new ArrayList<>();
				p2=(ArrayList<Double>) ALL.get(s2).clone();
				//Collections.sort(p2);
//				TreeSet<Double> ts_2 = new TreeSet<>();
//				ts_2.addAll(p2);
				
				
				double[] d1= new double[p1.size()];
				double[] d2= new double[p2.size()];
				int z=0;
				for (double d : p1) {
					d1[z++]=d;
				}
				z=0;
				for (double d : p2) {
					d2[z++]=d;
				}
				Avg_corr += AverageCorrelation(d1,d2);
			}
		}

		double ALL_ALL_perCorr = ALL.size()*(ALL.size()-1)/2;


		avgCorr[0]= Avg_corr/ALL_ALL_perCorr;

		//------------------------------------------------------------------------------------------


		Set<String> keyset1= AML.keySet();
		double Avg_corr1=0;
		for (int i = 0; i < keyset.size(); i++) {
			for (int j = 0; j < keyset1.size(); j++) {
				String s1 =(String) keyset.toArray()[i];
				String s2 =(String) keyset1.toArray()[j];

				ArrayList<Double> p1= new ArrayList<>();
				p1=(ArrayList<Double>) ALL.get(s1).clone();

//				TreeSet<Double> ts_1 = new TreeSet<>();
//				ts_1.addAll(p1);
				
				
				ArrayList<Double> p2= new ArrayList<>();
				p2=(ArrayList<Double>) AML.get(s2).clone();
				
//				TreeSet<Double> ts_2 = new TreeSet<>();
//				ts_2.addAll(p2);
				
				
				double[] d1= new double[p1.size()];
				double[] d2= new double[p2.size()];
				int z=0;
				for (double d : p1) {
					d1[z++]=d;
				}
				z=0;
				for (double d : p2) {
					d2[z++]=d;
				}
				Avg_corr1 += AverageCorrelation(d1,d2);
			}
		}

		double ALL_AML_perCorr = ALL.size()*(AML.size());


		avgCorr[1]= Avg_corr1/ALL_AML_perCorr;



		return avgCorr;
	}

	public static ArrayList<String> olap_oper(String disease)
	{
		ArrayList<HashMap<String , ArrayList<Double>>> temp = new ArrayList<>();
		temp= DAL.SqlHandler.olap_oper(disease);

		HashMap<String , ArrayList<Double>> ALL_Map= new HashMap<>();
		HashMap<String , ArrayList<Double>> Other_Map= new HashMap<>();


		ALL_Map = (HashMap<String, ArrayList<Double>>) temp.get(0).clone();
		Other_Map= (HashMap<String, ArrayList<Double>>) temp.get(1).clone();


		Set<String> keyset= ALL_Map.keySet();
		//Set<String> keyset1= Other_Map.keySet();
		double t_stat=0;
		ArrayList<String> informative_gene= new ArrayList<>();

		for ( Entry<String, ArrayList<Double>> entry : ALL_Map.entrySet()) 
		{
			String u_id = entry.getKey();
			ArrayList<Double> p1= new ArrayList<>();
			p1 = entry.getValue();

			ArrayList<Double> p2= new ArrayList<>();
			p2 = Other_Map.get(u_id);

			double[] d1= new double[p1.size()];
			double[] d2= new double[p2.size()];

			int z=0;
			for (double d : p1) {
				d1[z++]=d;
			}
			z=0;
			for (double d : p2) {
				d2[z++]=d;
			}
//			t_stat = cal_Tstatiatics(d1, d2);
//
//			TDistribution tdist = new TDistribution(degreesofFreedom(d1, d2));
//			double p = 1 - tdist.cumulativeProbability(t_stat);

			TTest t = new TTest();
			double p =t.homoscedasticTTest(d1, d2);
			//double p =t.tTest(d1, d2);
			
			if(p < 0.01)
			{
				informative_gene.add(u_id);
			}
			

		}

		return informative_gene;

	}

	public static ArrayList<String> Classify(String disease)
	{
		ArrayList<String> Classification= new ArrayList<>();
		ArrayList<HashMap<String, HashMap<String, Double>>> result = new ArrayList<>();

		HashMap<String , HashMap<String, Double>> info_All_gene= new HashMap<>();
		HashMap<String , HashMap<String , Double>>  info_notALL_gene= new HashMap<>();

		HashMap<String , HashMap<String , Double>> All_gene= new HashMap<>();
		HashMap<String , HashMap<String , Double>> notALL_gene= new HashMap<>();

		result= DAL.SqlHandler.ClassifyQuery(disease);

		info_All_gene=(HashMap<String, HashMap<String, Double>>) result.get(0).clone();
		info_notALL_gene=(HashMap<String, HashMap<String, Double>>) result.get(1).clone();
		All_gene=(HashMap<String, HashMap<String, Double>>) result.get(2).clone();
		notALL_gene=(HashMap<String, HashMap<String, Double>>) result.get(3).clone();


		double t_stat=0;
		//ArrayList<String> informative_gene= new ArrayList<>();
		ArrayList<String> informative_gene= new ArrayList<>();
//		for ( Entry<String, HashMap<String, Double>> entry : info_All_gene.entrySet()) 
//		{
//			String u_id = entry.getKey();
//			HashMap<String, Double> p1= new HashMap<>();
//			p1 = (HashMap<String, Double>) entry.getValue().clone();
//			double[] d1= new double[p1.size()];
//			int z=0;
//			for(Entry<String, Double> e1:p1.entrySet())
//			{
//				d1[z++]=e1.getValue();
//			}
//			//ArrayList<Double> p1= new ArrayList<>();
//
//
//			HashMap<String, Double> p2= new HashMap<>();
//			p2 = (HashMap<String, Double>) info_notALL_gene.get(u_id).clone();
//
//
//			double[] d2= new double[p2.size()];
//			z=0;
//			for(Entry<String, Double> e1:p1.entrySet())
//			{
//				d2[z++]=e1.getValue();
//			}
////			t_stat = cal_Tstatiatics(d1, d2);
////
////			TDistribution tdist = new TDistribution(degreesofFreedom(d1, d2));
////			double p = 1 - tdist.cumulativeProbability(t_stat);
//			TTest t = new TTest();
//			double p =t.tTest(d1, d2);
//			if(p < 0.01)
//			{
//				informative_gene.add(u_id);
//			}
//
//		}

		informative_gene.addAll(olap_oper(disease));
		
		for(int i=1;i<=5;i++)
		{
			ArrayList<Double> RA= new ArrayList<>();
			ArrayList<Double> RB= new ArrayList<>();
			String A= informative_gene.toString();
			String gene= A.substring(1, A.length()-1);

			ArrayList<Double> Pn= DAL.SqlHandler.getPatient("test"+i, gene);
			double[] pn= new double[Pn.size()];
			int a=0;
			for (Double double1 : Pn) {
				pn[a++]=double1;
			}


			for(Entry<String, HashMap<String, Double>> entry:All_gene.entrySet())
			{
				HashMap<String, Double> temp= new HashMap<>();
				ArrayList<Double> PA= new ArrayList<>();
				temp= (HashMap<String, Double>) entry.getValue().clone();
				for (String string : informative_gene) {

					if (temp.containsKey(string)) {
						PA.add(temp.get(string));	
					}
				}
				double[] pa= new double[PA.size()];
				int b=0;
				for (Double double1 : PA) {
					pa[b++]=double1;
				}

				RA.add(AverageCorrelation(pa, pn));
			}

			for(Entry<String, HashMap<String, Double>> entry:notALL_gene.entrySet())
			{
				HashMap<String, Double> temp= new HashMap<>();
				ArrayList<Double> PB= new ArrayList<>();
				temp= (HashMap<String, Double>) entry.getValue().clone();
				for (String string : informative_gene) {

					if (temp.containsKey(string)) {
						PB.add(temp.get(string));	
					}
				}
				double[] pb= new double[PB.size()];
				int b=0;
				for (Double double1 : PB) {
					pb[b++]=double1;
				}

				RB.add(AverageCorrelation(pb, pn));
			}


			double[] d1= new double[RA.size()];
			double[] d2= new double[RB.size()];

			int z=0;
			for (double d : RA) {
				d1[z++]=d;
			}
			z=0;
			for (double d : RB) {
				d2[z++]=d;
			}
//			t_stat = cal_Tstatiatics(d1, d2);
//
//			TDistribution tdist = new TDistribution(degreesofFreedom(d1, d2));
//			double p = 1 - tdist.cumulativeProbability(t_stat);
			TTest t = new TTest();
			double p =t.homoscedasticTTest(d1, d2);
			if(p < 0.01)
			{
				Classification.add("Test"+i+" is classified as having "+disease);
				System.out.println("Test"+i+" is classified as having "+disease);
			}
			else
			{
				Classification.add("Test"+i+" is classified as not having "+disease);
				System.out.println("Test"+i+" is classified as not having "+disease);
			}

		}

return Classification;
	}

	public static ArrayList<String> Classify1(String disease)
	{
		ArrayList<String> Classification= new ArrayList<>();
		ArrayList<HashMap<String, ArrayList<Double>>> result = new ArrayList<>();

		HashMap<String , ArrayList<Double>> info_All_gene= new HashMap<>();
		HashMap<String , ArrayList<Double>>  info_notALL_gene= new HashMap<>();

	
		ArrayList<String> informative_gene= new ArrayList<>();

		informative_gene.addAll(olap_oper(disease));
		
		String A= informative_gene.toString();
		String gene= A.substring(1, A.length()-1);
		result= DAL.SqlHandler.ClassifyQuery1(disease,gene);
		info_All_gene= (HashMap<String, ArrayList<Double>>) result.get(0).clone();
		info_notALL_gene= (HashMap<String, ArrayList<Double>>) result.get(1).clone();
		for(int i=1;i<=5;i++)
		{
			ArrayList<Double> RA= new ArrayList<>();
			ArrayList<Double> RB= new ArrayList<>();
			

			ArrayList<Double> Pn= DAL.SqlHandler.getPatient("test"+i, gene);
//			
//			TreeSet<Double> ts_1 = new TreeSet<>();
//			ts_1.addAll(Pn);
			//Collections.sort(Pn);
			double[] pn= new double[Pn.size()];
			int a=0;
			for (Double double1 : Pn) {
				pn[a++]=double1;
			}


			for(Entry<String, ArrayList<Double>> entry:info_All_gene.entrySet())
			{
				
				
				ArrayList<Double> PA= new ArrayList<>();
				PA= entry.getValue();
				
//				TreeSet<Double> ts_2 = new TreeSet<>();
//				ts_2.addAll(PA);
				//Collections.sort(PA);
				double[] pa= new double[PA.size()];
				int b=0;
				for (Double double1 : PA) {
					pa[b++]=double1;
				}

				RA.add(AverageCorrelation(pa, pn));
			}

			for(Entry<String, ArrayList<Double>> entry:info_notALL_gene.entrySet())
			{
				
				
				ArrayList<Double> PB= new ArrayList<>();
				PB= entry.getValue();
				
//				TreeSet<Double> ts_2 = new TreeSet<>();
//				ts_2.addAll(PB);
				
				//Collections.sort(PB);
				double[] pb= new double[PB.size()];
				int b=0;
				for (Double double1 : PB) {
					pb[b++]=double1;
				}

				RB.add(AverageCorrelation(pb, pn));
			}
		
			

			double[] d1= new double[RA.size()];
			double[] d2= new double[RB.size()];

			int z=0;
			for (double d : RA) {
				d1[z++]=d;
			}
			z=0;
			for (double d : RB) {
				d2[z++]=d;
			}
//			t_stat = cal_Tstatiatics(d1, d2);
//
//			TDistribution tdist = new TDistribution(degreesofFreedom(d1, d2));
//			double p = 1 - tdist.cumulativeProbability(t_stat);
			TTest t = new TTest();
			double p =t.homoscedasticTTest(d1, d2);
			//t.tTest(d1, d2);
			if(p < 0.01)
			{
				Classification.add("Test"+i+" is classified as having "+disease);
				System.out.println("Test"+i+" is classified as having "+disease);
			}
			else
			{
				Classification.add("Test"+i+" is classified as not having "+disease);
				System.out.println("Test"+i+" is classified as not having "+disease);
			}

		}

		return Classification;
	}

  

}


