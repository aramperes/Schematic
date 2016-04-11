package ca.momoperes.schematic;

import com.flowpowered.nbt.ByteArrayTag;
import com.flowpowered.nbt.CompoundMap;
import com.flowpowered.nbt.CompoundTag;
import com.flowpowered.nbt.ShortTag;
import com.flowpowered.nbt.stream.NBTInputStream;
import com.flowpowered.nbt.stream.NBTOutputStream;

import java.io.*;

public class Schematic {

    /**
     * Loads a schematic from a source file
     *
     * @param source the source file
     * @return the loaded schematic
     * @throws IOException if the file could not be loaded as a schematic file
     */
    public static SchematicFile load(File source) throws IOException {
        NBTInputStream stream = new NBTInputStream(new FileInputStream(source));
        CompoundTag nbt = (CompoundTag) stream.readTag();

        int width = (int) (Short) nbt.getValue().get("Width").getValue();
        int height = (int) (Short) nbt.getValue().get("Height").getValue();
        int length = (int) (Short) nbt.getValue().get("Length").getValue();
        SchematicVector size = new SchematicVector(width, height, length);

        SchematicFile schematic = new SchematicFile(source, size);

        byte[] blockArray = (byte[]) nbt.getValue().get("Blocks").getValue();
        byte[] dataArray = (byte[]) nbt.getValue().get("Data").getValue();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    SchematicVector pos = new SchematicVector(x, y, z);
                    int index = (y * length + z) * width + x;
                    int type = (int) blockArray[index];
                    byte data = dataArray[index];
                    SchematicBlock block = new SchematicBlock(pos, type, data);
                    schematic.getBlocks().add(block);
                }
            }
        }

        return schematic;
    }

    /**
     * Saves a schematic file to its source file
     *
     * @param schematic the schematic to save
     * @throws IOException if the schematic could not be saved
     */
    public static void save(SchematicFile schematic) throws IOException {
        save(schematic, schematic.getSource());
    }

    /**
     * Saves a schematic file to the given destination
     *
     * @param schematic the schematic to save
     * @throws IOException if the schematic could not be saved
     */
    public static void save(SchematicFile schematic, File destination) throws IOException {
        CompoundTag nbt = new CompoundTag("Schematic", new CompoundMap());
        nbt.getValue().put(new ShortTag("Width", (short) schematic.getSize().getX()));
        nbt.getValue().put(new ShortTag("Height", (short) schematic.getSize().getY()));
        nbt.getValue().put(new ShortTag("Length", (short) schematic.getSize().getZ()));

        ByteArrayOutputStream blockStream = new ByteArrayOutputStream(schematic.getBlocks().size());
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream(schematic.getBlocks().size());

        for (SchematicBlock block : schematic.getBlocks()) {
            blockStream.write(block.getType());
            dataStream.write(block.getData());
        }

        ByteArrayTag blocks = new ByteArrayTag("Blocks", blockStream.toByteArray());
        ByteArrayTag data = new ByteArrayTag("Data", dataStream.toByteArray());

        nbt.getValue().put(blocks);
        nbt.getValue().put(data);

        NBTOutputStream stream = new NBTOutputStream(new FileOutputStream(destination));
        stream.writeTag(nbt);
        stream.close();
    }
}
