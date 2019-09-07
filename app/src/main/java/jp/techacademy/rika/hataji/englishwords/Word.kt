package jp.techacademy.rika.hataji.englishwords

import java.io.Serializable

class Word(val num: String, val word: String, val wordClass: String, val wordMean: String, val wordSentence: String, val wordSentenceMean: String) : Serializable {
}