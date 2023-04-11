/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Mañana
 */
public class CuentaBancaria {

    // Identificador de cuenta
    private int Identificador;
    // Fecha de creación de la cuenta.
    private LocalDate fechaCreacion;
    // Límite de descubierto para la cuenta
    private int limiteDescubierto;
    // El identificador empezará por 0 e irá incrementando
    private int IDENTIFICADOR_BASE = 0;

    //-------------------------------------------
    //          DE OBJETO
    //-------------------------------------------
    // El saldo actual de la cuenta 
    private double saldo_actual;
    //El porcentaje de embargo de la cuenta
    private double porcentajeEmbargo;
    // La cantidad total euros ingresados en la cuenta desde que se abrió 
    private double cantidadTotal;
    // El saldo máximo que ha llegado a tener la cuenta desde que se abrió
    private double saldo_Max;

    //-------------------------------------------
    //          DE CLASE
    //-------------------------------------------
    // Saldo global entre todas las cuentas que existan en ese momento.
    private static double saldoGlobal;
    // Número de cuentas embargadas en el momento actual.
    private static int cuentasEmbargadas;
    // Fecha de creación de la cuenta más moderna hasta el momento
    private static LocalDate fechaCuentaMasModerna;
    //
    private static final double saldoPorOmision = 0.00;
    //
    private static final LocalDate fechaPorOmision = LocalDate.now();
    //
    private static final int limiteDescubiertoPorOmision = 0;

    //-------------------------------------------
    //          ATRIBUTOS
    //-------------------------------------------
    // Máximo descubierto permitido a la hora de crear una nueva cuenta
    public final double MAX_DESCUBIERTO = -2000.00;
    // Máximo saldo permitido en una cuenta
    public final double MAX_SALDO = 50000000.00;
    //Minimo saldo permitido para una cuenta
    public final double MIN_SALDO = 0.00;
    // Año mínimo permitido en la fecha de creación a la hora de crear una nueva cuenta
    public final int MIN_YEAR = 1900;
    // Máximo porcentaje de embargo permitido en una cuenta
    public final double MAX_EMBARGO = 100.0;
    // Mínimo porcentaje de embargo permitido en una cuenta
    public final double MIN_EMBARGO = 0.0;
    //Valor por omisión para el saldo inicial de una cuenta
    public final double DEFAULT_SALDO = 0.00;
    // Valor por omisión para el límite de descubierto de una cuenta
    public final int DEFAULT_MAX_DESCUBIERTO = 0;

    private double saldoInicial = 0.00;

    public CuentaBancaria(double saldoInicial, LocalDate fechaCreacion, int limiteDescubierto) throws IllegalArgumentException {

        if (this.saldoInicial < MIN_SALDO || this.saldoInicial > MAX_SALDO) {
            throw new IllegalArgumentException(String.format("Error: Parámetros de creación de la cuenta invalidos. Saldo inicial: %2f", this.saldoInicial));
        } else if (this.fechaCreacion == null || (this.fechaCreacion.isBefore(LocalDate.of(MIN_YEAR, 1, 1)) && this.fechaCreacion.isAfter(LocalDate.now()))) {
            throw new IllegalArgumentException(String.format("Error: Parámetros de creación de la cuenta invalido. Fecha de creación: %s", this.fechaCreacion));
        } else if (this.limiteDescubierto < 0.00 || this.limiteDescubierto > MAX_DESCUBIERTO) {
            throw new IllegalArgumentException(String.format("Error: Parametros de creación invalidos. Máximo descubierto: %d", this.limiteDescubierto));
        }

        this.saldoInicial = DEFAULT_SALDO;
        this.fechaCreacion = fechaCreacion;
        this.limiteDescubierto = limiteDescubierto;
        this.Identificador = this.IDENTIFICADOR_BASE;
        this.IDENTIFICADOR_BASE++;
        this.porcentajeEmbargo = 0.00;

    }

    public CuentaBancaria(double saldoInicial, LocalDate fechaCreacion) throws IllegalArgumentException {

        this(saldoInicial, fechaCreacion, CuentaBancaria.limiteDescubiertoPorOmision);

    }

    public CuentaBancaria(double SaldoInicial) throws IllegalArgumentException {
        this(SaldoInicial, CuentaBancaria.fechaPorOmision);
    }

    public CuentaBancaria() throws IllegalArgumentException {
        this(CuentaBancaria.saldoPorOmision);
    }

    //-------------------------------------------
    //          GETTERS
    //-------------------------------------------
    
    public int getID() {
        return this.Identificador;
    }

    public double getSaldo() {
        return this.saldo_actual;
    }

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public int getLimiteDescubierto() {
        return this.limiteDescubierto;
    }

