package br.usp.each.saeg.jaguar2.api;

import java.util.Collection;

public interface IMutantSpectrum {

    String getMutator();
    String getDescription();
    
    int getLineNumber();
    int getCef();
    int GetCep();

    Collection<String> getKillingtests();
}
