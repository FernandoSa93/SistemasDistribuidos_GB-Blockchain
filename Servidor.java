package TGB;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Servidor {

	public String fraseCliente = "", fraseMinerador = "";
	
	private List<Socket> socketListMinerador = new ArrayList<Socket>();
	private List<Socket> socketListCliente = new ArrayList<Socket>();

	private ServerSocket serverSocket;

	public void addSocketListMinerador(Socket s) {
		socketListMinerador.add(s);
	}
	
	public void addSocketListCliente(Socket s) {
		socketListCliente.add(s);
	}

	private Socket aguardaConexao() throws IOException {
		Socket socket = serverSocket.accept();
		return socket;
	}

	public static void main(String argv[]) throws Exception {

		try {
			Scanner ler = new Scanner(System.in);
			String fraseServidor = "";
			
			// instancia o servidor
			Servidor servidor = new Servidor();
			System.out.println("Servidor online...");
			System.out.println("Qual a dificuldade de mineração desejada(1 a 10)?");
			fraseServidor = ler.nextLine();
			
			while ((fraseServidor == null) || (Integer.parseInt(fraseServidor) < 1) || (Integer.parseInt(fraseServidor) > 10)) {
				System.out.println("Comando inválido, tente novamente!");
				fraseServidor = ler.nextLine();
			}

			// instancia o socket de recepção do servidor
			servidor.serverSocket = new ServerSocket(6789);
			
			// instancia o blockchain
			Blockchain blockchain = new Blockchain(Integer.parseInt(fraseServidor));
			System.out.println("Blockchain online...");

			System.out.println("Aguardando conexão do minerador...");
			Socket socketMinerador = servidor.aguardaConexao();
			servidor.addSocketListMinerador(socketMinerador);
			System.out.println("Minerador conectado...");
			
			ObjectOutputStream outputMinerador = new ObjectOutputStream(socketMinerador.getOutputStream());
			ObjectInputStream inputMinerador = new ObjectInputStream(socketMinerador.getInputStream());
			
			System.out.println("Aguardando conexão do cliente...");
			Socket socketCliente = servidor.aguardaConexao();
			servidor.addSocketListCliente(socketCliente);
			System.out.println("Cliente conectado...");
			
			ObjectOutputStream outputCliente = new ObjectOutputStream(socketCliente.getOutputStream());
			ObjectInputStream inputCliente = new ObjectInputStream(socketCliente.getInputStream());
			
			while (true) {
				System.out.println("Aguardando solicitação da aplicação...");
				servidor.fraseCliente = inputCliente.readUTF();
				
				if (servidor.fraseCliente.equals("Fim\n")) {
					outputMinerador.writeUTF("Fim\n");
					outputMinerador.flush();
					break;
				} else {
					System.out.println("Solicitação recebida...");
					
					//Envia os dados da aplicação para o Minerador para ser inserido no bloco
					outputMinerador.writeUTF(servidor.fraseCliente + "\n");
					outputMinerador.flush();
					
					//Envia o objeto do blockchain para que o minerador possa minerar o bloco
					System.out.println("Minerando bloco...");
					outputMinerador.writeObject(blockchain);
					//outputMinerador.flush();
					
					//Aguarda o minerador responder que finalizou o trabalho
					servidor.fraseMinerador = inputMinerador.readUTF();
					if (servidor.fraseMinerador.equals("Minerei\n")) {
						System.out.println("Bloco minerado com sucesso...");
					}
					
					//Notifica cliente
					outputCliente.writeUTF("Sucesso\n");
					outputCliente.flush();
				}
			}
			System.out.println("Fim");
			
			ler.close();
			
			outputMinerador.flush();
			outputCliente.flush();

			inputMinerador.close();
			outputMinerador.close();
			socketMinerador.close();
			inputCliente.close();
			outputCliente.close();
			socketCliente.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
