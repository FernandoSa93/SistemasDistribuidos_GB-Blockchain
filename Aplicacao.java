package TGB;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Aplicacao {

	public static void main(String argv[]) throws Exception {
		
		Scanner ler = new Scanner(System.in);
		String ipServidor = "", fraseCliente = "", fraseServidor = "";
		
		System.out.println("Digite o IP do servidor:");
		ipServidor = ler.nextLine();
		Socket socketCliente = new Socket(ipServidor, 6789);
		System.out.println("Aplicação online...");

		ObjectOutputStream outputServidor = new ObjectOutputStream(socketCliente.getOutputStream());
		ObjectInputStream inputServidor = new ObjectInputStream(socketCliente.getInputStream());
		
		while (true) {
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Salvar texto no blockchain");
			System.out.println("2 - Finalizar");
			fraseCliente = ler.nextLine();
			
			while ((fraseCliente == null) || (Integer.parseInt(fraseCliente) < 1) || (Integer.parseInt(fraseCliente) > 2)) {
				System.out.println("Comando inválido, tente novamente!");
				fraseCliente = ler.nextLine();
			}
			
			if (fraseCliente.equals("2")) {
				outputServidor.writeUTF("Fim\n");
				outputServidor.flush();
				break;
			} else {
				System.out.println("Digite o texto que deseja salvar no blockchain:");
				fraseCliente = ler.nextLine();
				
				//Enviar frase para o servidor
				System.out.println("Pedido de operação no blockchain iniciado...");
				outputServidor.writeUTF(fraseCliente);
				outputServidor.flush();
				
				//Aguarda o retorno do servidor
				fraseServidor = inputServidor.readUTF();
				if (fraseServidor.equals("Sucesso\n")) {
					System.out.println("Operação concluída com sucesso!");
				}
			}	
		}	
		System.out.println("Fim");
		
		ler.close();
		inputServidor.close();
		outputServidor.close();
		socketCliente.close();
	}
}
