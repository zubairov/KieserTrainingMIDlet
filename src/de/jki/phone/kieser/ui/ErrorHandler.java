package de.jki.phone.kieser.ui;

import javax.microedition.lcdui.Displayable;

/**
 *
 * @author jkindler
 */
public interface ErrorHandler {
    public void handleError(Exception ex, Displayable nextScreen);
}
