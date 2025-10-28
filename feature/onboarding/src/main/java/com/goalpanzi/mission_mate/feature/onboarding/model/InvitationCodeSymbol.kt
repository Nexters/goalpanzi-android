package com.goalpanzi.mission_mate.feature.onboarding.model

sealed class InvitationCodeSymbol{
    data object Blank: InvitationCodeSymbol()
    data class Code(val symbol: Char) : InvitationCodeSymbol()

    companion object {
        fun isValidSymbol(symbol: Char) : Boolean {
            return symbol.isDigit() || symbol.isUpperCase()
        }
    }
}
