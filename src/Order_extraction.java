import Class_package.*;
import java.io.*;
public class Order_extraction {

//Conversation conv = new Conversation();


public  static void main(String[] args) throws IOException{

	
	FileInputStream in_stream = null;
	for(int i =2;i<46;i++)
	{
		Conversation conv = new Conversation();
		System.out.print("Sanitized"+i+".txt");
	try{
	in_stream= new FileInputStream("Sanitized2.txt");
	InputStreamReader reader = new InputStreamReader(in_stream);
	BufferedReader rd= new BufferedReader(reader);
	conv.read_data(rd);
	conv.create_reg_exp();
	conv.data_clean();
	conv.read_conversation();
	}
	finally{
		if(in_stream != null)
			in_stream.close();
		}
	}
}
	
}
