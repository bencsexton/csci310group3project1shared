package csci310.servlet.activity;

public class Activities {
	private static String filename = "activities.txt";
	
	public static List<String> getActivityList(){
		System.out.println("pwd: " + System.getProperty("user.dir"));
		File file = new File(filename); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String st = "";
		List<String> activityList = new ArrayList<String>();
		br.readLine(); // hot:
		while (!st.equals("temperate:")) {
			st = br.readLine();
			activityList.add(st);
		}
		br.readLine();
		while (!st.equals("cold:")) {
			st = br.readLine();
			activityList.add(st);
		}
		br.readLine();
		while ((st = readLine()) != null) {
			activityList.add(st);
		}
		return activityList;
	}
}
