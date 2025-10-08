package com.factorial;

public class Factorial {

    public static long calcularFactorial(int numero) {
        if (numero < 0) {
            throw new IllegalArgumentException("el numero debe ser positivo .");
        }

        long resultado = 1;
        for (int i = 1; i <= numero; i++) {
            resultado *= i;
        }
        return resultado;
    }
}
