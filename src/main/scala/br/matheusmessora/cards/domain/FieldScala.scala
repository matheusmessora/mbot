package br.matheusmessora.cards.domain

/**
  * Created by cin_mmessora on 7/13/17.
  */
class FieldScala {
	private val _slots: CardListScala = new CardListScala
	private val _graveyard: CardListScala = new CardListScala

	def slots = _slots
	def graveyard = _graveyard
	def size = _slots.size

	def +=(card: CardScala) = {
		_slots += card
	}


}
