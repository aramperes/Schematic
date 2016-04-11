package ca.momoperes.schematic;

public class SchematicBlock {

    private final SchematicVector position;
    private final int type;
    private final byte data;

    public SchematicBlock(SchematicVector position, int type, byte data) {
        this.position = position;
        this.type = type;
        this.data = data;
    }

    public SchematicBlock(SchematicVector position, int type) {
        this(position, type, (byte) 0);
    }

    public SchematicVector getPosition() {
        return position;
    }

    public int getType() {
        return type;
    }

    public byte getData() {
        return data;
    }
}
