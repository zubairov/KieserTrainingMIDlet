package de.jki.phone.kieser.ui;

import de.jki.phone.kieser.KieserTraining;
import javax.microedition.lcdui.Command;

/**
 *
 * @author jkindler
 */
public class ApplicationExitCommand extends Command 
        implements ExecutableCommand {
    private KieserTraining application;

    private ApplicationExitCommand(String label, int commandType, int priority) {
        super(label, commandType, priority);
    }

    public ApplicationExitCommand(String label
            , int commandType
            , int priority
            , KieserTraining app) {
        this(label, commandType, priority);
        this.application = app;
    }

    public void execute() {
        this.application.destroyApp(true);
        this.application.notifyDestroyed();
    }
}
