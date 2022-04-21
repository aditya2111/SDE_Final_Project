
package cpjitsdpexperiment;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import moa.options.ClassOption;
import moa.tasks.EvaluatePrequential;
import moa.tasks.MainTask;
import moa.tasks.TaskThread;

public class ExpOPAIO {

	static MainTask currentTask = new EvaluatePrequential();
	static Writer writer;

	public ExpOPAIO() {

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
		/*** Use only for ORB ***/
		String paramsORB=args[5];

		/*** Example parameter values ***/
		//		0
		//		0
		//		20
		//		0.99
		//		90
		//		100;0.4;10;12;1.5;3


		String[] datasetsArray = {"tomcat","JGroups","spring-integration",
				"camel","brackets","nova","fabric8",
				"neutron","npm","BroadleafCommerce"
		};

		String paramStr = ens+"-"+theta;

		/*** OOB ***/
		//		String task = "CpjitsdpOPAIO  -l (meta.ggc2.meta.WaitForLabelsOOB -i 15 -s "+ens+" -t "+theta+" -w "+waitingTime+")  -s  (ArffFileStream -f (datasets/"+datasetsArray[dsIdx]+".arff) -c 15) -e (FadingFactorEachClassPerformanceEvaluator -a 0.99) -f 1 -d results/"+datasetsArray[dsIdx]+"-OP-AIO-OOB-"+arrId+".csv";

		/*** ORB ***/
		String task = "CpjitsdpOPAIO  -l (spdisc.meta.WFL_ORB -i 15 -s "+ens+" -t "+theta+" -w "+waitingTime+" -p "+paramsORB+")  -s  (ArffFileStream -f (datasets/"+datasetsArray[dsIdx]+".arff) -c 15) -e (FadingFactorEachClassPerformanceEvaluator -a 0.99) -f 1 -d results/"+datasetsArray[dsIdx]+"("+paramsORB.replaceAll(";", "-")+")-"+paramStr+"-OP-AIO-ORB-"+arrId+".csv";


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
