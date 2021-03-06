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
 * @author Andrey
 *
 */

public class CoinsMashine {

	private final int BEGINOFMONETSUAH = 6;
	private final int BEGINOFMONETS$ = 5;
	private final int BEGINOFCOINSUAH = 0;
	private final int BEGINOFCOINSEUR = 0;
	private final int BEGINOFCOINS$$$ = 2;
	private int beginOfMonets = 0;
	private int beginOfCoins = 0;

	
	// an array of the number of coins of each denomination
	private int[] arrMoney = { 2, 3, 7, 15, 24, 38, 84, 29, 33, 22, 74, 91, 32,
			40, 8 };
	// array of coin denominations
	private final int[] constMoneyUAH = { 500, 200, 100, 50, 20, 10, 5, 2, 1, 50,
			25, 10, 5, 2, 1 };
	private final int[] constMoneyEUR = { 500, 200, 100, 50, 20, 10, 5, 2, 1, 50,
			20, 10, 5, 2, 1 };
	private final int[] constMoney$$$ = { 500, 200, 100, 50, 20, 10, 5, 2, 1, 50,
			20, 10, 5, 1 };
	private int[] constMoney;
	// number of coins for the issuance
	private int[] arrMoneyOut = new int[15];
	//
	private final String[] arrNameMoneyUAH = { "UAH 500", "UAH 200", "UAH 100",
			"UAH 50", "UAH 20", "UAH 10", "UAH 5", "UAH 2", "UAH 1",
			"UAH 0.50", "UAH 0.25", "UAH 0.10", "UAH 0.05", "UAH 0.02",
			"UAH 0.01" };
	private String[] arrNameMoney;
	private final String[] arrNameMoneyEUR = { "EUR 500", "EUR 200", "EUR 100",
			"EUR 50", "EUR 20", "EUR 10", "EUR 5", "EUR 2", "EUR 1",
			"EUR 0.50", "EUR 0.20", "EUR 0.10", "EUR 0.05", "EUR 0.02",
			"EUR 0.01" };
	private final String[] arrNameMoney$$$ = { "$500", "$200", "$100",
			"$50", "$20", "$10", "$5", "$2", "$1",
			"$0.50", "$0.20", "$0.10", "$0.05", "$0.01" };
	public CoinsMashine() {
		super();
		// TODO Auto-generated constructor stub
		
		
	}

