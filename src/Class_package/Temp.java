package Class_package;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp {
	
	
	public static void main(String args[]) throws IOException
	{
		String date_reg_exp= "(\\d{1}/\\d{1}/\\d{4})";
		//|(\\Ad{2}/\\d{2}/\\d{4})|(\\Ad{2}/\\d{1}/\\d{4})|(\\Ad{1}/\\d{2}/\\d{4})|(\\A/\\d{2}/\\d{4})|(\\A/\\d{1}/\\d{4})";
		//String data = "1\\10\\2105  this  me this is not kthismemeis";
		Vector<String> data= new Vector();
		
		try{
			FileInputStream in_stream = new FileInputStream("input.txt");
			InputStreamReader reader = new InputStreamReader(in_stream);
			BufferedReader rd= new BufferedReader(reader);
			String line;
			while((line = rd.readLine())!= null)
			{
				data.add(line);
			}
			String strg = data.elementAt(8);
			String[] temp_data = strg.split("\\.");
			//System.out.println("the leanght of data is" + strg );
		}finally
		{
			
		}
		Pattern pt = Pattern.compile(date_reg_exp);
		Matcher m = pt.matcher(data.elementAt(15));
		int count =1;
		System.out.println(data.elementAt(15)+"\n");
		
		while(m.find())
		{
			int str = m.start();
			int end = m.end();
			
			System.out.print(count+"\n");
			count++;
			System.out.print(data.elementAt(15).substring(str, end));
		}
		
		
	}
}
