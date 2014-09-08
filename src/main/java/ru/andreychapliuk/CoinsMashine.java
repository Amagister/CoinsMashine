package ru.andreychapliuk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class CoinsMashine 
{

	private int[] arrMoney = {2,3,7,15,24,38,84,29,33,22,74,91,32,40,8};
	private final int[] constMoney = {500,200,100,50,20,10,5,2,1,50,25,10,5,2,1};
	private int[] arrMoneyOut = new int[15];
	private String[] arrNameMoney = {"UAH 500", "UAH 200", "UAH 100", "UAH 50", "UAH 20", "UAH 10", 
			"UAH 5", "UAH 2", "UAH 1", "UAH 0.50", "UAH 0.25", "UAH 0.10", "UAH 0.05", "UAH 0.02", "UAH 0.01"};
	
    public CoinsMashine() {
		super();
		// TODO Auto-generated constructor stub
		setProp();
	}
	public static void main( String[] args )
    {
		double num = 0.0;
		boolean ext = true;
		CoinsMashine cm = new CoinsMashine();
//    	OutputStream output = null;
    	
    	
    	Scanner scanner = new Scanner(System.in);
    	while(ext){
    		cm.clear();
    	System.out.println("Type the sum UAH in format: XXX,xx");
    	System.out.println("sample: 123,54");
    	System.out.println("for quit press any symbol and press Enter");
    	try{
    	num = scanner.nextDouble();
    	}catch(InputMismatchException ime){
    		ext = false;
    		break;
    	}
    	System.out.println(num);
        
        if(cm.checkSum(num) == false){
        	System.out.println("money is tight in mashine");
        	continue;
        }
        cm.calculateSum(num);
        System.out.println(cm.toString());
        
    	}
        scanner.close();
        
    }
	
	private void clear() {
		// TODO Auto-generated method stub
		for(int i = 0; i < arrMoneyOut.length; arrMoneyOut[i++]=0);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arrMoney.length; i++){
			if(arrMoneyOut[i] != 0){
				sb.append(arrNameMoney[i]+" "+arrMoneyOut[i]+", ");
			}
		}
		sb.append("\n");
		return sb.toString();
		
	}
	
    private void setProp(){
    	// set the properties value
    	Properties prop = new Properties();
    	for(int i = 0; i < arrMoney.length; i++){
    		prop.setProperty(arrNameMoney[i], new Integer(arrMoney[i]).toString());
    	}

		try (OutputStream output = new FileOutputStream("config.properties")){
    		
    		
    		// save properties to project root folder
			
    		prop.store(output, null);
    		prop.list(System.out);
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private void getProp(){
    	Properties prop = new Properties();

    	try (InputStream  input = new FileInputStream("config.properties")){
    		// load a properties file
    		prop.load(input);
//    		prop.list(System.out);

    		for(int i = 0; i < arrMoney.length; i++){
    			arrMoney[i] = Integer.parseInt(prop.getProperty(arrNameMoney[i]));
    		}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	 			
	}
    // check input sum in mashine
	private boolean checkSum(double sum){
		int totalSum1 = 0;
		int totalSum2 = 0;
		for(int i = 0; i < constMoney.length; i++){
			if(i < constMoney.length - 6)
				totalSum1 += (constMoney[i]*arrMoney[i]);
			else{
				totalSum2 += (constMoney[i]*arrMoney[i]);
			}
			
		}
		return (int)(sum*100) < (int)(totalSum1*100 + totalSum2);
	}
    public void calculateSum(double d){
    	
		// get the property value and print it out
		double sum = d;
		getProp();
		
		for(int i = 0; i < arrMoney.length; i++){
			if(i == arrMoney.length-6)
				d *= 100;
			while(d-constMoney[i] > 0)
			{
				if(arrMoney[i] == 0)
					continue;
				d -= constMoney[i];
				arrMoney[i]--;
				arrMoneyOut[i]++;
				
			}
		}
		setProp();
    }
    
}