	public static void main(String[] args) {
		double num = 0.0;
		String inputLine;	// selector UAH - EURO
		boolean ext = true;	// trigger for run/exit
		CoinsMashine cm = new CoinsMashine();
		cm.setProp();		// initial preferences
		Scanner scanner = new Scanner(System.in);
		while (ext) {
			cm.clear();
			
			System.out.println("Type the sum UAH or EURO in format: XXX,xx ZZZ");
			System.out.println("sample: 123,54 UAH  or 123,54 EUR or 123,54 $");
			System.out.println("for quit press any symbol and press Enter");
			// input sum for calculate
			try {
				num = scanner.nextDouble();
				inputLine = scanner.next();
			} catch (InputMismatchException ime) {
				ext = false;	// moment exit from program
				continue;
			}
			System.out.println("input "+num);
			
			if(!cm.selectStrategy(inputLine))
				continue;
			
			// get the property value and print it out
			cm.getProp();
			if (cm.checkSum(num) == false) {
				System.out.println("money is tight in mashine");
				continue;
			}
			cm.calculateSum(num);
			System.out.println(cm);

		}
		scanner.close();
		System.out.println("Exited");
	}
	/**
	 * method select UAH or EURO denomination
	 * @param money TODO
	 * @return TODO
	 */
	public boolean selectStrategy(String money){
		switch (money) {
		case "UAH":
			arrNameMoney = arrNameMoneyUAH;
			constMoney = constMoneyUAH;
			beginOfMonets = BEGINOFMONETSUAH;
			beginOfCoins = BEGINOFCOINSUAH;
			return true;

		case "EUR":
			arrNameMoney = arrNameMoneyEUR;
			constMoney = constMoneyEUR;
			beginOfMonets = BEGINOFMONETSUAH;
			beginOfCoins = BEGINOFCOINSEUR;
			return true;
			
		case "$":
			arrNameMoney = arrNameMoney$$$;
			constMoney = constMoney$$$;
			beginOfMonets = BEGINOFMONETS$;
			beginOfCoins = BEGINOFCOINS$$$;
			return true;
		default:
			System.out.println("Error' type currency");
			return false;

		}
		
	}
	/**
	 * 
	 */
	private void clear() {
		// TODO Auto-generated method stub
		for (int i = 0; i < arrMoneyOut.length; arrMoneyOut[i++] = 0)
			;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = beginOfCoins; i < constMoney.length; i++) {
			if (arrMoneyOut[i] != 0) {
				sb.append(arrNameMoney[i] + "=" + arrMoneyOut[i] + ", ");
			}
		}
		sb.append("\n");
		return sb.toString();

	}
	/**
	 * set initial values for coins denomination
	 */
	private void setProp(){
		Properties prop = new Properties();
		if(selectStrategy("UAH"))
			fillProp(prop);
		if(selectStrategy("EUR"))
			fillProp(prop);
		if(selectStrategy("$"))
			fillProp(prop);
		flushPropToFile(prop);
		
//		prop.list(System.out);
		
	}
	/**
	 * save properties to file "config.properties"
	 */
	private void saveProp() {
		// set the properties value
		Properties prop = new Properties();
		fillProp(prop);
//		prop.list(System.out);
		flushPropToFile(prop);
	}

	/**
	 * @param prop
	 */
	private void flushPropToFile(Properties prop) {
		try (OutputStream output = new FileOutputStream("config.properties")) {

			// save properties to project root folder

			prop.store(output, null);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param prop
	 */
	private void fillProp(Properties prop) {
		selectStrategy("UAH");		
		saveCurrencyProp(prop);
		selectStrategy("EUR");		
		saveCurrencyProp(prop);
		selectStrategy("$");		
		saveCurrencyProp(prop);
	}

	/**
	 * @param prop
	 */
	private void saveCurrencyProp(Properties prop) {
		for (int i = beginOfCoins; i < constMoney.length; i++) {
			prop.setProperty(arrNameMoney[i],
					new Integer(arrMoney[i]).toString());
		}
	}
	/**
	 * read properties from file "config.properties"
	 */
	private void getProp() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream("config.properties")) {
			// load a properties file
			prop.load(input);
//			 prop.list(System.out);
			selectStrategy("UAH");
			loadCurrencyProp(prop);
			selectStrategy("EUR");
			loadCurrencyProp(prop);
			selectStrategy("$");
			loadCurrencyProp(prop);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param prop
	 */
	private void loadCurrencyProp(Properties prop) {
		for (int i = beginOfCoins; i < constMoney.length; i++) {
			arrMoney[i] = Integer.parseInt(prop
					.getProperty(arrNameMoney[i]));
		}
	}

	/**
	 * check input sum in mashine
	 * @param sum - entered sum
	 * @return if sum in mashine > input query sum
	 */
	boolean checkSum(double sum) {
		int totalSum1 = 0;
		int totalSum2 = 0;
		for (int i = beginOfCoins; i < constMoney.length; i++) {
			if (i < constMoney.length - beginOfMonets)
				totalSum1 += (constMoney[i] * arrMoney[i]);
			else {
				totalSum2 += (constMoney[i] * arrMoney[i]);
			}

		}
		return (int) (sum * 100) < (int) (totalSum1 * 100 + totalSum2);
	}
	/**
	 * 
	 * @param d - 
	 */
	public void calculateSum(double d) {
		

		for (int i = beginOfCoins; i < constMoney.length; i++) {
			if (i == constMoney.length - beginOfMonets)
				d = (d+0.005)*100;
			if(arrMoney[i] != 0)
			while (d - constMoney[i] >= 0) {
				if (arrMoney[i] == 0)
					break;
				d -= constMoney[i];
				arrMoney[i]--;
				arrMoneyOut[i]++;

			}
		}
		saveProp();
	}

}
