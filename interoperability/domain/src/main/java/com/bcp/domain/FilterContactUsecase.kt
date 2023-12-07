package com.bcp.domain

import android.database.Cursor
import androidx.core.text.isDigitsOnly
import com.bcp.domain.model.ContactModel
import javax.inject.Inject

const val AREA_CODE = "51"
const val AREA_CODE_PLUS = "+51"

class FilterContactUsecase @Inject constructor() {

    private val listConstacts = mutableListOf<ContactModel>()

    companion object {
        const val INITIAL_CELLPHONE_NUMBER_STRING = "9"
        const val VALID_CELLPHONE_NUMBER_LENGTH = 9
        const val NAME = 1
        const val NUMBER = 3

    }

    operator fun invoke(data: Cursor?): MutableList<ContactModel> {
        listConstacts.clear()
        if (data?.moveToFirst() ?: false) {
            do {

                data?.getString(NUMBER).orEmpty().removingUnnecessaryCharts().reversed().take(11)
                    .reversed()
                    .takeIf {
                        (hasValidCodeArea(it) && it.substring(2)
                            .isValidNineDigitCharacter()) || it.isValidNineDigitCharacter()
                    }?.reversed()?.take(VALID_CELLPHONE_NUMBER_LENGTH)?.reversed()?.let { number ->
                        listConstacts.add(
                            ContactModel(
                                name = data?.getString(NAME) isnotTheSameTakeName data?.getString(
                                    NUMBER
                                ),
                                number = number,
                                normalizeNumber = number.normalizePhoneNumber()
                            )
                        )
                    }
            } while (data?.moveToNext() ?: false)

            return listConstacts.distinctBy {
                it.number
            }.toMutableList()

        } else {
            return listConstacts
        }
    }

    private infix fun String?.isnotTheSameTakeName(phoneNumber: String?): String {
        if (this.equals(phoneNumber)) {
            return ""
        } else
            return this.orEmpty()
    }

    fun String.normalizePhoneNumber(): String {
        val result = StringBuilder()
        this.reversed()
            .take(9).reversed()
            .mapIndexed { index, c ->
                result.append(c.toString())
                if (index == 2 || index == 5) {
                    result.append(" ")
                }
            }
        return result.toString()
    }

    fun String.removingUnnecessaryCharts() = this
        .filterNot { it.isWhitespace() }
        .replace("(", "", true)
        .replace(")", "", true)
        .replace("+", "", true)
        .replace("-", "", true)


    private fun hasValidCodeArea(number: String) = number.take(2).equals(AREA_CODE)

    private fun String.isValidNineDigitCharacter() =
        this.reversed().take(VALID_CELLPHONE_NUMBER_LENGTH).reversed()
            .startsWith(INITIAL_CELLPHONE_NUMBER_STRING) && this.length == VALID_CELLPHONE_NUMBER_LENGTH && this.isDigitsOnly()

}