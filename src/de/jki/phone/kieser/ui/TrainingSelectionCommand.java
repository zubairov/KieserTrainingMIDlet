package de.jki.phone.kieser.ui;

import javax.microedition.lcdui.Command;

/**
 *
 * @author jkindler
 */
public class TrainingSelectionCommand extends Command 
        implements ExecutableCommand {
    private TrainingSelectionReceiver receiver;
    private TraininSelectionProvider provider;

    private TrainingSelectionCommand(String label, int commandType, int priority) {
        super(label, commandType, priority);
    }

    public TrainingSelectionCommand(String label
            , int commandType
            , int priority
            , TraininSelectionProvider provider
            , TrainingSelectionReceiver receiver) {
        this(label, commandType, priority);
        this.provider = provider;
        this.receiver = receiver;
    }

    public void execute() {
        this.receiver.setTraining(this.provider.getTraining());
    }
}
