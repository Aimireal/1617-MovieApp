


import java.util.Scanner;
import java.io.*;

public class SnackInfo{

	public static String[] getSnackNames() throws IOException {
		int count=0;

		final int SIZE = 21;

		String[] snacks = new String[SIZE];

		File myFile = new File("SnackPrices.txt");
		Scanner inputFile = new Scanner(myFile);

		while(inputFile.hasNext() && count < snacks.length)
		{
			String str;

			str= inputFile.nextLine();

			String[] parts = str.split(",");
			snacks[count]=parts[0];
		 	count++;
		}
		inputFile.close();

		return snacks;
	}
	public static double[] getSnackPrices() throws IOException{
		int count=0;
		final int SIZE = 21;
		double[] prices = new double[SIZE];

		File myFile = new File("SnackPrices.txt");
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