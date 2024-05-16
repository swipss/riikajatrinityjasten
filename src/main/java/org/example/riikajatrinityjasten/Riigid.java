package org.example.riikajatrinityjasten;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Riigid {
    private String failinimi;
    private List<String> riigid;
    private List<String> vihjed;
    private List<Integer> usedIndices;
    private Random random;

    public Riigid(String failinimi) throws Exception {
        this.failinimi = failinimi;
        this.riigid = new ArrayList<>();
        this.vihjed = new ArrayList<>();
        this.usedIndices = new ArrayList<>();
        this.random = new Random();
        failiLugemine();
    }

    public void failiLugemine() throws Exception {
        File fail = new File(this.failinimi);
        try (Scanner sc = new Scanner(fail, "UTF-8")) {
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                String[] andmed = rida.split(": ");
                if (andmed.length == 2) {
                    this.riigid.add(andmed[0].trim());
                    this.vihjed.add(andmed[1].trim());
                }
            }
        }
    }

    public String[] getRandomQuestion() {
        if (vihjed.isEmpty() || usedIndices.size() == vihjed.size()) {
            return new String[]{"", "No more unique hints available"};
        }
        int index;
        do {
            index = random.nextInt(vihjed.size());
        } while (usedIndices.contains(index));
        usedIndices.add(index);

        return new String[]{riigid.get(index), vihjed.get(index)};
    }
}