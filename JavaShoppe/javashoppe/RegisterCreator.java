package javashoppe;

import java.util.ArrayList;

public class RegisterCreator {

    private int numSelf;
    private int numFull;
    ArrayList<Registers> reg = new ArrayList<>();

    public RegisterCreator() {

    }

    public RegisterCreator(int numS, int numF) {
        numSelf = numS;
        numFull = numF;

    }

    public ArrayList<Registers> createRegisters() {
        for (int i = 0; i < numFull; i++) {    //Full checkouts
            Registers temp = new Registers("full");
            reg.add(temp);
        }

        for (int j = 0; j < numSelf; j++) {    //self checkouts
            Registers temp = new Registers("self");
            reg.add(temp);
        }
        return reg;

    }
}