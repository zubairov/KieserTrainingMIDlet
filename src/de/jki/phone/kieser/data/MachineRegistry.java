package de.jki.phone.kieser.data;

import java.util.Hashtable;

public class MachineRegistry {

    private static final String A1 = "A1", A2 = "A2", A3 = "A3", A4 = "A4";
    private static final String B1 = "B1", B5 = "B5", B6 = "B6", B7 = "B7"
                                , B8 = "B8";
    private static final String C1 = "C1", C3 = "C3", C5 = "C5", C7 = "C7";
    private static final String D5 = "D5", D6 = "D6", D7 = "D7";
    private static final String E1 = "E1", E2 = "E2", E3 = "E3", E4 = "E4"
                                , E5 = "E5";
    private static final String F1 = "F1", F2 = "F2", F3 = "F3";
    private static final String G1 = "G1", G3 = "G3", G4 = "G4", G5 = "G5";
    private static final String H1 = "H1", H2 = "H2", H3 = "H3", H4 = "H4"
                                , H5 = "H5", H6 = "H6", H7 = "H7";
    private static final String J2 = "J2", J3 = "J3", J4 = "J4", J5 = "J5"
                                , J9 = "J9";
    private final static String[] ID_LIST = {
        A1, A2, A3, A4, B1, B5, B6, B7, B8, C1, C3, C5, C7, D5, D6, D7, E1, E2
        , E3, E4, E5, F1, F2, F3, G1, G3, G4, G5, H1, H2, H3, H4, H5, H6, H7, J2
        , J3, J4, J5, J9};
    private Hashtable machines;

    public MachineRegistry() {
        super();
        machines = new Hashtable(ID_LIST.length);
    }

    public String[] getAvailable() {
        return ID_LIST;
    }

    public Machine getMachine(String id) {
        if (!machines.containsKey(id)) {
            machines.put(id, create(id));
        }
        return (Machine) machines.get(id);
    }

    private Machine create(String id) {
        Machine m = null;

        if (A1.equals(id)) {
            m = new Machine(A1, "Hüftstreckung", (short) 0, (short) 0,
                    (short) 300, new String[]{"Sitz"});
        } else if (A2.equals(id)) {
            m = new Machine(A2, "Beugung im Hüftgelenk", (short) 0, (short) 0,
                    (short) 300, new String[]{"Sitz"});
        } else if (A3.equals(id)) {
            m = new Machine(A3, "Spreizung im Hüftgelenk", (short) 0,
                    (short) 0, (short) 300, new String[]{"Sitz"});
        } else if (A4.equals(id)) {
            m = new Machine(A4, "Anziehung im Hüftgelenk", (short) 0,
                    (short) 0, (short) 300, new String[]{"Sitz"});
        } else if (B1.equals(id)) {
            m = new Machine(B1, "Beinstreckung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (B5.equals(id)) {
            m = new Machine(B5, "Beugung Knie (Bauchlage)", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (B6.equals(id)) {
            m = new Machine(B6, "Beinpressen", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (B7.equals(id)) {
            m = new Machine(B7, "Beugung Knie (sitzend)", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (B8.equals(id)) {
            m = new Machine(B8, "Fußheben", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (C1.equals(id)) {
            m = new Machine(C1, "Überzug", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (C3.equals(id)) {
            m = new Machine(C3, "Armzug", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (C5.equals(id)) {
            m = new Machine(C5, "Rudern im Schultergelenk", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (C7.equals(id)) {
            m = new Machine(C7, "Ruderzug", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (D5.equals(id)) {
            m = new Machine(D5, "Armkreuzen", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (D6.equals(id)) {
            m = new Machine(D6, "Brustdrücken", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (D7.equals(id)) {
            m = new Machine(D7, "Barrenstütz sitzend", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (E1.equals(id)) {
            m = new Machine(E1, "Nackendrücken", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (E2.equals(id)) {
            m = new Machine(E2, "Seitheben", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (E3.equals(id)) {
            m = new Machine(E3, "Drücken", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (E4.equals(id)) {
            m = new Machine(E4, "Schulterdrehung nach innen", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (E5.equals(id)) {
            m = new Machine(E5, "Schulterdrehung nach außen", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (F1.equals(id)) {
            m = new Machine(F1, "Rumpfdrehung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (F2.equals(id)) {
            m = new Machine(F2, "Rückenflexion", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (F3.equals(id)) {
            m = new Machine(F3, "Rückenstreckung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (G1.equals(id)) {
            m = new Machine(G1, "Schulterheben", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (G3.equals(id)) {
            m = new Machine(G3, "Halsbeugung seitwärts", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (G4.equals(id)) {
            m = new Machine(G4, "Halsbeugung nach vorne", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (G5.equals(id)) {
            m = new Machine(G5, "Nackenstreckung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H1.equals(id)) {
            m = new Machine(H1, "Armbeugung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H2.equals(id)) {
            m = new Machine(H2, "Armstreckung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H3.equals(id)) {
            m = new Machine(H3, "Handdrehung nach innen", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H4.equals(id)) {
            m = new Machine(H4, "Handdrehung nach außen", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H5.equals(id)) {
            m = new Machine(H5, "Beugung im Handgelenk", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H6.equals(id)) {
            m = new Machine(H6, "Streckung im Handgelenk", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (H7.equals(id)) {
            m = new Machine(H7, "Fingerbeugung", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (J2.equals(id)) {
            m = new Machine(J2, "Klimmzug vorne", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (J3.equals(id)) {
            m = new Machine(J3, "Klimmzug seitlich", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (J4.equals(id)) {
            m = new Machine(J4, "Barrenstütz", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (J5.equals(id)) {
            m = new Machine(J5, "Armstreckung stehend", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        } else if (J9.equals(id)) {
            m = new Machine(J9, "Seitbeuge", (short) 0, (short) 0, (short) 300,
                    new String[]{"Sitz"});
        }
        return m;
    }
}
