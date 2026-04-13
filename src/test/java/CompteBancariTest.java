package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;   
import main.java.CompteBancari;

public class CompteBancariTest {

    private CompteBancari compte;

    @BeforeEach
    void setUp() {
        // @BeforeEach prepara dades abans de cada prova [cite: 14, 47]
        compte = new CompteBancari("Anna Pou", "ES1234567890123456789012", 1000.0);
    }

    // --- PROVES DE CREACIÓ ---
    @Test
    void testCreacioCorrecta() {
        assertEquals("Anna Pou", compte.getTitular());
        assertEquals("ES1234567890123456789012", compte.getIban());
        assertEquals(1000.0, compte.getSaldo(), 0.001); // Evita errors petits de precisió decimal [cite: 61, 62]
    }

    @Test
    void testErrorTitularBuit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CompteBancari("", "ES1234567890", 1000.0);
        }); // Llença excepcions amb IllegalArgumentException [cite: 12, 48]
    }

    @Test
    void testErrorIbanBuit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CompteBancari("Anna Pou", "", 1000.0);
        });
    }

    @Test
    void testErrorSaldoInicialNegatiu() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CompteBancari("Anna Pou", "ES1234567890", -50.0);
        });
    }

    // --- PROVES D'INGRÉS ---
    @Test
    void testIngresCorrecte() {
        compte.ingressar(500.0);
        assertEquals(1500.0, compte.getSaldo(), 0.001);
    }

    @Test
    void testErrorIngresNegatiuOZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            compte.ingressar(0.0);
        });
    }

    // --- PROVES DE RETIRADA ---
    @Test
    void testRetiradaCorrecta() {
        compte.retirar(200.0);
        assertEquals(800.0, compte.getSaldo(), 0.001);
    }

    @Test
    void testErrorRetiradaNegativaOZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            compte.retirar(-10.0);
        });
    }

    @Test
    void testErrorRetiradaSuperiorAlSaldo() {
        assertThrows(IllegalArgumentException.class, () -> {
            compte.retirar(1500.0);
        }); // error si es vol retirar més saldo del disponible [cite: 40]
    }
}
