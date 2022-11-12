package TGB;

import java.io.*;
import java.net.*;

public class Minerador {
	
	public void minerarBloco(Blockchain blockchain, String data) {
		blockchain.addBlock(data);
	}

	public static void main(String argv[]) throws Exception {

		System.out.println("Minerador online...");
		Minerador minerador = new Minerador();
		//Blockchain blockchain = null;
		Socket socketCliente = new Socket("127.0.0.1", 6789);
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
		
		inputServidor.close();
		outputServidor.close();
		socketCliente.close();
	}
}
