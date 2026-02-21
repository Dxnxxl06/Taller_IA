import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

// --- CLASE MODELO (Estructura Anidada) ---
class Producto {
    String id;
    String nombre;
    double precio;

    public Producto(String id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Precio: $" + precio;
    }
}

// --- CLASE PRINCIPAL ---
public class TallerPersistencia {
    private static final String FILE_NAME = "datos.json";
    private static Map<String, Producto> inventario = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        inventario = cargarDatos(); // Carga inicial
        menu();
    }

    // --- MANEJO DEL MENÚ (RETO 4) ---
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 3) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Registrar (Diccionario Anidado)");
            System.out.println("2. Mostrar Todo");
            System.out.println("3. Guardar y Salir");
            System.out.print("Seleccione: ");
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 -> registrar(sc);
                    case 2 -> mostrar();
                    case 3 -> guardarDatos();
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor ingrese un número.");
            }
        }
    }

    // --- LÓGICA DE NEGOCIO (RETO 2) ---
    private static void registrar(Scanner sc) {
        System.out.print("ID Único (Clave): ");
        String id = sc.nextLine();
        if (inventario.containsKey(id)) {
            System.out.println("❌ Error: El ID ya existe en el diccionario.");
            return;
        }
        System.out.print("Nombre: ");
        String nom = sc.nextLine();
        System.out.print("Precio: ");
        double pre = Double.parseDouble(sc.nextLine());

        inventario.put(id, new Producto(id, nom, pre));
        System.out.println("✅ Registrado con éxito.");
    }

    private static void mostrar() {
        if (inventario.isEmpty()) System.out.println("Inventario vacío.");
        else inventario.values().forEach(System.out::println);
    }

    // --- PERSISTENCIA (RETO 3) ---
    private static void guardarDatos() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(inventario, writer);
            System.out.println("💾 Datos persistidos en " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error al guardar.");
        }
    }

    private static Map<String, Producto> cargarDatos() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return new HashMap<>(); // Manejo de FileNotFoundError

        try (Reader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<HashMap<String, Producto>>(){}.getType();
            Map<String, Producto> data = gson.fromJson(reader, type);
            return (data != null) ? data : new HashMap<>();
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}