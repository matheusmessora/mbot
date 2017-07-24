package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands._
import org.scalatest._


/**
  * Created by cin_mmessora on 7/11/17.
  */
class AttackTestNG extends FunSuite with Matchers with BeforeAndAfter {

  test("First player should be able to attack") {
    val game: GameScala = GameScala().start()
      .action(InvokeCard(0))
      .endTurn()
      .endTurn()

    val result = game.action(AttackScala(0, game.enemy))

    result.enemy.life shouldBe 29
  }

  test("Second player should be able to attack") {
    val game: GameScala = GameScala().start()
      .endTurn()
      .action(InvokeCard(0))

    val result = game.action(AttackScala(0, game.enemy))

    result.enemy.life shouldBe 29
  }

  test("Minion should be able to attack other minion") {
    val game: GameScala = GameScala().start()
      .action(InvokeCard(0))
      .endTurn()
      .action(InvokeCard(0))
      .endTurn()

    val result = game.action(AttackMinionScala(0, game.enemy.field.head))

    result.enemy.life shouldBe 29
  }

}
