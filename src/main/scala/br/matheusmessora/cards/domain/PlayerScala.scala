package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands.{AttackScala, CommandScala, InvokeCard}

/**
  * Created by cin_mmessora on 7/11/17.
  */
case class PlayerScala(name: String,
											 life: Int = 30,
											 deck: List[CardScala] = List.fill(30)(new CardScala),
											 hand: List[CardScala] = List.empty,
											 field: List[CardScala] = List.empty,
											 graveyard: List[CardScala] = List.empty) {

	def drawCard(i: Int): PlayerScala = {
		for( _ <- 1 to i){
			draw(this)
		}

		(1 to 4).map(_ => draw()).last
	}

	def draw(player: PlayerScala): PlayerScala = {
		val selectedCard: CardScala = player.deck.head
		val newHand = player.hand :+ selectedCard
		val newDeck = player.deck.drop(1)
		player.copy(deck = newDeck, hand = newHand)
	}

	def draw(): PlayerScala = {
		 val selectedCard: CardScala = deck.head
		 val newHand = hand :+ selectedCard
		 val newDeck = deck.drop(1)
		 copy(deck = newDeck, hand = newHand)
	 }

	def invokeCard(position: Int): PlayerScala = {
		val selectedCard = hand.apply(position)
		val newHand = hand.filterNot(_.equals(selectedCard))
		val newField = field :+ selectedCard
		copy(hand = newHand, field = newField)
	}

	def receiveDamage(power: Int): PlayerScala = copy(life = life - power)

	def start(): PlayerScala = {
		var newPlayer = draw()
		for( i <- 1 to 4) {
			newPlayer = newPlayer draw
		}
		newPlayer
	}

	def action(_command: CommandScala): PlayerScala = _command match {
		case InvokeCard(position) => invokeCard(position)

		case command: AttackScala =>
			val selectedCard = field.apply(command.position)
			command.target.receiveDamage(selectedCard.power)

		case _ => throw new IllegalStateException()
	}


}
