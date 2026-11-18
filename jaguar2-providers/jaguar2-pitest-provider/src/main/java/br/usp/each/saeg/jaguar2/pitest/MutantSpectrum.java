package br.usp.each.saeg.jaguar2.pitest;

import br.usp.each.saeg.jaguar2.api.IMutantSpectrum;

import java.util.Collection;

public class MutantSpectrum implements IMutantSpectrum {
    public String getMutator() {return " ";}
    public String getDescription() {return " ";}
    
    public int getLineNumber() {return 0;}
    public int getCef() {return 0;}
    public int GetCep() {return 0;}

    public Collection<String> getKillingtests(){return null;}
}
