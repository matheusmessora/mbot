package br.matheusmessora.cards.commands

import br.matheusmessora.cards.domain.PlayerScala

/**
  * Created by cin_mmessora on 7/13/17.
  */
case class AttackScala(_fieldPosition: Int,
											 _target: PlayerScala) extends CommandScala {

	def position = _fieldPosition

	def target = _target



}
