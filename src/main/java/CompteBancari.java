package main.java;

/**
 * Representa un compte bancari amb operacions bàsiques d'ingrés i retirada.
 * La lògica de negoci està completament separada de la presentació.
 */
public class CompteBancari {

    // --- Constants per evitar "Magic Numbers" ---
    private static final double LLINDAR_SALDO_BAIX   = 1000.0;
    private static final double LLINDAR_SALDO_NORMAL = 5000.0;

    private final String titular;
    private final String iban;
    private double saldo;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    public CompteBancari(String titular, String iban, double saldoInicial) {
        validarTitular(titular);
        validarIban(iban);
        validarSaldoNoNegatiu(saldoInicial);

        this.titular = titular;
        this.iban    = iban;
        this.saldo   = saldoInicial;
    }

    // -------------------------------------------------------------------------
    // Operacions de negoci
    // -------------------------------------------------------------------------

    /**
     * Ingressa una quantitat positiva al compte.
     *
     * @param quantitat Import a ingressar (ha de ser > 0)
     * @throws IllegalArgumentException si la quantitat no és positiva
     */
    public void ingressar(double quantitat) {
        validarQuantitatPositiva(quantitat);
        saldo += quantitat;
    }

    /**
     * Retira una quantitat del compte si hi ha saldo suficient.
     *
     * @param quantitat Import a retirar (ha de ser > 0 i <= saldo)
     * @throws IllegalArgumentException si la quantitat no és vàlida o no hi ha saldo
     */
    public void retirar(double quantitat) {
        validarQuantitatPositiva(quantitat);
        validarSaldoSuficient(quantitat);
        saldo -= quantitat;
    }

    // -------------------------------------------------------------------------
    // Mètode de presentació (únic punt on s'imprimeix informació)
    // -------------------------------------------------------------------------

    /**
     * Mostra les dades del compte per la sortida estàndard.
     * Separa la responsabilitat de presentació de la lògica de negoci.
     */
    public void mostrarDades() {
        System.out.println("Titular : " + titular);
        System.out.println("IBAN    : " + iban);
        System.out.printf ("Saldo   : %.2f €%n", saldo);
        System.out.println("Estat   : " + descripcioEstatSaldo());
    }

    // -------------------------------------------------------------------------
    // Mètodes privats d'ajuda (helpers)
    // -------------------------------------------------------------------------

    /** Retorna una descripció textual de l'estat del saldo (sense imprimir). */
    private String descripcioEstatSaldo() {
        if (saldo < LLINDAR_SALDO_BAIX) {
            return "Saldo baix";
        } else if (saldo < LLINDAR_SALDO_NORMAL) {
            return "Saldo normal";
        } else {
            return "Saldo alt";
        }
    }

    // --- Validacions extraetes com a mètodes independents ---

    private static void validarTitular(String titular) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("El titular és obligatori");
        }
    }

    private static void validarIban(String iban) {
        if (iban == null || iban.isBlank()) {
            throw new IllegalArgumentException("L'IBAN és obligatori");
        }
    }

    private static void validarSaldoNoNegatiu(double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no pot ser negatiu");
        }
    }

    private static void validarQuantitatPositiva(double quantitat) {
        if (quantitat <= 0) {
            throw new IllegalArgumentException("La quantitat ha de ser positiva");
        }
    }

    private void validarSaldoSuficient(double quantitat) {
        if (quantitat > saldo) {
            throw new IllegalArgumentException("No hi ha prou saldo");
        }
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    public String getTitular() {
        return titular;
    }

    public String getIban() {
        return iban;
    }

    public double getSaldo() {
        return saldo;
    }
}
