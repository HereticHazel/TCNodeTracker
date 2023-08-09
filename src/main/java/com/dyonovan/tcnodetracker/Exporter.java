package com.dyonovan.tcnodetracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.common.DimensionManager;

import com.dyonovan.tcnodetracker.lib.NodeList;

public class Exporter extends CommandBase {

    @Override
    public String getCommandName() {
        return "exportnodelist";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/exportnodelist";
    }

    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";
    private static final String HEADER = "X, Z, Y, Dimension, Type, Modifier, Aer, Aqua, Ignis, Ordo, Perditio, Terra, Compound Aspects";

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        List<Object> nodes = new ArrayList<>();

        for (NodeList n : TCNodeTracker.nodelist) {
            Node node = new Node(n.x, n.y, n.z, n.dim, n.type, n.aspect, n.mod);
            nodes.add(node);
        }

        try {
            final File dir = new File("/home/chris/Documents");
            FileWriter file = new FileWriter(new File(dir, "Nodes.csv"));
            file.append(HEADER);
            file.append(SEPARATOR);
            for (Object node : nodes) {
                Node b = (Node) node;
                file.append(String.valueOf(b.getX()));
                file.append(DELIMITER);
                file.append(String.valueOf(b.getZ()));
                file.append(DELIMITER);
                file.append(String.valueOf(b.getY()));
                file.append(DELIMITER);
                file.append(b.getDim());
                file.append(DELIMITER);
                file.append(b.getType());
                file.append(DELIMITER);
                file.append(b.getMod());
                file.append(DELIMITER);
                file.append(b.getAspectAer());
                file.append(DELIMITER);
                file.append(b.getAspectAqua());
                file.append(DELIMITER);
                file.append(b.getAspectIgnis());
                file.append(DELIMITER);
                file.append(b.getAspectOrdo());
                file.append(DELIMITER);
                file.append(b.getAspectPerditio());
                file.append(DELIMITER);
                file.append(b.getAspectTerra());
                if (!b.getCompoundAspects().isEmpty()) {
                    for (int i = 0; i < b.getCompoundAspects().size(); i++) {
                        file.append(DELIMITER);
                        file.append(b.getCompoundAspects().get(i));
                    }
                }
                file.append(SEPARATOR);
            }
            file.close();
            sender.addChatMessage(
                    new ChatComponentTranslation("Node List succesfully exported to " + dir + "/Nodes.csv!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    static class Node {

        public int x, y, z, dim;
        public String type, mod;
        public HashMap<String, Integer> aspect;

        public Node(int x, int y, int z, int dim, String type, HashMap<String, Integer> aspect, String mod) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dim = dim;
            this.type = type;
            this.aspect = aspect;
            this.mod = mod;
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

        public String getMod() {
            return mod;
        }

        public String getDim() {
            return DimensionManager.getProvider(dim).getDimensionName();
        }

        public String getType() {
            return type;
        }

        public String getAspectAer() {
            if (aspect.containsKey("aer")) {
                return aspect.get("aer").toString();
            } else {
                return "0";
            }
        }

        public String getAspectAqua() {
            if (aspect.containsKey("aqua")) {
                return aspect.get("aqua").toString();
            } else {
                return "0";
            }
        }

        public String getAspectIgnis() {
            if (aspect.containsKey("ignis")) {
                return aspect.get("ignis").toString();
            } else {
                return "0";
            }
        }

        public String getAspectOrdo() {
            if (aspect.containsKey("ordo")) {
                return aspect.get("ordo").toString();
            } else {
                return "0";
            }
        }

        public String getAspectPerditio() {
            if (aspect.containsKey("perditio")) {
                return aspect.get("perditio").toString();
            } else {
                return "0";
            }
        }

        public String getAspectTerra() {
            if (aspect.containsKey("terra")) {
                return aspect.get("terra").toString();
            } else {
                return "0";
            }
        }

        public ArrayList<String> getCompoundAspects() {

            List<String> keySet = new ArrayList<>(aspect.keySet());
            keySet.removeAll(Arrays.asList("terra", "ordo", "ignis", "perditio", "aqua", "aer"));

            if (keySet.size() != 0) {
                ArrayList<String> values = new ArrayList<>();
                for (String s : keySet) {
                    values.add(s + ", " + aspect.get(s));
                }
                return values;
            } else {
                return new ArrayList<>();
            }
        }
    }
}
