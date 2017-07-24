package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands._
import org.scalatest._


/**
  * Created by cin_mmessora on 7/11/17.
  */
class GameTestNG extends FunSuite with Matchers with BeforeAndAfter {

	test("Should start game") {
		var game = GameScala().start()

		val currentPlayer = game.current
		currentPlayer.life shouldBe 30
		currentPlayer.deck.size shouldBe 25
		currentPlayer.hand.size shouldBe 5

		val enemy = game.enemy
		enemy.life shouldBe 30
		enemy.deck.size shouldBe 26
		enemy.hand.size shouldBe 4
	}

	test("First Player should Summond card") {
		var game = GameScala().start()
		game = game.action(InvokeCard(0))

		game.current.hand.size shouldBe 4
		game.current.field.size shouldBe 1
	}

	test("Second player should summon card after first one") {
		val game = GameScala()
			.start()
			.endTurn()

		game.enemy.hand.size shouldBe 5

		val result = game.action(InvokeCard(0))
		result.current.hand.size shouldBe 4
		result.current.field.size shouldBe 1
	}


}
