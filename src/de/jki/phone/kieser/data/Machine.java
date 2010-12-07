package de.jki.phone.kieser.data;

public class Machine {

    private String id;

    public Machine(String id, String description, short minRom, short maxRom, short maxPounds,
            String[] valueNames) {
        super();

        this.id = id;
        this.description = description;
        this.minRom = minRom;
        this.maxRom = maxRom;
        this.valueNames = valueNames;
        this.maxPounds = maxPounds;
    }
    private String description;
    private short minRom;
    private short maxRom;
    private short maxPounds;
    private String[] valueNames;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public short getMinRom() {
        return minRom;
    }

    public short getMaxRom() {
        return maxRom;
    }

    public short getMaxPounds() {
        return maxPounds;
    }

    public String[] getValueNames() {
        return valueNames;
    }
}
