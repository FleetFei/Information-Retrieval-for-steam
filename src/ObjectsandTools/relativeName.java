package ObjectsandTools;

public class relativeName {
	
	public String appid;
	public double score;
	public String name;
	public double rating;
	public double releasedate;
	public String tags;
	
	public relativeName(int score, String name) {
		this.score = score;
		this.name = name;
		this.rating = 0.0;
	}
	
	public relativeName(int score, String name, int rating) {
		this.score = score;
		this.name = name;
		this.rating = rating;
	}
	
	public relativeName(String appid,int score, String name, int rating, String releasedate, String tags) {
		this.appid = appid;
		this.score = score;
		this.name = name;
		this.rating = rating;
		this.releasedate = Integer.parseInt(releasedate);
		this.tags = tags;
	}
	
}
