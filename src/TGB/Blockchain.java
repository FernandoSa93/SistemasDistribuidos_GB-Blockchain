package TGB;

import java.util.Stack;

class Blockchain {

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
		String previousHash = this.getLastBlock().hash;

		Block block = new Block(index, previousHash, data, difficulty);

		this.index++;
		this.blocks.push(block);
	}

	public Boolean isValid() {
		for (int i = 1; i < this.blocks.size(); i++) {
			Block currentBlock = this.blocks.get(i);
			Block previousBlock = this.blocks.get(i - 1);

			if (currentBlock.hash != currentBlock.generateHash()) {
				return false;
			}

			if (currentBlock.index != previousBlock.index + 1) {
				return false;
			}

			if (currentBlock.previousHash != previousBlock.hash) {
				return false;
			}
		}
		return true;
	}
}
