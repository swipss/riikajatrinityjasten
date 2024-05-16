package org.example.riikajatrinityjasten;

import java.util.HashMap;
import java.util.Map;

public class MängijateHaldur {
    private static MängijateHaldur instance;
    private Map<String, Mängija> mängijad;

    private MängijateHaldur() {
        mängijad = new HashMap<>();
    }

    public static synchronized MängijateHaldur getInstance() {
        if (instance == null) {
            instance = new MängijateHaldur();
        }
        return instance;
    }

    public void lisaMängija(String nimi, Mängija mängija) {
        mängijad.put(nimi, mängija);
    }

    public Mängija getMängija(String nimi) {
        return mängijad.get(nimi);
    }

    public Map<String, Mängija> getMängijad() {
        return mängijad;
    }
}
