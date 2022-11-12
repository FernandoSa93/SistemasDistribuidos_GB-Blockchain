package TGB;

import java.security.MessageDigest;
import java.util.Date;

public class Block {

	public int index;
	public String previousHash;
	public String data;
	public Date timestamp;
	public String hash;
	
	//Dificuldade geral do sistema
	//Quanto maior a dificuldade, mais zeros precisarão estar a esquerda do hash, dependendo de mais poder computacional na mineração
	public int difficulty;
	
	//Quantidade de tentativas até que o hash correto seja criado
	public int nonce;

	public Block(int index, String previousHash, String data, int difficulty) {
		this.index = index;
		this.previousHash = previousHash;
		this.data = data;
		this.timestamp = new Date();
		this.difficulty = difficulty;
		this.nonce = 0;

		this.mine();
	}

	public String generateHash() {
		return sha256(index + previousHash + data + timestamp + nonce);
	}

	public void mine() {
		String zeros = "";
		hash = generateHash();
		
		for (int i = 0; i < difficulty; i++) {
			zeros += '0';
		}

	    //Repetir até a esquerda do hash possuir a quantidade correta de zeros 
		//de acordo com a dificuldade
		while (!(hash.substring(0, difficulty).equals(zeros))) {
	    	nonce++;
	    	hash = generateHash();
	    }
	}
	
	//Gerar o hash usando o padrão sha256
	public static String sha256(final String base) {
	    try{
	        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        final byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        final StringBuilder hexString = new StringBuilder();
	        for (int i = 0; i < hash.length; i++) {
	            final String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) 
	              hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
}