package com.factorial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" ingrese el numero de la operacion , para calcular su factorial: ");
        int numero = sc.nextInt();

        long resultado = Factorial.calcularFactorial(numero);
        System.out.println(" el factorial de " + numero + "es: " + resultado);

        try (Connection conn = Conexionoracle.getConnection()) {
            if (conn != null) {
                String sql = "INSERT INTO FACTORIAL_RESULTADOS (NUMERO, FACTORIAL) VALUES (?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, numero);
                ps.setLong(2, resultado);
                ps.executeUpdate();
                System.out.println("✅ Resultado guardado en la base de datos .");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al guardar el resultado: " + e.getMessage());
        }
    }
}
