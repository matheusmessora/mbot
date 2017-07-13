package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands.InvokeCard
import org.scalatest._


/**
  * Created by cin_mmessora on 7/11/17.
  */
class GameTestNG extends FunSuite with Matchers with BeforeAndAfter {

	var game: GameScala = _

	before {
		game = new GameScala
		game.start()
	}

	test("Should start game") {
		val currentPlayer = game.currentPlayer
		currentPlayer.life shouldBe 30
		currentPlayer.deck.size shouldBe 25
		currentPlayer.hand.size shouldBe 5

		val enemy = game.enemy
		enemy.life shouldBe 30
		enemy.deck.size shouldBe 25
		enemy.hand.size shouldBe 5
	}

	test("First Player should Summond card") {
		game.action(new InvokeCard(_position = 0))

		game.currentPlayer.hand.size shouldBe 4
		game.currentPlayer.field.size shouldBe 1
	}

	test("Second player should summond card after first one") {
		game.endTurn()
		game.enemy.hand.size shouldBe 5

		game.action(new InvokeCard(_position = 0))
		game.currentPlayer.hand.size shouldBe 4
		game.currentPlayer.field.size shouldBe 1
	}


}
