import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * <b>Actividad 1. Tarea Individual. Gestión de un concesionario de coches.</b><br><br>
 * Siguiendo la instrucciones de la práctica, implementamos una aplicación para la gestión del 
 * almacen de coches de un concesionario.<br>
 * El menú que aparecerá será el siguiente:<br><br>
 * <i>1. Añadir nuevo coche</i><br>
 * <i>2. Borrar coche por ID</i><br>
 * <i>3. Consultar coche por ID</i><br>
 * <i>4. Listado de coches</i><br>
 * <i>5. Exportar coches a archivo</i><br>
 * <i>6. Salir</i><br>
 * 
 * @author Ignacio Pineda Martín
 *
 */
public class Principal {
    /**
     * Lista de coches del concesionario.
     */
    private static ArrayList<Coche> Coches = new ArrayList<Coche>();    
    /**
     * Constante para definir el nombre completo del fichero que almacena
     * los objetos coche del concesionario.
     */    
    final static String ARCHIVO_COCHES = ".\\test\\coches.dat";
    /**
     * Constante para definir el nombre completo del fichero que almacena
     * los coche del concesionario en formato texto.
     */    
    final static String ARCHIVO_COCHES_TXT = ".\\test\\coches.txt";
    
    // Abrimos un Scanner para captura de datos por pantalla.
    private static Scanner lector = new Scanner(System.in);

