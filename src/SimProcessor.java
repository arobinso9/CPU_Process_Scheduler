//TID: T00521651
import java.util.Random;

public class SimProcessor {
    private SimProcess currentProcess;
    private int currInstruction;
    private int[] registers;
    
    public SimProcessor() {
        this.registers = new int[4]; //our 4 registers
    }
    
    public void setCurrentProcess(SimProcess p) {
        this.currentProcess = p;
    }
    
    public SimProcess getCurrentProcess() {
        return currentProcess;
    }
    
    public int getCurrInstruction() {
        return currInstruction;
    }
    
    public void setCurrInstruction(int i) {
        this.currInstruction = i;
    }
    
    public void setRegisterValue(int index, int value) {
        this.registers[index] = value;
    }
    
    public int[] getRegisterValues() {
        return registers.clone();
    }
    
    public ProcessState executeNextInstruction(int step) {
        ProcessState result = currentProcess.execute(currInstruction, step);
        currInstruction++;

        //sets all 4 registers to random values to simulate execution
        Random rand = new Random();
        for (int i = 0; i < registers.length; i++) {
            registers[i] = rand.nextInt();
        }
        
        return result;
    }
    
    public boolean isIdle() {
        return currentProcess == null;
    }
}