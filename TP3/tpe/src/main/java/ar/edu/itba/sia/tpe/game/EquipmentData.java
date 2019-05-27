package ar.edu.itba.sia.tpe.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EquipmentData {

    public static Map<Integer, Equipment> equipments[] = new Map[5];

    public EquipmentData() {
        equipments[0] = parseEquipmentFile("testdata/armas.tsv");
        equipments[1] = parseEquipmentFile("testdata/cascos.tsv");
        equipments[2] = parseEquipmentFile("testdata/botas.tsv");
        equipments[3] = parseEquipmentFile("testdata/guantes.tsv");
        equipments[4] = parseEquipmentFile("testdata/pecheras.tsv");
    }

    public static Map<Integer, Equipment>[] getEquipments(){
        return equipments;
    }

    private static Map<Integer, Equipment> parseEquipmentFile(String filename) {
        Map<Integer, Equipment> equipment = new HashMap<>();
        try {
            Scanner line = new Scanner(new File(filename));
            line.nextLine();
            while (line.hasNext()) {
                Equipment e = new Equipment(line.nextInt(), line.nextDouble(), line.nextDouble(), line.nextDouble(), line.nextDouble(), line.nextDouble());
                equipment.put(e.getId(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return equipment;
    }

}
