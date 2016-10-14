package it.unisa.gitdm.scattering;

public class PeriodsListNonInitialized extends Exception {

    @Override
    public String getMessage() {
        return "You have to inizialize periods list first. Use PeriodManager";
    }

}
