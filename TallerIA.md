Taller Inteligencia Artificial(IA)

1. Prompts Diseñados y Explicación

![Fases del Taller](./images/fases_taller.png)

2. Código Fuente en Java
Este código utiliza la librería Gson (asegúrate de incluirla en tu proyecto Maven o como .jar).
Java

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

// Clase Modelo para el Diccionario Anidado
class Registro {
    String id;
    String nombre;
    String extra;

    public Registro(String id, String nombre, String extra) {
        this.id = id;
        this.nombre = nombre;
        this.extra = extra;
    }
}

public class SistemaGestion {
    private static final String FILE_NAME = "datos.json";
    private static Map<String, Registro> mapa = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        mapa = cargarDesdeJSON();
        ejecutarMenu();
    }

    private static void ejecutarMenu() {
        Scanner sc = new Scanner(System.in);
        String op = "";

        while (!op.equals("3")) {
            System.out.println("\n--- SISTEMA EN JAVA ---");
            System.out.println("1. Registrar\n2. Mostrar\n3. Guardar y Salir");
            System.out.print("Seleccione: ");
            op = sc.nextLine();

            if (op.equals("1")) {
                System.out.print("ID Único: "); String id = sc.nextLine();
                if (mapa.containsKey(id)) {
                    System.out.println("⚠️ ID ya existe.");
                } else {
                    System.out.print("Nombre: "); String nom = sc.nextLine();
                    System.out.print("Dato Extra: "); String ext = sc.nextLine();
                    mapa.put(id, new Registro(id, nom, ext));
                    System.out.println("✅ Registrado.");
                }
            } else if (op.equals("2")) {
                mapa.values().forEach(r -> System.out.println("ID: " + r.id + " | " + r.nombre));
            } else if (op.equals("3")) {
                guardarAJSON();
                System.out.println("💾 Datos guardados.");
            }
        }
    }

    private static void guardarAJSON() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(mapa, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private static Map<String, Registro> cargarDesdeJSON() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new HashMap<>();
        try (Reader reader = new FileReader(FILE_NAME)) {
            Type type = new TypeToken<HashMap<String, Registro>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) { return new HashMap<>(); }
    }
}

3. Archivo JSON Generado (datos.json)
El resultado en disco muestra la estructura de diccionario anidado con el ID como clave principal:
JSON

{
  "101": {
    "id": "101",
    "nombre": "Daniel",
    "extra": "Estudiante de Ingeniería"
  },
  "102": {
    "id": "102",
    "nombre": "Carlos",
    "extra": "Monitor de IT"
  }
}

🎓 Conclusión Técnica
La implementación en Java destaca el uso de HashMap para una búsqueda eficiente y la librería GSON para la persistencia. Se logró un sistema robusto donde la información se organiza jerárquicamente, permitiendo que el estado de la aplicación se recupere íntegramente tras cada ejecución, cumpliendo con los principios de persistencia de datos.
Link repo: https://github.com/Dxnxxl06/Taller_IA.git