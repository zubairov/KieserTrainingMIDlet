package de.jki.phone.kieser;

/**
 *
 * @author jkindler
 */
public class Resources {
    public static String SELECTION = "selection";
    public static String ACTION_SELECT = "select";
    private static final String[] KEYS = {SELECTION, ACTION_SELECT};
    private static final Object[] ELEMENTS = {"Auswahl", "Auswählen"};

    public Resources() {
        super();
    }

    public Object get(String locale, String key) {
        Object result = null;

        for (int i = 0; i < KEYS.length && result == null; i++) {
            if (KEYS[i].equals(key)) {
                result = ELEMENTS[i];
            }
        }
        return result;
    }
}
