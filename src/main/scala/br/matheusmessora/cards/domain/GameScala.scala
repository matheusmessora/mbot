package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands.{AttackScala, CommandScala, InvokeCard}


/**
  * Created by cin_mmessora on 7/11/17.
  */
case class GameScala(current: PlayerScala = PlayerScala("blue"),
										 enemy: PlayerScala = PlayerScala("red")){

  def endTurn(): GameScala = copy(enemy.start(), current)

  def action(command: CommandScala): GameScala = command match {
    case InvokeCard(position) => {
      copy(current.invokeCard(position), enemy)
    }

    case command: AttackScala =>
      val selectedCard = current.field.apply(command.position)
      copy(current, command.target.receiveDamage(selectedCard.power))

    case _ => throw new IllegalStateException() // TODO: refactoring
  }

	def start(): GameScala = copy(current.drawCard(5), enemy.drawCard(4))

}
