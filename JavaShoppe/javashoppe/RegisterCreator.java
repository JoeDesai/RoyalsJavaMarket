package javashoppe;

import java.util.ArrayList;

public class RegisterCreator {

    private int numSelf;
    private int numFull;
    

    public RegisterCreator() {

    }

    public RegisterCreator(int numS, int numF) {
        numSelf = numS;
        numFull = numF;

    }

    public ArrayList<Registers> createRegisters() {
    	ArrayList<Registers> reg = new ArrayList<>();
    	
        for (int i = 0; i < numFull; i++) {    //Full checkouts
            Registers temp = new Registers("full");
            reg.add(temp);
            System.out.println(temp.toString());
        }

        for (int j = 0; j < numSelf; j++) {    //self checkouts
            Registers temp = new Registers("self");
            reg.add(temp);
            System.out.println(temp.toString());


        }
        return reg;

    }
}