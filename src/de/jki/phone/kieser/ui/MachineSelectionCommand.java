package de.jki.phone.kieser.ui;

import javax.microedition.lcdui.Command;

/**
 *
 * @author jkindler
 */
public class MachineSelectionCommand
        extends Command
        implements ExecutableCommand {
    private MachineSelectionReceiver receiver;
    private MachineSelectionProvider provider;

    private MachineSelectionCommand(String label, int commandType, int priority) {
        super(label, commandType, priority);
    }

    public MachineSelectionCommand(String label
            , int commandType
            , int priority
            , MachineSelectionProvider provider
            , MachineSelectionReceiver receiver) {
        this(label, commandType, priority);
        this.provider = provider;
        this.receiver = receiver;
    }

    
    public void execute() {
        this.receiver.setMachine(this.provider.getMachine());
    }
}
