import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import modelos.Garage;

public class Main {
	static Scanner sc = new Scanner(System.in);

	static void menuUno() {
		System.out.println(" _____________________________");
		System.out.println("| > 1. Registrar trabajo      |");
		System.out.println("| > 2. Aumentar horas         |");
		System.out.println("| > 3. Aumentar coste piezas  |");
		System.out.println("| > 4. Finalizar trabajo      |");
		System.out.println("| > 5. Muestra trabajo        |");
		System.out.println("|_____________________________|");
	}

	static void menuDos() {
		System.out.println("Indique el tipo de trabajo: ");
		System.out.println("   _____________________________");
		System.out.println("  | 1. Revision                 |");
		System.out.println("  | 2. Reparacion Mecanica      |");
		System.out.println("  | 3. Reparacion Chapa Pintura |");
		System.out.println("  |_____________________________|");
	}


	public static void main(String[] args) throws FileNotFoundException, IOException {
		Garage gazMonkey = new Garage();
		;
		int option = 0;
		int value = 0;
		int codigo = 0;

		while (true) {
			menuUno();
			try {
			option = Integer.valueOf(sc.nextLine());

			if (option == 1) {
				menuDos();
				value = Integer.valueOf(sc.nextLine());
				System.out.println("Descripcion: ");
				String desc = sc.nextLine();
				codigo = gazMonkey.registrarTrabajo(value, desc);
				if (codigo >= 0) {
					System.out.println("Trabajo registrado con el identificador: " + codigo);
				} else {
					System.out.println("Opcion equivocada elegida!");
				}

			}
			if (option == 2) {
				System.out.println("Introduzca el identificador del trabajo: ");
				value = Integer.valueOf(sc.nextLine());
				System.out.println("Número de horas:");
				int horas = Integer.valueOf(sc.nextLine());
				codigo = gazMonkey.aumentarHoras(value, horas);
				if (codigo == 0) {
					System.out.println("Aumento realizado!");
				} else if (codigo == 1) {
					System.out.println("Las horas1adas son negativas");
				} else if (codigo == 2) {
					System.out.println("Este trabajo esta terminado");
				} else if (codigo == 3) {
					System.out.println("El identificador es incorrecto");
				} else if (codigo == 4) {
					System.out.println("Las horas de revisiones no pueden aumentarse");
				}
			}

			if (option == 3) {
				System.out.println("Introduzca el identificador del trabajo: ");
				value = Integer.valueOf(sc.nextLine());
				System.out.println("Coste de las piezas:");
				int coste = Integer.valueOf(sc.nextLine());
				codigo = gazMonkey.aumentarCostePiezas(value, coste);
				if (codigo == 0) {
					System.out.println("El precio se ha incrementado");
				} else if (codigo == 1) {
					System.out.println("El nuevo precio es negativo");
				} else if (codigo == 2) {
					System.out.println("Este trabajo esta terminado");
				} else if (codigo == 3) {
					System.out.println("ID inválido o no es una reparación");
				}
			}

			if (option == 4) {
				System.out.println("Introduzca el identificador del trabajo: ");
				value = Integer.valueOf(sc.nextLine());
				codigo = gazMonkey.finalizarTrabajo(value);
				if (codigo == -1) {
					System.out.println("El identificador es incorrecto");
				} else if (codigo == -2) {
					System.out.println("Este trabajo ya esta terminado");
				} else {
					System.out.println("Trabajo completado con exito");
				}
			}

			if (option == 5) {
				System.out.println("Introduzca el identificador del trabajo: ");
				value = Integer.valueOf(sc.nextLine());
				String trabajo = gazMonkey.muestraTrabajo(value);
				if (trabajo.length() > 0) {
					System.out.println(trabajo);
				} else {
					System.out.println("El identificador es incorrecto");
				}
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		

	}

	}
}
