//TID: T00521651
public class ProcessControlBlock {
    private SimProcess process;
    private int currentInstruction;
    private int[] registers;
    
    public ProcessControlBlock(SimProcess process) {
        this.process = process;
        this.currentInstruction = 0;
        this.registers = new int[4];
    }
    
    public SimProcess getProcess() {
        return process;
    }
    
    public int getCurrentInstruction() {
        return currentInstruction;
    }
    
    public void setCurrentInstruction(int i) {
        this.currentInstruction = i;
    }
    
    public int[] getRegisters() {
        return registers.clone();
    }

    public void setRegisters(int[] regs) {
        this.registers = regs.clone();
    }
}