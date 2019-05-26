package ar.edu.itba.sia.tpe.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum SelectionMethods implements Selection {

    ELITE {

        @Override
        public List<Character> select(List<Character> sample, int size) {
            List<Character> aux = new ArrayList<>(sample);
            Collections.sort(aux, Collections.reverseOrder());
            return aux.subList(0, size);
        }

    };

}
