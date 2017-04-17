package Class_package;





public class Word implements Comparable<Word> {

	public String word;
	public String word_type;
	public int starting_point;
	public int end_point ;
	@Override
	public int compareTo(Word o) {
		
		return this.starting_point - o.starting_point;
	}
}
