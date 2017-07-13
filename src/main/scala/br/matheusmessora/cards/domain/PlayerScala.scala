package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands.InvokeCard

/**
  * Created by cin_mmessora on 7/11/17.
  */
class PlayerScala(private val _life: Int = 30,
                  private val _deck: DeckScala = new DeckScala,
                  private val _hand: CardListScala = new CardListScala,
                  private val _field: FieldScala = new FieldScala) {
	for( _ <- 1 to 4) {
		val card = _deck.drawCard()
		_hand += card
	}

	def life = _life
	def hand = _hand
	def deck = _deck
	def field = _field

	def start(): Unit = {
		hand += deck.drawCard

	}

	def action(command: InvokeCard): CardScala = {
		val card = hand.remove(command.position)
		field += card
		card
	}


}
