public class Arvaja {
    private String arvajaNimi;
    private int punktiskoor;

    public Arvaja(String arvajaNimi, int punktiskoor) {
        this.arvajaNimi = arvajaNimi;
        this.punktiskoor = punktiskoor;
    }

    public int getPunktiskoor() {
        return punktiskoor;
    }

    @Override
    public String toString() {
        return "Mängija: " + arvajaNimi +
                ", lõpppunktiskoor: " + punktiskoor + ".";
    }

    public void LisaPunktiskoor(){
        this.punktiskoor += 1;
    }


}
