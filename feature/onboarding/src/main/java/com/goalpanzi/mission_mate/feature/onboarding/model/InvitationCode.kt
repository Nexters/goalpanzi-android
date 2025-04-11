package com.goalpanzi.mission_mate.feature.onboarding.model

class InvitationCode private constructor(
    private val symbols: List<InvitationCodeSymbol>
) {
    fun getCode(): String {
        return symbols.joinToString(EMPTY_VALUE) {
            when(it) {
                is InvitationCodeSymbol.Code -> it.symbol.toString()
                InvitationCodeSymbol.Blank -> EMPTY_VALUE
            }
        }
    }

    fun valueAt(index: Int): String {
        return when (val target = symbols[index]) {
            is InvitationCodeSymbol.Code -> target.symbol.toString()
            else -> EMPTY_VALUE
        }
    }

    fun input(
        text: String,
        startIndex: Int
    ): InvitationCode {
        if (startIndex !in symbols.indices) return this
        return if (text.isEmpty()) {
            deleteAt(startIndex)
        } else {
            inputSymbols(startIndex, *text.toCharArray())
        }
    }

    private fun inputSymbols(
        startIndex: Int,
        vararg symbols: Char
    ): InvitationCode {
        val newCodes = this.symbols.toMutableList()
        symbols.forEachIndexed { index, c ->
            if (startIndex + index >= CODE_LENGTH || !InvitationCodeSymbol.isValidSymbol(c)){
                return this
            }
            newCodes[startIndex + index] = InvitationCodeSymbol.Code(c)
        }
        return InvitationCode(newCodes)
    }

    fun deleteAt(index: Int): InvitationCode {
        if (index !in symbols.indices) return this
        val newCodes = symbols.toMutableList()
        newCodes[index] = InvitationCodeSymbol.Blank
        return InvitationCode(newCodes)
    }

    fun isValid(): Boolean {
        return symbols.size == CODE_LENGTH && symbols.all {
            isValidSymbol(it)
        }
    }

    private fun isValidSymbol(value: InvitationCodeSymbol): Boolean {
        return when (value) {
            is InvitationCodeSymbol.Code -> InvitationCodeSymbol.isValidSymbol(value.symbol)
            else -> false
        }
    }

    companion object {
        private const val CODE_LENGTH = 4
        private const val EMPTY_VALUE = ""

        fun create(initialCode: InvitationCodeSymbol = InvitationCodeSymbol.Blank): InvitationCode {
            val codes = List(CODE_LENGTH) { initialCode }
            return InvitationCode(codes)
        }
    }
}
