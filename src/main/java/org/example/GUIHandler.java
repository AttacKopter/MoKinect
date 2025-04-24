package org.example;

import edu.ufl.digitalworlds.j4k.Skeleton;

import java.util.ArrayList;

public class GUIHandler {

    private ArrayList<Skeleton> skeletons;

    public GUIHandler() {
        skeletons = new ArrayList<>();
        skeletons.add(null);
    }

    public void addSkeleton(Skeleton skeleton) {
        skeletons.add(skeleton);
    }

    public Skeleton  getSkeleton() {
        if  (skeletons.size() > 1) {
            skeletons.remove(0);
        }
        return skeletons.get(0);

    }
}
