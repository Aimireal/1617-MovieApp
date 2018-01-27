


import java.util.Scanner;
import java.io.*;

public class DrinkInfo{

	public static String[] getDrinkNames() throws IOException {
		int count=0;

		final int SIZE = 21;

		String[] drinks = new String[SIZE];

		File myFile = new File("DrinkPrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < drinks.length)
		{
			String str;

			str= inputFile.nextLine();

			String[] parts = str.split(",");
			drinks[count]=parts[0];
		 	count++;
		}
		inputFile.close();

		return drinks;
	}
	public static double[] getDrinkPrices() throws IOException{
		int count=0;
		final int SIZE = 21;
		double[] prices = new double[SIZE];

		File myFile = new File("DrinkPrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < prices.length)
		{
			String str;

			str = inputFile.nextLine();
			String[] parts = str.split(",");

		 	prices[count]= Double.parseDouble(parts[1]) ;
		 	count++;
		}
		inputFile.close();

		return prices;
	}

}