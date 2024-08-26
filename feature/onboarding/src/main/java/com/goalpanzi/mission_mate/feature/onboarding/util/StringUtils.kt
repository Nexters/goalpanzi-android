package com.goalpanzi.mission_mate.feature.onboarding.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

fun List<String>.getStringRanges(text: String): List<IntRange> {
    return this.mapNotNull { target ->
        val index = text.indexOf(target, 0)
        if (index != -1) {
            (index until index + target.length)
        } else {
            null
        }
    }
}

fun styledTextWithHighlights(
    text: String,
    colorTargetTexts: List<String>,
    textColor: Color,
    targetTextColor: Color,
    weightTargetTexts: List<String> = emptyList(),
    underlineTargetTexts: List<String> = emptyList(),
    targetFontWeight: FontWeight = FontWeight.Normal
): AnnotatedString {
    return styledTextWithHighlightsWithIndices(
        text = text,
        colorTargetTextIndices = colorTargetTexts.getStringRanges(text),
        weightTargetTextIndices = weightTargetTexts.getStringRanges(text),
        underlineTargetTextIndices = underlineTargetTexts.getStringRanges(text),
        textColor = textColor,
        targetTextColor = targetTextColor,
        targetFontWeight = targetFontWeight
    )
}

fun styledTextWithHighlightsWithIndices(
    text: String,
    colorTargetTextIndices: List<IntRange>,
    weightTargetTextIndices: List<IntRange>,
    underlineTargetTextIndices: List<IntRange>,
    textColor: Color,
    targetTextColor: Color,
    targetFontWeight: FontWeight = FontWeight.Normal,
): AnnotatedString {
    return buildAnnotatedString {
        addStyle(style = SpanStyle(color = textColor), start = 0, end = text.length)
        append(text)
        colorTargetTextIndices.forEach { range ->
            addStyle(
                style = SpanStyle(color = targetTextColor),
                start = range.first,
                end = range.last + 1
            )
        }
        weightTargetTextIndices.forEach { range ->
            addStyle(
                style = SpanStyle(fontWeight = targetFontWeight),
                start = range.first,
                end = range.last + 1
            )
        }
        underlineTargetTextIndices.forEach { range ->
            addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = range.first,
                end = range.last + 1
            )
        }
    }
}