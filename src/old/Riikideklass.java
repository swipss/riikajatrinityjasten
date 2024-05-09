import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Riikideklass {
    private String failinimi;
    private ArrayList<String> Riigid;
    private ArrayList<String> Vihjed;

    public Riikideklass(String failinimi, ArrayList<String> riigid, ArrayList<String> vihjed) {
        this.failinimi = failinimi;
        Riigid = riigid;
        Vihjed = vihjed;
    }

    public void setFailinimi(String failinimi) {
        this.failinimi = failinimi;
    }

    public void FailiLugemine() throws Exception {
        File fail = new File(this.failinimi);
        try (Scanner sc = new Scanner(fail, "UTF-8")) {
            while (sc.hasNextLine()) {
                String rida = sc.nextLine();
                String[] andmed = rida.split(": ");
                //loeme andmed ridadest
                String riigiNimi = andmed[0];
                String vihje = andmed[1];
                //lisame riiginime ja vihje vastavasse järjendisse
                this.Riigid.add(riigiNimi);
                this.Vihjed.add(vihje);
            }
        }
    }

    public void ListideGenereerimine(int küsimusteArv, Arvaja mängija) throws Exception {
        //Loeme failist riiginimed ja vihjed, ning lisame need õigesse listi.
        FailiLugemine();
        ArrayList<String> kontrollKasOnVaremOlnud = new ArrayList<>();
        for (int i = 0; i < küsimusteArv; i++) {
            int juhuarv = (int) (Math.random() * 27);
            System.out.println("Vihje: " + (i + 1));

            //genereerib uue vihje, kui eelmine vihje juba oli
            while (kontrollKasOnVaremOlnud.contains(Vihjed.get(juhuarv))){
                juhuarv = (int) (Math.random() * 27);
            }
            kontrollKasOnVaremOlnud.add(Vihjed.get(juhuarv));
            System.out.println(Vihjed.get(juhuarv));
            //lisabimeetod
            Scanner skänner = new Scanner(System.in);
            System.out.print("Teie vastus: ");
            String arvajaVastus = skänner.nextLine().toLowerCase().trim().replace(" ", "");



            if (arvajaVastus.equals(Riigid.get(juhuarv).toLowerCase())) {
                System.out.println("\uD83D\uDD25 \uD83D\uDD25 \uD83D\uDD25 Õige vastus! Jätka samas vaimus!");
                mängija.LisaPunktiskoor();
            } else {
                System.out.println("\uD83E\uDD75 Pole hullu, järgmine kord tead, et õige vastus on " + Riigid.get(juhuarv) + ".");
            }
        }
    }
}
