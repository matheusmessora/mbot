package br.matheusmessora.cards.domain

import br.matheusmessora.cards.commands.InvokeCard

/**
  * Created by cin_mmessora on 7/11/17.
  */
class GameScala(private val _bluePlayer: PlayerScala = new PlayerScala,
                private val _redPlayer: PlayerScala = new PlayerScala){

	def endTurn() = {
		if(currentPlayer == _bluePlayer){
			_current = _redPlayer
		}else {
			_current = _bluePlayer
		}

		_current.start()
	}

	def action(command: InvokeCard) = {
		_current.action(command)
	}

	def start(): Unit = {
		_current = _bluePlayer

		_bluePlayer.start()
	}

	def enemy: PlayerScala = {
		if(_current == _bluePlayer){
			_redPlayer
		}
		_bluePlayer
	}

	def currentPlayer: PlayerScala = _current

	private var _current: PlayerScala = _bluePlayer



}
