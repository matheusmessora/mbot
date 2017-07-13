package br.matheusmessora.cards.domain

import scala.collection.mutable.ArrayBuffer

/**
  * Created by cin_mmessora on 7/12/17.
  */
class CardListScala {

	private val _cards = ArrayBuffer.empty[CardScala]

	def +=(card: CardScala): this.type  = {
		_cards += card
		this
	}

	def remove(position: Int): CardScala = {
		_cards.remove(position)
	}

	def size: Int = _cards.size




}
