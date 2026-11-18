package br.usp.each.saeg.jaguar2.api;

import java.util.Collection;

public interface IMutationLineSpectrum extends ILineSpectrum{
    Collection<? extends IMutantSpectrum> getMutants();
}