    public double getPorcentajeEmbargo() {
        return this.porcentajeEmbargo;
    }

    public boolean isDescubierta() {
        return this.saldo_actual < 0;
    }

    public boolean isEmbargada() {
        return this.porcentajeEmbargo > 0;
    }

    public int getDiasCuenta() {
        return (int) this.fechaCreacion.until(LocalDate.now(), ChronoUnit.DAYS);
    }

    public double getSaldoMaximo() {
        if (this.saldo_actual > this.saldo_Max) {
            this.saldo_Max = this.saldo_actual;
        }
        return this.saldo_Max;
    }

    public double getTotalIngresado() {
        return this.cantidadTotal;
    }

    public double getSaldoGlobal() {
        return CuentaBancaria.saldoGlobal;
    }

    public LocalDate getFechaMasModerna() {
        return CuentaBancaria.fechaCuentaMasModerna;
    }

    public int getNumCuentasEmbargadas() {
        return CuentaBancaria.cuentasEmbargadas;
    }

    public void embargar(double porcentaje) throws IllegalArgumentException, IllegalStateException {
        if (porcentaje < 0 || porcentaje > 100 || porcentaje == 0) {
            throw new IllegalArgumentException(String.format("Error: Porcentaje de embargo no válido: %1f", porcentaje));
        } else if (this.isEmbargada() == false) {
            this.porcentajeEmbargo = porcentaje;
        } else if (this.isEmbargada() == true) {
            throw new IllegalStateException("Error: La cuenta ya está embargada.");
        }

        this.porcentajeEmbargo = porcentaje;
        CuentaBancaria.cuentasEmbargadas++;

    }

    public boolean desembargar() {

        if (this.isEmbargada()) {
            this.embargar(0.00);
            return true;
        } else {
            CuentaBancaria.cuentasEmbargadas--;
            return false;
        }
        
    }

    public void ingresar(double cantidad) throws IllegalArgumentException, IllegalStateException {
        if (cantidad < 0) {
            throw new IllegalArgumentException(String.format("Error: cantidad de ingreso no válida: %2f", cantidad));
        } else if (cantidad > this.MAX_SALDO) {
            throw new IllegalStateException(String.format("Error: saldo máximo superado con este ingreso: %2f", cantidad));
        }

        this.saldo_actual += cantidad;
        if (this.saldo_actual > this.saldo_Max) {
            this.saldo_Max += this.saldo_actual;
        }
        CuentaBancaria.saldoGlobal += cantidad;
        this.cantidadTotal += cantidad;
    }

    public void extraer(double cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException(String.format("Error: cantidad de extracción no válida: %2f", cantidad));
        } else if (cantidad > this.MAX_DESCUBIERTO) {
            throw new IllegalStateException(String.format("Error: descubierto máximo superado con este ingreso: %2f", cantidad));
        }

        this.saldo_actual -= cantidad;
    }

    public void transferir(double cantidad, CuentaBancaria cuentaDestino) throws IllegalArgumentException, IllegalStateException {

        double cantidadEfectiva = cantidad;
        
        if (cuentaDestino.isEmbargada()) {
            cantidadEfectiva -= cantidad * cuentaDestino.getPorcentajeEmbargo()/100;
        }

        if (cuentaDestino.getSaldo() + cantidadEfectiva > this.MAX_SALDO) {
            throw new IllegalArgumentException("Error: la cuenta de destino no puede asumir esa cantidad.");
        }
        
        if (cuentaDestino == null) {
            throw new IllegalArgumentException("Error: Cuenta no válida");
        } else if (cantidad < 0) {
            throw new IllegalArgumentException (String.format("Error: cantidad a transferir no válida: %2f", cantidad));
        } else if (this.limiteDescubierto < cantidad) {
            throw new IllegalStateException (String.format("Error: cantidad no disponible en la cuenta origen: %2f", cantidad));
        } else if (cuentaDestino.saldo_actual + cantidad > cuentaDestino.MAX_SALDO) {
            throw new IllegalStateException (String.format("Error: saldo máximo en la cuenta destino supero con esta transferencia: %2f", cantidad > this.MAX_SALDO ? this.MAX_SALDO - cantidad : cantidad));
        }
        
        cuentaDestino.ingresar(cantidadEfectiva);
        
    }
    
    
    
    
    //toString
    
    @Override
    public String toString() {
        return String.format("ID: %d - Saldo: %2f - Embargada: %s", this.Identificador, this.saldoInicial, this.isEmbargada() ? "si " + String.format("%5.1f", this.porcentajeEmbargo) : "no");
    }
    
    

}
