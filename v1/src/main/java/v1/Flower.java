package v1;

import java.util.ArrayList;
import java.util.List;

public class Flower {
	
	private List<Float> params = new ArrayList<Float>();
	private String name;
	
	public Flower(float S_len, float S_wid, float P_len, float P_wid, String Name) {
		this.params.add(S_len);
		this.params.add(S_wid);
		this.params.add(P_len);
		this.params.add(P_wid);
		this.name = Name;
		params.toArray();
	}
	
	public void Print() {
		params.toArray();
		String temp = "Name: %s; Sepal length: %f; Sepal width: %f; Petal length: %f; Petal width: %f.\n";
		String out = String.format(temp, name, params.get(0), params.get(1), params.get(2), params.get(3));
		System.out.print(out);
	}
	
	public String getName() {
		return name;
	}
	
	public float getParameter(int i) {
		return params.get(i);
	}
	

	
}
