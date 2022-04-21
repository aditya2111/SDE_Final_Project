

package cpjitsdpexperiment;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import moa.options.ClassOption;
import moa.tasks.EvaluatePrequential;
import moa.tasks.MainTask;
import moa.tasks.TaskThread;

public class ExpOPFilter {

	static MainTask currentTask = new EvaluatePrequential();
	static Writer writer;

	public ExpOPFilter() {

	}

private static String[] savedArgs;
	 
public static String[] getArgs() {
	     return savedArgs;
	    }
	public static void main(String[] args) throws IOException, InterruptedException {
		
		savedArgs = args;
		
		int dsIdx = new Integer(args[0]);
		int arrId = new Integer(args[1]);
		String ens =  args[2];
		String theta = args[3];
		String waitingTime= args[4];
		String filterParams = args[5];
		/*** Use only for ORB ***/
		String paramsORB=args[6];
		
		
		/*** Example parameter values ***/
//				0
//				0
//				20
//				0.99
//				90
//				500;50;0.7;500
//				100;0.4;10;12;1.5;3
		
	
		String[] datasetsArray = {"tomcat","JGroups","spring-integration",
				"camel","brackets","nova","fabric8",
				"neutron","npm","BroadleafCommerce"
				};
		
		 String paramStr = ens+"-"+theta;
		
		 /*** OOB ***/
//		 String task = "EvaluatePrequentialOfflineCPFilter  -l (meta.ggc2.meta.WaitForLabelsOOB -i 15 -s "+ens+" -t "+theta+" -w "+waitingTime+")  -s  (ArffFileStream -f (datasets/"+datasetsArray[dsIdx]+".arff) -c 15) -e (FadingFactorEachClassPerformanceEvaluator -a 0.99) -f 1 -d results/"+datasetsArray[dsIdx]+"-"+filterParams.replaceAll(";", "-")+"-OP-Filter-OOB-"+arrId+".csv";
		 
		 /*** ORB ***/
		 String task = "EvaluatePrequentialOfflineCPFilter -l (spdisc.meta.WFL_OO_ORB_Oza -i 15 -s "+ens+" -t "+theta+" -w "+waitingTime+" -p "+paramsORB+")  -s  (ArffFileStream -f (datasets/"+datasetsArray[dsIdx]+".arff) -c 15) -e (FadingFactorEachClassPerformanceEvaluator -a 0.99) -f 1 -d results/ORB/"+datasetsArray[dsIdx]+"-("+paramsORB.replaceAll(";", "-")+")-"+filterParams.replaceAll(";", "-")+"-OP-Filter-ORB-"+arrId+".csv";

		try {

			System.out.println(task);
			currentTask = (MainTask) ClassOption.cliStringToObject(task, MainTask.class, null);

			TaskThread thread = new TaskThread((moa.tasks.Task) currentTask);

			thread.start();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
