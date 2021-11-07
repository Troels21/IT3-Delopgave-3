public class AftaleController {

    private AftaleController() {
    }

    static private final AftaleController AftaleControllerOBJ = new AftaleController();

    static public AftaleController getAftaleControllerOBJ() {
        return AftaleControllerOBJ;
    }

    // bolsk værdi til kontrol af cpr'er
    public boolean cprCheck(String name) {
        try {
            double test = Double.parseDouble(name);
            return name.length() == 10;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean navnCheck(String s){
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public String createAftale(String cpr, String name, String timestart, String timeend, String note) throws OurException {
        Aftale aftale = new Aftale();
        if (cprCheck(cpr)) {
            if (navnCheck(name)) {
                if (note.length() < 255) {
                    aftale.setCpr(cpr);
                    aftale.setName(name);
                    aftale.setTimestart(timestart);
                    aftale.setTimeend(timeend);
                    aftale.setNote(note);

                    SQL.getSqlOBJ().insertAftaleSQL(aftale);
                    return "added patient" + aftale;
                } else {
                    //forkert note
                    OurException ex = new OurException();
                    ex.setMessage("For lang note, skal være under 255 tegn.");
                    throw ex;
                }
            } else {
            //forkert navn
                OurException ex = new OurException();
                ex.setMessage("Ugyldigt navn");
                throw ex;
            }
        } else {
            // forkert cpr
            OurException ex = new OurException();
            ex.setMessage("CPR skal være 10 cifre, yyyymmddxxxx");
            throw ex;
        }


    }


}