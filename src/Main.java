//TID: T00521651
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {
    private static final int QUANTUM = 5;
    private static final int MAIN_LOOP_ITERATIONS = 3000;
    private static final double UNBLOCK_PROB = 0.3;
    
    public static void main(String[] args) {
        Random rand = new Random(42);

        SimProcess[] processes = new SimProcess[10];
        ProcessControlBlock[] pcbs = new ProcessControlBlock[10];
        
        int[] instructionCounts = {150, 200, 175, 300, 100, 250, 180, 350, 225, 400};
        
        for (int i = 0; i < 10; i++) {
            processes[i] = new SimProcess(i + 1, "Process" + (i + 1), instructionCounts[i]);
            pcbs[i] = new ProcessControlBlock(processes[i]);
            pcbs[i].setCurrentInstruction(0);
            pcbs[i].setRegisters(new int[4]);
        }

        //adding each pcb to the ready queue
        Queue<ProcessControlBlock> readyQueue = new LinkedList<>();
        for (ProcessControlBlock pcb : pcbs) {
            readyQueue.add(pcb);
        }

        LinkedList<ProcessControlBlock> blockedList = new LinkedList<>();
        
        SimProcessor processor = new SimProcessor();
        int quantumCounter = 0;
        int finishedCount = 0;
        ProcessControlBlock currentPCB = null;

        for (int step = 1; step <= MAIN_LOOP_ITERATIONS; step++) {

            // If processor has no current process
            if (processor.isIdle()) {
                //if there is a pcb on the ready queue...
                if (!readyQueue.isEmpty()) {
                    currentPCB = readyQueue.poll();
                    restoreProcessContext(processor, currentPCB, step);
                    quantumCounter = 0;
                } else {
                    System.out.printf("Step %d Processor idling (no ready processes)%n", step);
                }
            }

            // if processor has current process
            else {
                ProcessState result = processor.executeNextInstruction(step);
                quantumCounter++;

                if (result == ProcessState.FINISHED) {
                    System.out.println("*** Process completed ***");
                    finishedCount++;
                    processor.setCurrentProcess(null); //mark the CPU as idle bc the process stopped running
                } else if (result == ProcessState.BLOCKED) {
                    System.out.println("*** Process blocked ***");
                    saveProcessContext(processor, currentPCB, step);
                    blockedList.add(currentPCB);
                    processor.setCurrentProcess(null);
                    //if process has run for a full quantum, it goes back to the ready list. Full quantum in our case= 5 ticks
                } else if (quantumCounter >= QUANTUM) {
                    System.out.println("*** Quantum expired ***");
                    saveProcessContext(processor, currentPCB, step);
                    readyQueue.add(currentPCB);
                    processor.setCurrentProcess(null);
                }
            }
            if (!blockedList.isEmpty()) {
                for (int i = 0; i < blockedList.size(); i++) {
                    ProcessControlBlock pcb = blockedList.get(i);
                    if (rand.nextDouble() < UNBLOCK_PROB) {
                        blockedList.remove(pcb);
                        readyQueue.add(pcb);
                        System.out.printf("Step %d Unblocking process PID %d -> moved to ready queue%n",
                                step, pcb.getProcess().pid);
                        break;
                    }
                }
            }
        }
        
        System.out.println("Finished processes: " + finishedCount);
        System.out.println("Ready processes: " + readyQueue.size()); 
        System.out.println("Blocked processes: " + blockedList.size());
    }

    private static void saveProcessContext(SimProcessor processor, ProcessControlBlock pcb, int step) {
        pcb.setCurrentInstruction(processor.getCurrInstruction());
        pcb.setRegisters(processor.getRegisterValues());
        
        int[] regs = pcb.getRegisters();
        System.out.printf("Step %d Context switch: Saving process: %d%n", step, pcb.getProcess().pid);
        System.out.printf("   Instruction: %d, Registers: R1=%d, R2=%d, R3=%d, R4=%d%n",
                pcb.getCurrentInstruction(), regs[0], regs[1], regs[2], regs[3]);
    }

    private static void restoreProcessContext(SimProcessor processor, ProcessControlBlock pcb, int step) {
        processor.setCurrentProcess(pcb.getProcess());
        processor.setCurrInstruction(pcb.getCurrentInstruction());
        int[] regs = pcb.getRegisters();
        for (int i = 0; i < regs.length; i++) {
            processor.setRegisterValue(i, regs[i]);
        }
        
        System.out.printf("Step %d Context switch: Restoring process: %d%n", step, pcb.getProcess().pid);
        System.out.printf("   Instruction: %d, Registers: R1=%d, R2=%d, R3=%d, R4=%d%n",
                pcb.getCurrentInstruction(), regs[0], regs[1], regs[2], regs[3]);
    }
}