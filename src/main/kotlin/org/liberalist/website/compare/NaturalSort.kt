package org.liberalist.website.compare

import org.liberalist.website.compare.NaturalSortUtil.isDigit
import org.liberalist.website.compare.NaturalSortUtil.splitNumbers

object NaturalSort : Comparator<String> {
    override fun compare(a: String, b: String): Int =
            if (a == b) {
                0
            } else {
                compareLists(splitNumbers(a), splitNumbers(b))
            }

    private fun compareLists(leftList: List<String>, rightList: List<String>): Int {
        if (leftList.isEmpty()) {
            return if (rightList.isEmpty()) {
                0
            } else {
                -1
            }
        } else {
            return if (rightList.isEmpty()) {
                1
            } else {
                val left = leftList[0]
                val right = rightList[0]
                val partComparison = compareParts(left, right)
                if (partComparison == 0) {
                    compareLists(leftList.drop(1), rightList.drop(1))
                } else {
                    partComparison
                }
            }
        }
    }

    private fun compareParts(left: String, right: String): Int =
            if (isDigit(left[0])) {
                if (isDigit(right[0])) {
                    compareNumbers(left, right)
                } else {
                    -1
                }
            } else {
                if (isDigit(right[0])) {
                    1
                } else {
                    left.compareTo(right)
                }
            }

    private fun compareNumbers(left: String, right: String): Int {
        val maxSize = maxOf(left.length, right.length)
        val paddedLeft = pad(left, maxSize)
        val paddedRight = pad(right, maxSize)
        val paddedCompare = paddedLeft.compareTo(paddedRight)
        return if (paddedCompare == 0) {
            left.length.compareTo(right.length)
        } else {
            paddedCompare
        }
    }

    private fun pad(s: String, size: Int): String {
        val numZeroes = size - s.length
        val zeroes = "0".repeat(numZeroes)
        return zeroes + s
    }
}
