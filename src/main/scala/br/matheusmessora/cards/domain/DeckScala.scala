package br.matheusmessora.cards.domain

/**
  * Created by cin_mmessora on 7/12/17.
  */
class DeckScala {

	private val _cards = new CardListScala
	for(_ <- 1 to 30){
		_cards += new CardScala
	}

	def drawCard() : CardScala = {
		_cards.remove(0)
	}

	def size : Int = _cards.size


}
