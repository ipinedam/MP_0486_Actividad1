import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Clase para implementar un objeto Coche.
 * @author Ignacio Pineda Martín
 *
 */
public class Coche implements Serializable {
    // Necesario por implementar el interface Serializable.
    private static final long serialVersionUID = 5571245623209555315L;
    
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    
    /**
     * Contador para asignar el ID automáticamente.
     */
    private static int contador;
    
    /**
     * Constructor por defecto de la clase.
     */
    public Coche() { 
    }

    /**
     * Constructor de la clase parametrizado por sus propiedades, con asignación automática del ID del nuevo coche.
     * @param matricula Matrícula del coche.
     * @param marca Marca del coche.
     * @param modelo Modelo del coche.
     * @param color Color del coche.
     */
    public Coche(String matricula, String marca, String modelo, String color) {
        super();
        this.setId();
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }

    /**
     * Getter para la propiedad <i>id</i> del coche
     * @return el identificador (<i>id</i>) del coche
     */
    public int getId() {
        return id;
    }
  
    /**
     * Setter para la propiedad <i>id</i> del coche. El valor se asigna automáticamente.
     */
    public void setId() {
        contador++;
        this.id = contador;
    }

    /**
     * Getter para la propiedad <i>matricula</i> del coche
     * @return la matricula del coche
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Setter para la propiedad <i>matricula</i> del coche
     * @param matricula la matrícula a asignar al coche
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Getter para la propiedad <i>marca</i>
     * @return la marca del coche
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Setter para la propiedad <i>marca</i>
     * @param marca la marca a asignar al coche
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Getter para la propiedad <i>modelo</i>
     * @return el modelo del coche
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Setter para la propiedad <i>modelo</i>
     * @param modelo el modelo a asignar al coche
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Getter para la propiedad <i>color</i>
     * @return el color del coche
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter para la propiedad <i>color</i>
     * @param color el color a asignar al coche
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Versión modificada del método <i>readObject()</i> para la clase <i>Coche</i>.<br>
     * La modificación consigue inicializar correctamente el contador usado para asignar automáticamente 
     * la propiedad <i>id</i> de la clase.
     * @param serialized ver <i>readObject()</i>
     * @throws ClassNotFoundException ver <i>readObject()</i>
     * @throws IOException ver <i>readObject()</i>
     */
    private void readObject(ObjectInputStream serialized) throws ClassNotFoundException, IOException {
        serialized.defaultReadObject();
        // Después de leer un objeto Coche desde fichero, asignamos el "id" leido
        // a la variable contador. De esta forma nos aseguramos tener el contador en su 
        // valor más alto.
        if (this.id > Coche.contador) {
            Coche.contador = this.id;            
        }
    }
  
    /**
     * Salida formateada con todas las propiedades del coche.
     * @return cadena formateada con todas las propiedades del coche
     */
    @Override
    public String toString() {
        return "ID: " + id + " Matricula: " + matricula + " Marca: " + marca + " Modelo: " + modelo + " Color: " + color;
    }
     
}
