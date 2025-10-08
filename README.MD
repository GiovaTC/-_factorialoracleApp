# 🧮 FactorialOracleApp
Aplicación Java creada en **IntelliJ IDEA** que:

1. Pide un número por teclado.  
2. Calcula su **factorial**.  
3. Inserta el resultado en una **base de datos Oracle 19c**.  

---

## 🧩 Paso 1: Crear el proyecto

**Nombre del proyecto:** `FactorialOracleApp`

**Estructura del proyecto:**
FactorialOracleApp/
├── src/
│ └── com/factorial/
│ ├── Main.java
│ ├── ConexionOracle.java
│ └── Factorial.java
├── lib/
│ └── ojdbc8.jar ← Conector de Oracle
└── README.md

---

## ⚙️ Paso 2: Configurar conexión con Oracle

**Base de datos Oracle 19c (local):**

| Parámetro  | Valor |
|-------------|--------|
| user | `system` |
| password | `Tapiero123` |
| host | `localhost` |
| port | `1521` |
| SID | `orcl` |

### 🔗 Agregar el driver ojdbc8.jar
1. Clic derecho en el proyecto → **Open Module Settings**  
2. Ir a **Libraries** → `+ Add JARs`  
3. Seleccionar **ojdbc8.jar**  

---

## 🧱 Paso 3: Crear la tabla en Oracle

Ejecuta el siguiente script en **SQL Developer** o **SQL*Plus**:

```sql
CREATE TABLE FACTORIAL_RESULTADOS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NUMERO NUMBER NOT NULL,
    FACTORIAL NUMBER NOT NULL,
    FECHA_REGISTRO DATE DEFAULT SYSDATE
);

💻 Paso 4: Código Java
🔹 ConexionOracle.java

package com.factorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String USER = "system";
    private static final String PASSWORD = "Tapiero123";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a Oracle 19c");
        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }
        return conn;
    }
}

🔹 Factorial.java

package com.factorial;

public class Factorial {

    public static long calcularFactorial(int numero) {
        if (numero < 0) {
            throw new IllegalArgumentException("El número debe ser positivo.");
        }

        long resultado = 1;
        for (int i = 1; i <= numero; i++) {
            resultado *= i;
        }
        return resultado;
    }
}

🔹 Main.java

package com.factorial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese un número para calcular su factorial: ");
        int numero = sc.nextInt();

        long resultado = Factorial.calcularFactorial(numero);
        System.out.println("El factorial de " + numero + " es: " + resultado);

        try (Connection conn = ConexionOracle.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO FACTORIAL_RESULTADOS (NUMERO, FACTORIAL) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, numero);
                ps.setLong(2, resultado);
                ps.executeUpdate();
                System.out.println("✅ Resultado guardado en la base de datos.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error al guardar el resultado: " + e.getMessage());
        }
    }
}

📦 Paso 5: Ejecución
Ejecuta Main.java desde IntelliJ IDEA .
Ingresa un número (por ejemplo, 5) .

Salida esperada :
Ingrese un número para calcular su factorial: 5
El factorial de 5 es: 120
✅ Conexión exitosa a Oracle 19c
✅ Resultado guardado en la base de datos.

🧾 Paso 6: Verificar en Oracle
Ejecuta:
SELECT * FROM FACTORIAL_RESULTADOS;

Salida esperada:
ID	NUMERO	FACTORIAL	FECHA_REGISTRO
1	5	    120	        08-OCT-25

🧰 Requisitos
Java 8 o superior :
IntelliJ IDEA (Community o Ultimate)

Oracle Database 19c :
ojdbc8.jar (conector JDBC de Oracle)

🧑‍💻 Autor
Giovanny Alejandro Tapiero Cataño
Proyecto educativo — Java + Oracle 19c — 2025

