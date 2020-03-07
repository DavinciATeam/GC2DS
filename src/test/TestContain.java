package test;

public class TestContain {

	public static void main(String[] args) {
		String text = "我從山東出發";
		if(text.contains("從")||text.contains("出發")){
			System.out.println("success!");
		}else{
			System.out.println("fail!");
		}
	}

}
