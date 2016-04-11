package ca.momoperes.schematic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SchematicFile {

    private final File source;
    private final List<SchematicBlock> blocks;
    private SchematicVector size;

    public SchematicFile(File source, SchematicVector size) {
        this.source = source;
        this.size = size;
        this.blocks = new ArrayList<SchematicBlock>();
    }

    public File getSource() {
        return source;
    }

    public List<SchematicBlock> getBlocks() {
        return blocks;
    }

    public SchematicVector getSize() {
        return size;
    }

    public void setSize(SchematicVector size) {
        this.size = size;
    }
}