    /**
     * Función principal de la clase.
     * @param args Sin uso.
     */
    public static void main(String[] args) {
        // Cargamos coches desde fichero.
        CargarFicheroCoches();

        // Variables auxiliares.
        int opcion = 0;
        boolean ok = false;
        String texto = "";
        
        // Bucle principal de tratamiento.
        do {
            // Mostramos las opciones.
            System.out.println();
            System.out.println("       CONCESIONARIO        ");
            System.out.println("----------------------------");
            System.out.println("1. Añadir nuevo coche");
            System.out.println("2. Borrar coche por ID");
            System.out.println("3. Consultar coche por ID");
            System.out.println("4. Listado de coches");
            System.out.println("5. Exportar coches a archivo de texto");
            System.out.println("6. Salir");
            System.out.println();

            // Capturamos opción.
            ok = false;
            while (!ok) {                 
                try {
                    System.out.print("Elija opción: ");
                    texto = lector.nextLine();
                    opcion = Integer.parseInt(texto);
                    ok = true;
                } catch (Exception e) {                        
                    System.out.println("Número no válido. Intentelo de nuevo.");
                    ok = false;
                }
            }                
            
            // Tratamos la opción elegida.
            switch(opcion) {
            case 1:
                // Añadir nuevo coche.
                NuevoCoche();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 2:
                // Borrar coche por ID.
                BajaConsultaCochePorID("BAJA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;
                
            case 3:
                // Consultar coche por ID.
                BajaConsultaCochePorID("CONSULTA");
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();
                break;

            case 4:
                // Listar coches.
                ListarCoches();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine(); 
                break;
                
            case 5:
                // Exportar coches a archivo de texto.
                ExportarCochesTxt();
                System.out.println("Pulse [ENTER] para continuar.");  
                lector.nextLine();   
                break;
                
            case 6:
                // Exportar coches a archivo de objetos y salir. 
                ExportarCoches();              
                break;
            default:
                System.out.println("Opción no válida. Intentelo de nuevo.");
                System.out.println();
                break;
            } // switch
            
        } while ( opcion != 6 ); // do
        
        // Fin de programa. Cerramos el Scanner de entrada.
        lector.close(); 
    }

    /**
     * Carga en una lista de coches el fichero definido por la constante <b><i>ARCHIVO_COCHES</i></b>, en caso de que 
     * dicho fichero exista.
     */
    private static void CargarFicheroCoches() {
        // Objeto "File" para obtener información del archivo de coches.
        File archivo = new File(ARCHIVO_COCHES);

        // Comprobamos si el archivo existe.
        if (archivo.exists()) {
            // Creamos una variable para controlar cuando hemos llegado a final de fichero. Es decir,
            // cuando ya no quedan más registros por leer.
            boolean eof = false;
            // Como el archivo existe, lo leemos y cargamos la lista de coches del concesionario.
            try {
                // Definimos el "stream" de entrada asociado al fichero.
                FileInputStream farchivo = new FileInputStream(ARCHIVO_COCHES);
                ObjectInputStream entrada = new ObjectInputStream(farchivo);                

                try {
                    // Bucle de lectura de todos los registros del fichero. 
                    while (!eof) {
                        Coches.add( (Coche) entrada.readObject() );                   
                    }                    
                } catch (EOFException e1) {
                    // Esta excepción se desencadenará cuando, al hacer un readObject(), no existan más
                    // registros por leer en el fichero.
                    eof = true;
                }
                
                // Cerramos el "stream" de entrada, así como el fichero.
                entrada.close();
                farchivo.close();
            } catch (Exception e2) {
                System.out.println("Ha ocurrido un problema al cargar " + ARCHIVO_COCHES + e2.getMessage());
            } // try
        }
    }

    /**
     * Agrega un nuevo objeto Coche a la lista de coches almacenados.
     */
    private static void NuevoCoche() {
        // Variables auxiliares.
        boolean ok = false;
        String texto = "";
        // Creamos un nuevo coche y le asignamos su ID de forma automática.
        Coche coche = new Coche();
        coche.setId();
        
        System.out.println();
        System.out.println("Añadir nuevo coche");
        System.out.println("------------------");
        //
        // Capturamos la matrícula.
        //
        ok = false;
        while (!ok) {                     
        	System.out.print("Introduce la matrícula[9999XXX]: ");
        	texto = lector.nextLine();
        	texto = texto.toUpperCase().trim();
        	// Comprobamos que la matrícula introducida no esta vacía y
        	// que coincida con el patrón solicitado.
        	if ( texto.equals("") || !texto.matches("^\\d{4}[A-Z]{3}") ) {
        		System.out.println("Matrícula en vacío/mal formateada. Intentelo de nuevo.");
        		ok = false;                           
        	}          
        	else {
    			coche.setMatricula(texto);
    			ok = true;
    			// Comprobamos que la matricula no este repetida en nuestro listado de coches,
    			// si esta repetida pondremos la matricula en blanco de nuevo
        		for (Coche c : Coches) {
        			if ( c.getMatricula().equals(texto) ) {
        				System.out.println("Matrícula repetida. Intentelo de nuevo.");
            			coche.setMatricula("");
        				ok = false;
        				break;
        			}
        		}
        	}
        }
        //
        // Capturamos la marca.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce la marca: ");
            texto = lector.nextLine();
            coche.setMarca(texto.toUpperCase().trim());
            if ( coche.getMarca().equals("") ) {
                System.out.println("Marca en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            }                            
        } 
        //
        // Capturamos el modelo.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce el modelo: ");
            texto = lector.nextLine();
            coche.setModelo(texto.toUpperCase().trim());
            if ( coche.getModelo().equals("") ) {
                System.out.println("Modelo en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            }                            
        }
        //
        // Capturamos el color.
        //
        ok = false;
        while (!ok) {
            System.out.print("Introduce el color: ");
            texto = lector.nextLine();
            coche.setColor(texto.toUpperCase().trim());
            if ( coche.getColor().equals("") ) {
                System.out.println("Modelo en vacío. Intentelo de nuevo.");
                ok = false;                           
            }
            else {
                ok = true;
            } 
        }
        // Añadimos el nuevo coche a la lista de coches.
        Coches.add(coche);
        System.out.println("Añadido coche con " + coche);
    }
    
    /**
     * Baja (borrado) o consulta de un coche por su id.
     * @param modo "BAJA" para el borrado de un coche / "CONSULTA" para mostrar en pantalla los datos 
     */
    private static void BajaConsultaCochePorID(String modo) {
        // Variables auxiliares.
        boolean ok = false;
        boolean encontrado = false;
        String texto = "";
        int ID = 0;

        System.out.println();
        // Mostramos la operación que estamos realizando.
        if (modo.equals("BAJA")) {
            System.out.println("Borrar coche por ID");
            System.out.println("-------------------");
        } else {
            System.out.println("Consultar coche por ID");
            System.out.println("----------------------");
        }

        //
        // Capturamos el ID.
        //
        ok = false;
        while (!ok) {
            try {
                System.out.print("Introduzca el ID: ");
                texto = lector.nextLine();
                ID = Integer.parseInt(texto);
                if ( ID == 0 ) {
                    System.out.println("El ID no puede ser 0. Intentelo de nuevo.");
                    ok = false;                           
                }
                else {
                    ok = true;
                }                            
            } catch (NumberFormatException e) {
                // 
                System.out.println("ID mal formateado. Intentelo de nuevo.");                           
            }
        }

        // Recorremos la lista de objetos coche para buscar el que coincida por ID.
        for (Iterator<Coche> iterator = Coches.iterator(); iterator.hasNext(); ) {
            Coche coche = iterator.next();
            // Comprobamos si el coche actual coincide con el buscado.
            if ( coche.getId() == ID ) {
                if (modo.equals("BAJA")) {
                    // En el caso de "BAJA", mostramos el coche que vamos a eliminar de la lista.
                    System.out.println(coche + " ***BORRADO***");
                    iterator.remove();
                }
                else {
                    // En el caso de "CONSULTA", mostramos el coche encontrado.
                    System.out.println(coche);                    
                }
                encontrado = true;
            }
        }
        
        // Si no hemos podido encontrar el ID introducido, mostramos un mensaje.
        if (!encontrado) {
            System.out.println("Coche con ID " + ID + " no encontrado.");
        }
    }
    
    /**
     * Muestra en pantalla la lista de coches almacenados.
     */
    private static void ListarCoches() {
        System.out.println();
        System.out.println("Listado de coches");
        System.out.println("-----------------");
        
        // Comprobamos si la lista de coches tiene elementos para ser listados.
        if (Coches.size() > 0) {            
            // Recorremos la lista e invocamos (indirectamente) al método toString() del objeto Coche.
            for (Coche coche : Coches) {
                System.out.println(coche);
            }        
        } else {
            System.out.println("No hay coches para listar.");             
        }
    }

    /**
     * Exporta la lista de objetos coches del concesionario, en modo texto, al fichero definido por 
     * la constante <b><i>ARCHIVO_COCHES_TXT</i></b>.
     */
    private static void ExportarCochesTxt() {
        System.out.println();
        System.out.println("Exportar coches a archivo de texto");
        System.out.println("----------------------------------");
        
        // Objeto "File" para exportar la lista de coches en modo texto.
        File archivo = new File(ARCHIVO_COCHES_TXT);
        
        // Comprobamos si el archivo existe.
        if (archivo.exists()) {
            // Si el archivo existe, lo borramos antes de crearlo de nuevo.
            // Esto lo hacemos para asegurarnos de que, en caso de que la lista de coches haya
            // quedado vacía (por ejemplo, por haber eliminado manualmente todos los coches
            // de la lista), no dejaremos una versión anterior obsoleta y/o vacía.
            try {
                if (!archivo.delete()) {
                    System.out.println("Error al borrar el archivo " + ARCHIVO_COCHES_TXT);
                }                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } // try
        }
        
        // Comprobamos si la lista de coches tiene elementos para ser exportados a modo texto.
        if (Coches.size() > 0) {
            try {
                // Abrir fichero y buffer para escritura.    
                FileWriter farchivo = new FileWriter(ARCHIVO_COCHES_TXT);
                BufferedWriter salida = new BufferedWriter(farchivo);

                // Recorremos la lista e invocamos al método toString() del objeto Coche.
                for (Coche coche : Coches) {
                    salida.write(coche.toString());
                    salida.newLine();
                }        

                // Cerrar el buffer y el fichero.
                salida.close();
                farchivo.close();
                
                System.out.println("Exportación a " + ARCHIVO_COCHES_TXT + " finalizada [" + Coches.size() + " coches].");
                
            } catch (Exception e) {
                System.out.println("Ha ocurrido un problema al exportar " + ARCHIVO_COCHES_TXT + " " + e.getMessage());
            } // try
            
        } else {
            System.out.println("No hay coches para exportar a texto.");             
        }
    }

    /**
     * Exporta la lista de objetos coches del concesionario, al fichero definido por 
     * la constante <b><i>ARCHIVO_COCHES</i></b>.
     */
    private static void ExportarCoches() {
        // Objeto "File" para exportar la lista de coches.
        File archivo = new File(ARCHIVO_COCHES);
          
        // Comprobamos si el archivo existe.
        if (archivo.exists()) {
            // Si el archivo existe, lo borramos antes de crearlo de nuevo.
            // Esto lo hacemos para asegurarnos de que, en caso de que la lista de coches haya
            // quedado vacía (por ejemplo, por haber eliminado manualmente todos los coches
            // de la lista), no dejaremos una versión anterior obsoleta y/o vacía.
            try {
                if (!archivo.delete()) {
                    System.out.println("Error al borrar el archivo " + ARCHIVO_COCHES);
                }                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } // try
        }

        // Creamos el archivo para exportar, sólo si la lista de coches no está vacía.            
        if (Coches.size() > 0) {            
            try {                
                // Definimos el "stream" de salida asociado al fichero, que será creado
                // automáticamente, sobreescribiendo la versión anterior.
                FileOutputStream farchivo = new FileOutputStream(ARCHIVO_COCHES);
                ObjectOutputStream salida = new ObjectOutputStream(farchivo);   
                // Volcamos la lista de coches en el fichero.
                for (Coche coche : Coches) {
                    salida.writeObject(coche);
                }
                // Cerramos el "stream" de salida, lo que cerrará también el fichero.
                salida.flush();
                salida.close();
                farchivo.close();
                
                System.out.println("Exportación a " + ARCHIVO_COCHES + " finalizada [" + Coches.size() + " coches].");
                    
            } catch (Exception e) {
                System.out.println("Ha ocurrido un problema al exportar " + ARCHIVO_COCHES + " " + e.getMessage());
            } // try
            
        } else {
            System.out.println("No hay objetos coches para exportar. No se crea archivo " + ARCHIVO_COCHES);
        }
    }

}
