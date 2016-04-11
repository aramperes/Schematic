package ca.momoperes.schematic;

public class SchematicVector {

    private final int x, y, z;

    public SchematicVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
