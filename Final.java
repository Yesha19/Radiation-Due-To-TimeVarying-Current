//EMT PROJECT
//DONE BY:- DEVSHREE PATEL, NAISHI SHAH, RATNAM PARIKH, YESHA SHASTRI,MEET MODI,PARAM RAVAL
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import java.util.*;

class Application
{
	static Scanner input = new Scanner(System.in);
	static double C = 3*Math.pow(10,8);
	public static void main(String[] args) throws IOException
	{
		
		double upper_l, lower_l;
		double ti;
		double time, s, integrate_v,E = 0.1,B;
		double[] vector_pot = new double[3];
		float time_r, I;
		System.out.print("Enter the maximum value of time:");
		ti = input.nextDouble();
		double[] vec = new double[10000];
		double cnt = 0;
		File myFile1 = new File("E.txt");
		File myFile2 = new File("B.txt");
		File myFile3 = new File("temp.txt");
		try
		{
			myFile1.createNewFile();
			FileOutputStream fout = null;
			DataOutputStream dout = null;
			fout = new FileOutputStream(myFile1);
			dout = new DataOutputStream(fout);
			fout.close();
			dout.close();
		}
		catch (FileNotFoundException e) 
		{
		    System.out.println("FILE ERROR");
		}
		try{	
		myFile2.createNewFile();
		FileOutputStream fout = null;
		DataOutputStream dout = null;
		fout = new FileOutputStream(myFile2);
		dout = new DataOutputStream(fout);
		fout.close();
		dout.close();
		}
		catch (FileNotFoundException e) 
		{
		    System.out.println("FILE ERROR");
		}

		try
		{
			myFile3.createNewFile();
			FileOutputStream fout = null;
			DataOutputStream dout = null;
			fout = new FileOutputStream(myFile3);
			dout = new DataOutputStream(fout);
			fout.close();
			dout.close();
		}
		catch (FileNotFoundException e) 
		{
		    System.out.println("FILE ERROR");
		}
		int i = 0;
		int j = 0;
		int m = 0;
		int temp = 0;
		double z = 0;
		
		
            		
		for(s = 1;s < 1000; s+=3)
		{
			for(time = 0.1; time < ti; time += 0.1)
			{
				for(z = -10 ; z <= 10; z+=1.5)
				{
					time_r = calc_time_tr(time,s,z);
					I = 0;
					String str2 = Float.toString(time_r);
					File file =new File("input.txt");
					Scanner in = null;
					String line = null;
					try 
					{
					    in = new Scanner(file);
					    while(in.hasNext())
					    {
						line =in.nextLine();
						if(line.contains(str2))
						    System.out.println(line);
					    }
					} 
					catch (FileNotFoundException e) 
					{
					    e.printStackTrace();
					}
	
					FileOutputStream fOut = null;
					DataOutputStream dOut = null;
					fOut = new FileOutputStream(myFile3);
			      		dOut = new DataOutputStream(fOut);
					dOut.writeBytes(String.format(line));
					fOut.close();
					dOut.close();

					 String ipFile=null;
					    
					   ArrayList<Float> arr1 = new ArrayList<>();
					   ArrayList<Float> arr2 = new ArrayList<>();
					   
					   int p=0,q=0; 
					    
					    try{
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("temp.txt"))));
					    
					   
					      String current=null;
					      float x, y;
					      String[] tempArr=null;
						    
						while((current = reader.readLine()) != null)
						{
							tempArr = current.split("\\d\\s+");
							
							x = Float.valueOf(tempArr[0].trim());
							y = Float.valueOf(tempArr[1].trim());
							
							arr1.add(x);
							arr2.add(y);
							p++;       	
						}
					     
						
					    } catch (Exception e) {
					      e.printStackTrace();
					    }

						 for(q = 0; q < arr1.size(); q++)
						{
							
							if(time_r==arr1.get(q))
							{
								
								I = arr2.get(q);
								temp = 1;
								break;
							}
						}
						if(temp!=1)
						{
						float myNumber = time_r;
						float distance = Math.abs(arr1.get(0) - myNumber);
						int idx = 0;
						for(int c = 1; c < arr1.size(); c++){
						    float cdistance = Math.abs(arr1.get(c) - myNumber);
						    if(cdistance < distance){
							idx = c;
							distance = cdistance;
						    }
						}
						I  = arr1.get(idx);
						}
						


					
					integrate_v =  integrate(-10, 10, s, 1);/////lower,upper,s,I
					if(cnt%500 == 0)
					{					
					vector_pot[1] = vector_pot[0];
					}
					vector_pot[0] = integrate_v* Math.pow(10,10);
					/*System.out.println(vector_pot[0]);
					System.out.println("PREVIOUS :" + vector_pot[1]);
					*/if(cnt%300 == 0 && cnt > 300)
					  {
						E = calc_E(vector_pot[1],vector_pot[0],0.1);
						if(E!=0)
						{				
							FileOutputStream fout = null;
							DataOutputStream dout = null;
							fout = new FileOutputStream(myFile1, true);
							dout = new DataOutputStream(fout);
							dout.writeBytes(String.format("%.3f",E) +"\t"+ s +"\n");
							fout.close();
							dout.close();
							//System.out.println("Electric Field" + E);
						}
						B = calc_B(vector_pot[1],vector_pot[0],3);
					
						if(B!=0)
						{
							
							FileOutputStream fout = null;
							DataOutputStream dout = null;
							fout = new FileOutputStream(myFile2, true);
							dout = new DataOutputStream(fout);
							dout.writeBytes(String.format("%.3f",B)+ "\t"+s+"\n");
							fout.close();
							dout.close();
							//System.out.println("Magnetic Field" + B);
						}
						
					}
					else
					{
						cnt++;
					}
				}
				
			}  

		}


	}


	static double fn(double z, double s, float I)
	{
		double z_term =  1/(Math.sqrt(Math.pow(z,2) + s*s));
		double whole_func = (I*z_term)/Math.pow(10,7);
         	return whole_func;
	}

	static double calc_Upper(double t, double s)
	{
		 return 10;
	}

	static double calc_Lower(double t, double s)
	{
		return -10;
	}

	static float calc_time_tr(double time, double s, double z)
	{
		double temp;
		temp = Math.sqrt((Math.pow(z,2) + s*s))/C;
		time = time - temp;
		return (float)time;
	}

	static double calc_i_O_t(double time, double exponent, double constant) throws IOException
	{
		double current;
		current = constant * Math.pow(time,exponent);
		
		
		File myFile = new File("input.txt");
		myFile.createNewFile();
		FileOutputStream fout = null;
		DataOutputStream dout = null;
		fout = new FileOutputStream(myFile, true);
      		dout = new DataOutputStream(fout);
		dout.writeBytes(String.format("%.3f",time)+"\t");
		dout.writeBytes(String.format("%.3f",current)+"\n");
		fout.close();
		dout.close();
		
		return current;
	}

	static double calc_E(double fa, double fb, double h) 
	{
		return  -(fb - fa)/h;
	}

	static double calc_B(double fa, double fb, double h)
	{
		return (fb - fa)/h;
	}

        static double integrate(double lower_l, double upper_l,double s, float I) {
        double h = (upper_l - lower_l) / 1000;            
        double sum = 0.5 * (fn(lower_l,s,I) + fn(upper_l,s,I));   
        for (int i = 1; i < 1000; i++)
        {
      	        double x = lower_l + h * i;
                sum = sum + fn(x,s,I);
        }

      return sum * h;
   }

}





