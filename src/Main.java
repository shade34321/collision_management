public class Main {
    public static void main(String[] args) {
        /* Call Management Class like this */
        RealTime RT = RealTime.getInstance();
       /* Start Threads */
        RT.startProcesses();
        /*Continue with main thread*/
    }

}
