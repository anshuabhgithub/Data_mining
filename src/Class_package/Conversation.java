package Class_package;
import Data.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;


public class Conversation {

public Vector<String> data = new Vector();

Vector<Clean_Conversation> cl_data = new Vector();

Map reg_exp = new HashMap();

String user;
String date_reg_exp= "(/\\d{2}/\\d{4})|(/\\d{1}/\\d{4})";
Vector<String> reg_exp_key = new Vector();


public Vector<String> find_item(String str,String type)
{
	Vector<String> temp_data =new Vector();
	Pattern pt = (Pattern)reg_exp.get(type);
	Matcher mt =pt.matcher(str);
	
	while(mt.find())
	{
		int strt = mt.start();
		int end = mt.end();
		temp_data.add(str.substring(strt,end));
		
	}
		
	return temp_data;
	
	
}
public Conversation()
{	
}
// create regular expression
public void create_reg_exp() throws IOException
{
	Vector<String> reg_data = new Vector() ;
	FileInputStream file = null;
	reg_data.add("Item_name");
	reg_data.add("Item_number");
	reg_data.add("Item_unit");
	reg_exp_key.add("Item_name");
	reg_exp_key.add("Item_number");
	reg_exp_key.add("Item_unit");
	try{
		for(int i =0 ;i<reg_data.size();i++)
		{
		file = new FileInputStream("./src/Data/"+reg_data.elementAt(i));
		InputStreamReader rd = new InputStreamReader(file);
		BufferedReader bf = new BufferedReader(rd);
		reg_data.set(i,bf.readLine());
		bf.close();
		}
		}
	finally{
	
			if(file!=null)
				file.close();
			}
	Vector<String[]> reg_list = new Vector(); 
	for(int i =0; i<reg_data.size();i++)
	{
		reg_list.add(reg_data.elementAt(i).split(","));
		String temp =reg_list.elementAt(i)[0];
		//reg_data.set(i,reg_list.elementAt(i)[0]);
		for(int j =1;j<reg_list.elementAt(i).length;j++)
			temp = temp+"|"+reg_list.elementAt(i)[j];
		//System.out.println(x);
		Pattern pt = Pattern.compile(temp);
		reg_exp.put(reg_exp_key.elementAt(i), pt);
		//System.out.println(reg_data.elementAt(i));
	}
	

}



// Read data for file

public void  read_data(BufferedReader br) throws IOException
{	
	String line;
	String data_reg = "(\\d{1}/\\d{1}/\\d{4})|(\\d{2}/\\d{2}/\\d{4})|(\\d{2}/\\d{1}/\\d{4})|(\\d{1}/\\d{2}/\\d{4})|(/\\d{2}/\\d{4})|(/\\d{1}/\\d{4})";
	Pattern pt = Pattern.compile(data_reg);
	List<String> temp_data = new LinkedList();
	while((line = br.readLine())!= null)
	{
		data.add(line);
	}
	for(int i =0;i<data.size();i++)
	{
		Matcher mt = pt.matcher(data.elementAt(i));

		if(!mt.find())
		{
			System.out.println(data.elementAt(i));
			temp_data.set(temp_data.size()-1,temp_data.get(temp_data.size()-1)+data.elementAt(i));
		}
		else
			temp_data.add(data.elementAt(i));
	}
	data.setSize(0);
	for(int i =0;i<temp_data.size();i++)
	{
		data.add(temp_data.get(i));
		
	}
}
public void data_clean()
{
	int i =0;
	String[] temp;
		temp = data.elementAt(i).split(":");
		System.out.println(data.elementAt(i)+"ddata temp[0]");
	while(temp[1].contains("Host"))
	{
	i++;
	temp = data.elementAt(i).split(":");
	}
	//System.out.print(temp[1]);
	this.user= temp[1].substring(temp[1].indexOf("- ")+1);
	System.out.println(this.user);
	String temp_str;
	Pattern pt = Pattern.compile(date_reg_exp);
	Matcher m = pt.matcher(data.elementAt(0));
	
	m.find();
	System.out.println(data.elementAt(0));
	String temp_date = data.elementAt(0).substring(m.start(),m.end());
	
	cl_data.add(new Clean_Conversation());
	cl_data.elementAt(0).date = temp_date;
	Clean_Conversation temp_cl_conv = cl_data.elementAt(0);
	System.out.println("\n the size of data is  "+ data.size());
	for(int j=0;j<data.size();j++)
	{
		//Pattern pt = Pattern.compile(date_reg_exp);
		temp_str = data.elementAt(j);
		Matcher mt = pt.matcher(data.elementAt(j));
		mt.find();
		System.out.println(data.elementAt(j));
		String Date = data.elementAt(j).substring(mt.start(),mt.end()); 
		System.out.println(Date+"\n");
		if(Date.equals(temp_date))
		{
			if(temp_str.contains("Host"))
				{
				temp_cl_conv.data.addElement("H:"+temp_str.substring(temp_str.indexOf("Host")+"Host".length()));
				}
			else
			{
				temp_cl_conv.data.addElement("U:"+temp_str.substring(temp_str.indexOf(user)+user.length()));
			}
		}
		else
		{
			temp_date = Date;
			cl_data.add(new Clean_Conversation());
			temp_cl_conv = cl_data.lastElement();
			temp_cl_conv.date = Date;
			if(temp_str.contains("Host"))
			{
			temp_cl_conv.data.addElement("H:"+temp_str.substring(temp_str.indexOf("Host")+"Host".length()));
			}
		else
		{
			temp_cl_conv.data.addElement("U:"+temp_str.substring(temp_str.indexOf(user)+user.length()));
		}
				
		}
		
	}
	
}
//test method
public void check()
{	
	String str ="1kg potatoes, 1 kg onions, 1 kg tomato, half kg beans, 250 gm capsicum, 1 cauliflower, 1 kg bhindi, half kg parwal, 6 elaichi bananas";
	System.out.println(str);
	Vector<Word> word;
	word = create_list(str);
	Vector<Order> order ;
	order = create_order(word);
	
	
	//for(int i =0 ;i<order.size();i++)
	//{
		//System.out.println("the"+i + "order is " + order.elementAt(i).item + " " + order.elementAt(i).quantity + " " + order.elementAt(i).unit +"\n");
		
	//}
	/*for(int i =0; i <cl_data.size();i++)
	{
		System.out.println("the date is " + cl_data.elementAt(i).date+"\n");
		for(int j=0;j<cl_data.elementAt(i).data.size();j++)
			System.out.println(cl_data.elementAt(i).data.elementAt(j)+"\n");
		
	}*/
	
}


public Vector<Word> create_list(String str)
{
		Vector<Word> word = new Vector();
	
	
	for(int i =0;i<reg_exp_key.size();i++)
	{
		Pattern  pt = (Pattern) reg_exp.get(reg_exp_key.elementAt(i));
		Matcher mt = pt.matcher(str);
		while(mt.find())
		{
			int start = mt.start();
			int end = mt.end();
			word.add(new Word());
			word.lastElement().starting_point=start;
			word.lastElement().end_point=end;
			word.lastElement().word = str.substring(start,end);
			word.lastElement().word_type = reg_exp_key.elementAt(i);
			
		}
		
	}
	Comparatr comparator = new Comparatr();
	Collections.sort(word,comparator);
	/*System.out.println("\n");
	for(int j =0;j<word.size();j++)
	{
		//System.out.println(word.elementAt(j).starting_point+"\n");
		System.out.println(word.elementAt(j).word+"\n");	
	}*/
	return word;
}

public Vector<Order> order_from_str(String str)
{
	Vector<Order> order ;
	Vector<Word>  word= create_list(str);
	order = create_order(word);
	return order;
}

public Vector<Order> create_order(Vector<Word> word)
{
	System.out.println("into create order");
	Vector<Order> order =new Vector();
	order.add(new Order());
	for(int i =0;i<word.size();i++)
	{
		System.out.print(word.elementAt(i).word+"\n");
		if(order.lastElement().item == null)
		{
			if(word.elementAt(i).word_type.equals("Item_unit"))
				order.lastElement().unit = word.elementAt(i).word;
			if(word.elementAt(i).word_type.equals("Item_name"))
			{
						order.lastElement().item = word.elementAt(i).word;
						if(order.lastElement().quantity !=null)
							order.add(new Order());
			}
			if(word.elementAt(i).word_type.equals("Item_number"))
				order.lastElement().quantity = word.elementAt(i).word;
		}
		else
		{
			if(word.elementAt(i).word_type.equals("Item_unit"))
				order.lastElement().unit = word.elementAt(i).word;
			if(word.elementAt(i).word_type.equals("Item_number"))
				order.lastElement().quantity = word.elementAt(i).word;
			if(word.elementAt(i).word_type.equals("Item_name"))
			{
				order.add(new Order());
				order.lastElement().item = word.elementAt(i).word;
				
			}
		}
		
	}
	if(order.lastElement().item== null)
		order.removeElementAt(order.size()-1);
	return order;
	
}

public void read_conversation()
{	
	Pattern send_pt = Pattern.compile("send|add");
	Pattern rate_pt = Pattern.compile("(rate of)|(rateof)");
	for(int con =0 ;con<cl_data.size();con++)
	{
		Clean_Conversation temp_con = cl_data.elementAt(con);
		Order rate_order = null;
		boolean rate_enq = false;
		for(int line =0 ;line<temp_con.data.size();line++)
		{
			String temp_str = temp_con.data.elementAt(line);
			
			String user_type = temp_str.substring(0,"U".length());

			temp_str = temp_str.substring("U:: ".length());

			String[] sentence = temp_str.split("\\.");
			
			boolean rate_f,send_f;
			
			for(int sen = 0;sen<sentence.length;sen++)
			{
				String sent = sentence[sen];
				Matcher send_mt = send_pt.matcher(sent);
				Matcher rate_mt = rate_pt.matcher(sent);
				if(send_mt.find())
					send_f= true;
				else
					send_f = false;
				if(rate_mt.find())
					rate_f = true;
				else
					rate_f = false;
				
			if(user_type.equals("U"))
			{
				//TODO implement
				Vector<String> temp_item = find_item(temp_str,"Item_name");
				String[] item_quan, item_unit;
				if(rate_f)
					
				{
				rate_order = new Order();
				rate_enq =true;
				if(temp_item.size()>0)
				{
					rate_order.item  = temp_item.elementAt(0);
				}
					
				}
				else if(send_f)
				{
					System.out.println("the item has to be sent");
					if(rate_enq)
					{
						if((temp_item.size()==0))
						{
							System.out.println("inside if size ==0");
						if(temp_item.elementAt(0).equals(rate_order.item))
						{
							Order quan_order = get_quant(sent);	
							rate_order.unit = quan_order.unit;
							rate_order.quantity = quan_order.quantity;
							rate_f= false;
							temp_con.orders.add(rate_order);
						}
						}
					}
					if((temp_item.size()>0))
					{
						rate_f = false;
						Vector<Order> temp_order = order_from_str(sent);
						append_order(temp_con.orders,temp_order);
						
					}
					
				}
				else if(temp_item.size()>0)
				{
					Vector<Order> temp_order = order_from_str(sent);
					append_order(temp_con.orders,temp_order);
				}
				
			}
			if(user_type.equals("H"))
			{
				
			}
			
		}
		}
	}
	
	System.out.println(cl_data.size());
	
	for(int i =0;i<cl_data.size();i++)
	{
		System.out.println("the order for " + i + "th conversation is \n");
		for(int j =0;j<cl_data.elementAt(i).orders.size();j++)
			System.out.println("the order is " + cl_data.elementAt(i).orders.elementAt(j).quantity + cl_data.elementAt(i).orders.elementAt(j).unit+cl_data.elementAt(i).orders.elementAt(j).item);
	}
	
}
public Order get_quant(String str)
	{
	Vector<Word> word = create_list(str);
	Order order  = new Order();
	String quan="", unit="";
		for(int i =0;i<word.size();i++)
		{
			if(word.elementAt(i).word_type.equals("Item_number"))
				quan = word.elementAt(i).word;
			if(word.elementAt(i).word_type.equals("Item_unit"))
				{
				unit = word.elementAt(i).word;
				 break;
				}
		}
		if(!unit.equals(""))
			order.unit= unit;
		if(!quan.equals(""))
			order.quantity = quan;
		return order;
		
	}
public Vector<Order> unit_order(String str)
{
	Vector<Order> order = new Vector();
	String[] temp_str = str.split(",");
	Vector<Order> temp_order;
	for(int i =0;i<temp_str.length;i++)
	{
		temp_order=order_from_str(temp_str[i]);
		for(int j =0;j<temp_order.size();j++)
		{
			order.add(temp_order.elementAt(j));
			
		}
		
	}
	return order;

}
public void append_order(Vector<Order> o1,Vector<Order> o2)
{
	for(int i =0 ; i<o2.size();i++)
		o1.add(o2.elementAt(i));
	}
public void  print_data()
{
	int size = data.size();
		for(int i=0;i<size;i++)
			System.out.println(data.elementAt(i));
}
}

