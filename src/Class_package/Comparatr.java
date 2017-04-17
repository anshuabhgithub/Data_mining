package Class_package;

import java.util.Comparator;

public class Comparatr implements Comparator<Word> {

	public int compare(Word w1 , Word w2)
	{
		
		return w1.starting_point -w2.starting_point;
	}
	
}
