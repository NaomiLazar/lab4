import java.util.*;
import java.io.*;

// Enumerări
enum Stare {
    ACHIZITIONAT, EXPUS, VANDUT
}

enum ModTiparire {
    COLOR, ALB_NEGRU
}

enum FormatCopiere {
    A3, A4
}

enum SistemOperare {
    WINDOWS, LINUX
}

// Clasa de bază
abstract class Echipament {
    protected String denumire;
    protected int nrInventar;
    protected double pret;
    protected String zonaMag;
    protected Stare stare;

    public Echipament(String denumire, int nrInventar, double pret, String zonaMag, Stare stare) {
        this.denumire = denumire;
        this.nrInventar = nrInventar;
        this.pret = pret;
        this.zonaMag = zonaMag;
        this.stare = stare;
    }

    public void setStare(Stare stare) {
        this.stare = stare;
    }

    public Stare getStare() {
        return stare;
    }

    @Override
    public String toString() {
        return String.format("Denumire: %s, Nr Inventar: %d, Pret: %.2f, Zona Mag: %s, Stare: %s",
                denumire, nrInventar, pret, zonaMag, stare);
    }
}

// Subclase
class Imprimanta extends Echipament {
    private int ppm;
    private int dpi;
    private int paginiCartus;
    private ModTiparire modTiparire;

    public Imprimanta(String denumire, int nrInventar, double pret, String zonaMag, Stare stare,
                      int ppm, int dpi, int paginiCartus, ModTiparire modTiparire) {
        super(denumire, nrInventar, pret, zonaMag, stare);
        this.ppm = ppm;
        this.dpi = dpi;
        this.paginiCartus = paginiCartus;
        this.modTiparire = modTiparire;
    }

    public void setModTiparire(ModTiparire modTiparire) {
        this.modTiparire = modTiparire;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", PPM: %d, DPI: %d, Pagini/Cartus: %d, Mod Tiparire: %s",
                ppm, dpi, paginiCartus, modTiparire);
    }
}

class Copiator extends Echipament {
    private int paginiToner;
    private FormatCopiere formatCopiere;

    public Copiator(String denumire, int nrInventar, double pret, String zonaMag, Stare stare,
                    int paginiToner, FormatCopiere formatCopiere) {
        super(denumire, nrInventar, pret, zonaMag, stare);
        this.paginiToner = paginiToner;
        this.formatCopiere = formatCopiere;
    }

    public void setFormatCopiere(FormatCopiere formatCopiere) {
        this.formatCopiere = formatCopiere;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Pagini/Toner: %d, Format Copiere: %s",
                paginiToner, formatCopiere);
    }
}

class SistemCalcul extends Echipament {
    private String tipMonitor;
    private double vitezaProcesor;
    private int capacitateHDD;
    private SistemOperare sistemOperare;

    public SistemCalcul(String denumire, int nrInventar, double pret, String zonaMag, Stare stare,
                        String tipMonitor, double vitezaProcesor, int capacitateHDD, SistemOperare sistemOperare) {
        super(denumire, nrInventar, pret, zonaMag, stare);
        this.tipMonitor = tipMonitor;
        this.vitezaProcesor = vitezaProcesor;
        this.capacitateHDD = capacitateHDD;
        this.sistemOperare = sistemOperare;
    }

    public void setSistemOperare(SistemOperare sistemOperare) {
        this.sistemOperare = sistemOperare;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Tip Monitor: %s, Viteza Procesor: %.2f GHz, HDD: %d GB, Sistem Operare: %s",
                tipMonitor, vitezaProcesor, capacitateHDD, sistemOperare);
    }
}

