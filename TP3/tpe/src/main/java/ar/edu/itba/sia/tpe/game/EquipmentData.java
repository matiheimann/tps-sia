package ar.edu.itba.sia.tpe.game;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class EquipmentData {

    static Map<Integer, Equipment> equipments[] = new Map[5];

    public static void parseData() {
        equipments[0] = parseEquipmentFile("testdata/armas.tsv");
        System.out.println("Loaded " + equipments[0].size() + " weapons data");
        equipments[1] = parseEquipmentFile("testdata/botas.tsv");
        System.out.println("Loaded " + equipments[1].size() + " boots data");
        equipments[2] = parseEquipmentFile("testdata/cascos.tsv");
        System.out.println("Loaded " + equipments[2].size() + " helmets data");
        equipments[3] = parseEquipmentFile("testdata/guantes.tsv");
        System.out.println("Loaded " + equipments[3].size() + " gloves data");
        equipments[4] = parseEquipmentFile("testdata/pecheras.tsv");
        System.out.println("Loaded " + equipments[4].size() + " breastplates data");
    }

    private static Map<Integer, Equipment> parseEquipmentFile(String filename) {
        Map<Integer, Equipment> equipment = new HashMap<>();
        try(Scanner line = new Scanner(new File(filename))) {
            line.useLocale(Locale.US);
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
