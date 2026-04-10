//TID: T00521651
public class SimProcess {
    protected int pid;
    private String procName;
    private int totalInstructions;
    
    public SimProcess(int pid, String procName, int totalInstructions) {
        this.pid = pid;
        this.procName = procName;
        this.totalInstructions = totalInstructions;
    }
    
    public ProcessState execute(int instructionNumber, int step) {
        System.out.printf("Step %d Proc %s, PID: %d executing instruction: %d%n", step, procName, pid, instructionNumber);
        
        if (instructionNumber >= totalInstructions) { //process already did this instruction..
            return ProcessState.FINISHED;
        } else if (Math.random() < 0.15) {  //process blocks
            return ProcessState.BLOCKED;
        } else {
            return ProcessState.READY;
        }
    }
    
}