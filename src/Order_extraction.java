import Class_package.*;
import java.io.*;
public class Order_extraction {

//Conversation conv = new Conversation();


public  static void main(String[] args) throws IOException{

	Conversation conv = new Conversation();
	FileInputStream in_stream = null;
	try{
	in_stream= new FileInputStream("input.txt");
	InputStreamReader reader = new InputStreamReader(in_stream);
	BufferedReader rd= new BufferedReader(reader);
	conv.read_data(rd);
	conv.create_reg_exp();
	//conv.create_order(14);
	conv.data_clean();
	conv.read_conversation();
	//conv.check();
	
	//conv.print_data();
	}
	finally{
		if(in_stream != null)
			in_stream.close();
		}
}
	
}
