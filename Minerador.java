package TGB;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Minerador {
	
	public void minerarBloco(Blockchain blockchain, String data) {
		blockchain.addBlock(data);
	}

	public static void main(String argv[]) throws Exception {
		
		Scanner ler = new Scanner(System.in);
		String ipServidor = "";
		
		System.out.println("Digite o IP do servidor:");
		ipServidor = ler.nextLine();
		Minerador minerador = new Minerador();
		Socket socketCliente = new Socket(ipServidor, 6789);
		System.out.println("Minerador online...");
		
		String fraseServidor = "";

		ObjectOutputStream outputServidor = new ObjectOutputStream(socketCliente.getOutputStream());
		ObjectInputStream inputServidor = new ObjectInputStream(socketCliente.getInputStream());

		while (true) {
			System.out.println("Aguardando solicitação do servidor...");
			fraseServidor = inputServidor.readUTF();
			
			if (fraseServidor.equals("Fim\n")) {
				break;
			} else {
				//Minerar bloco
				Blockchain blockchain = (Blockchain) inputServidor.readObject();
				
				System.out.println("Minerando bloco...");
				minerador.minerarBloco(blockchain, fraseServidor);
				
				System.out.println("Mineração concluída...");
				outputServidor.writeUTF("Minerei\n");
				outputServidor.flush();
			}
		}
		System.out.println("Fim");
		
		ler.close();
		inputServidor.close();
		outputServidor.close();
		socketCliente.close();
	}
}
