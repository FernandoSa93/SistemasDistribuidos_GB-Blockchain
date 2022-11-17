package TGB;

import java.io.Serializable;
import java.util.Stack;

@SuppressWarnings("serial")
class Blockchain implements Serializable {

	public int index;
	public int difficulty;
	Stack<Block> blocks;

	public Blockchain(Integer difficulty) {
		this.blocks = new Stack<Block>();
		this.index = 1;
		this.difficulty = difficulty;
	}

	public Block getLastBlock() {
		return this.blocks.get(this.blocks.size() - 1);
	}

	public void addBlock(String data) {
		int index = this.index;
		int difficulty = this.difficulty;
		
		String previousHash = "";
		if (index > 1) {
			previousHash = this.getLastBlock().hash;
		}

		Block block = new Block(index, previousHash, data, difficulty);

		this.index++;
		this.blocks.push(block);
	}

	public Boolean isValid() {
		for (int i = 1; i < this.blocks.size(); i++) {
			Block currentBlock = this.blocks.get(i);
			Block previousBlock = this.blocks.get(i - 1);

			//Gera novamente o hash com os parâmetros do bloco e compara com o hash salvo
			if (!currentBlock.hash.equals(currentBlock.generateHash())) {
				return false;
			}

			//Verifica o indice do bloco atual com o do anterior
			if (currentBlock.index != previousBlock.index + 1) {
				return false;
			}

			//Verifica se o hash salvo do bloco anterior no atual está correto
			if (!currentBlock.previousHash.equals(previousBlock.hash)) {
				return false;
			}
		}
		return true;
	}
}
