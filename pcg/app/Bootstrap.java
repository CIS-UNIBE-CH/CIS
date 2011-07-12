import java.io.File;

import play.jobs.Every;
import play.jobs.Job;

@Every("1h")
public class Bootstrap extends Job {

    public void doJob() {
	File directory = new File("pcg/public/images/graphs/");
	File[] files = directory.listFiles();
	for (File file : files) {
	    if (!file.delete()) {
		System.out.println("Failed to delete " + file);
	    }
	}
    }
}
