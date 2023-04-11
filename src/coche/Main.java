
package coche;

/**
 *
 * @author Fran
 */
public class Main {
    
      public static void main(String[] args) {
        Coche miCoche;
        int stockActual;
        
        miCoche = new Coche("Opel",12000,500);
        try 
        {
            operacionventa();
            miCoche.vender(300);
        } catch (Exception e)
        {
            System.out.print("Fallo al vender");
        }
        
        try
        {
            operacioncompra();
            miCoche.comprar(500);
        } catch (Exception e)
        {
            System.out.print("Fallo al ingresar");
        }
        stockActual = miCoche.obtenerStock();
        System.out.println("El stock actual es"+ stockActual );
    }

    private static void operacioncompra() {
        System.out.println("Compra de Coches");
    }

    private static void operacionventa() {
        System.out.println("Venta de Coches");
    }

}
    
