package codigo;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class Factorial {
	private static BigInteger[] resultadosParciales;

	public static void main(String[] args) {
		int cant_cores;
		int num;
		int tmn;	//tamaño
		int i, j, t;
		long tInicio, tFinal, tElapsed;
		boolean repetir = true;
		Scanner entrada = new Scanner(System.in);

		//Número del cuál se obtendrá su factorial
		System.out.println("Ingrese un numero ");
		num = entrada.nextInt();

		//Cantidad de nucleos --> subprocesos
		cant_cores = Runtime.getRuntime().availableProcessors();
		j = cant_cores;
		tmn = cant_cores;
		//t = tmn - 1;
		//tmn = calcularTamano(cant_cores);
		resultadosParciales = new BigInteger[cant_cores];


		tInicio = System.currentTimeMillis();
		//Iniciar los hilos de ejecución
		primeraCorrida(num, cant_cores);

		//Multiplicar todos los valores obtenidos
		do {
			j = actualizarValor(j);
			if(j == 1) {
				repetir = false;
			}
			resultadosParciales = multiplicar2en2(resultadosParciales);
		}while(repetir);
		tFinal = System.currentTimeMillis();
		tElapsed = tFinal - tInicio;
		System.out.println("\n" + imprimirTiempo(tElapsed));

		//Mostrar el resultado final por consola
		mostrarResultados(num, 0);

		entrada.close();
		System.out.println("\nFIN");
	}

	public static int calcularTamano(int cc) {
		int a;
		int tmn = 0;
		a = cc;

		//Almacenar TODOS los resultados parciales
		do {
			tmn += a;
			a = actualizarValor(a);
		}while(a > 0);
		System.out.println(cc + " núcleos --> Tamaño final " + tmn);
		return tmn;
	}

	public static int actualizarValor(int v) {
		int a = v;

		if((a % 2 == 1) && (a != 1)) {
			a++;
		}
		a =	a >> 1;

		return a;
	}

	public static void mostrarResultados(int num, int t) {
		int rpta;
		long tInicio, tFinal, tElapsed;
		Scanner entrada = new Scanner(System.in);

		System.out.println("\n¿Desea ver el resultado por pantalla? \n0 - no \n1 - si");
		rpta = entrada.nextInt();
		if(rpta == 1) {
			System.out.println("El resultado es: " + num + "! = " + resultadosParciales[t].toString());
		}

		System.out.println("\n¿Desea almacenar el resultado en un archivo TXT? \n0 - no \n1 - si");
		rpta = entrada.nextInt();
		if(rpta == 1) {
			String nombre = num + " factorial.txt";

			try {
				tInicio = System.currentTimeMillis();
				File archivo = new File(nombre);
				FileOutputStream is = new FileOutputStream(archivo);
				OutputStreamWriter osw = new OutputStreamWriter(is);
				Writer w = new BufferedWriter(osw);

				w.write(resultadosParciales[0].toString());
				w.close();
				tFinal = System.currentTimeMillis();
				tElapsed = tFinal - tInicio;
				System.out.println("El archivo tardó " + imprimirTiempo(tElapsed) + " en crearse");
			} catch (IOException e) {
				System.out.println("Error, no se pudo grabar correctamente");
			}

		}

		entrada.close();
	}

	public static void primeraCorrida(int num, int cc) {
		int i;
		Hilo[] hilos =  new Hilo[cc];

		for(i = 0; i<cc; i++) {
			hilos[i] = new Hilo(i, num, cc);
			hilos[i].start();
		}

		//Esperar a que todos los nucleos terminen
		for(i = 0; i<cc; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Almacenar los valores

		for(i = 0; i<cc; i++) {
			resultadosParciales[i] = hilos[i].getAcum();
		}

	}

	public static BigInteger[] multiplicar2en2(BigInteger[] numeros) {
		int i, j;
		int t0 = numeros.length;
		int t = actualizarValor(t0);
		long tInicio, tFinal, tElapsed;
		BigInteger[] resultParc = new BigInteger[t];

		tInicio = System.currentTimeMillis();
		for(i = 0; i<t; i++) {
			j = t0 - i - 1;

			if(i == j) {
				resultParc[i] = numeros[i];
			}else {
				resultParc[i] = numeros[i].multiply(numeros[j]);
			}
		}
		tFinal = System.currentTimeMillis();
		tElapsed = tFinal - tInicio;
		System.out.println("Tamaño del resultado: " + t +
							"\t\tTiempo: " + imprimirTiempo(tElapsed));

		return resultParc;
	}

	public static String imprimirTiempo(long tiempo) {
        long min, seg, ms;
        String reporte;

        min = tiempo / 60000;

        tiempo = tiempo - 60000 * min;
        seg = tiempo / 1000;

        tiempo = tiempo - seg * 1000;
        ms = tiempo;

        reporte = min + "min " + seg + "seg " + ms + "ms";
        return reporte;
	}

}

