fun main() {
    testIsValidSudoku()
}


fun isValidSudoku(board: Array<Array<Char>>): Boolean {
    val size = board.size

    if (!isValidBoardDimensions(board, size)) return false

    val validChars = getValidCharacters(size)

    return areRowsValid(board, size, validChars) &&
            areColumnsValid(board, size) &&
            areSubGridsValid(board, size)
}

private fun isValidBoardDimensions(board: Array<Array<Char>>, size: Int): Boolean {
    val sqrtSize = Math.sqrt(size.toDouble()).toInt()
    if (sqrtSize * sqrtSize != size) return false

    return board.all { it.size == size }
}

private fun getValidCharacters(size: Int): Set<Char> {
    val validChars = mutableSetOf<Char>()

    if (size <= 9) {
        for (i in 1..size) {
            validChars.add('0' + i)
        }
    } else {
        for (i in 1..9) {
            validChars.add('0' + i)
        }
        val remaining = size - 9
        for (i in 0 until remaining) {
            validChars.add('A' + i)
        }
    }

    return validChars
}

private fun areRowsValid(board: Array<Array<Char>>, size: Int, validChars: Set<Char>): Boolean {
    for (i in 0 until size) {
        val seen = HashSet<Char>()
        for (j in 0 until size) {
            val cell = board[i][j]
            if (cell == '-') continue

            if (cell !in validChars) return false

            if (!seen.add(cell)) return false
        }
    }
    return true
}

private fun areColumnsValid(board: Array<Array<Char>>, size: Int): Boolean {
    for (j in 0 until size) {
        val seen = HashSet<Char>()
        for (i in 0 until size) {
            val cell = board[i][j]
            if (cell == '-') continue

            if (!seen.add(cell)) return false
        }
    }
    return true
}

private fun areSubGridsValid(board: Array<Array<Char>>, size: Int): Boolean {
    val sqrtSize = Math.sqrt(size.toDouble()).toInt()

    for (boxRow in 0 until sqrtSize) {
        for (boxCol in 0 until sqrtSize) {
            if (!isValidSubGrid(board, boxRow, boxCol, sqrtSize)) {
                return false
            }
        }
    }
    return true
}

private fun isValidSubGrid(
    board: Array<Array<Char>>,
    boxRow: Int,
    boxCol: Int,
    sqrtSize: Int
): Boolean {
    val seen = HashSet<Char>()

    for (i in 0 until sqrtSize) {
        for (j in 0 until sqrtSize) {
            val rowIndex = boxRow * sqrtSize + i
            val colIndex = boxCol * sqrtSize + j
            val cell = board[rowIndex][colIndex]

            // Skip empty cells
            if (cell == '-') continue

            // Check for duplicates in the sub grid
            if (!seen.add(cell)) return false
        }
    }
    return true
}


fun testIsValidSudoku() {

    // Test case 1: Valid 3x3 Sudoku when the number represent with "-"
    val case0 = arrayOf(
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-'),
        arrayOf('-', '-', '-', '-', '-', '-', '-', '-', '-')
    )
    check("all case passed", isValidSudoku(case0), true)


// Test case 1: Valid 3x3 Sudoku
    val case1 = arrayOf(
        arrayOf('5', '2', '3', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("all case passed", isValidSudoku(case1), true)


    // Test case 2: Invalid row (duplicate in first row)
    val case2 = arrayOf(
        arrayOf('5', '3', '3', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')


    )
    check("duplicate in row", isValidSudoku(case2), false)


    // Test case 3: Invalid column (duplicate in first column)
    val case3 = arrayOf(
        arrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('5', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("duplicate in column", isValidSudoku(case3), false)


    // Test case 4: Invalid subgrid (duplicate in top-left 3x3 box)
    val case4 = arrayOf(
        arrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '5', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("duplicate in box", isValidSudoku(case4), false)


    // Test case 5: Invalid column size
    val case5 = arrayOf(
        arrayOf('5', '3', '-', '-', '7', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '5', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Invalid column size", isValidSudoku(case5), false)

    // Test case 6: Invalid row size
    val case6 = arrayOf(
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '5', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6')
    )
    check("Invalid row size", isValidSudoku(case6), false)


    // Test case 7: Invalid character (non-digit and non-empty)
    val case7 = arrayOf(
        arrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '!', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '7', '9')
    )
    check("Invalid character", isValidSudoku(case7), false)


    // Test case 8: Value out of valid range (1-9)
    val case8 = arrayOf(
        arrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('-', '-', '-', '-', '8', '-', '-', '-', 'a')
    )
    check("out of allowed range (1-9)", isValidSudoku(case8), false)


    // Test case 9: Value duplicated in both row and column
    val case9 = arrayOf(
        arrayOf('5', '3', '-', '-', '7', '-', '-', '-', '-'),
        arrayOf('6', '-', '-', '1', '9', '5', '-', '-', '-'),
        arrayOf('-', '9', '8', '-', '-', '-', '-', '6', '-'),
        arrayOf('8', '-', '-', '-', '6', '-', '-', '-', '3'),
        arrayOf('4', '-', '-', '8', '-', '3', '-', '-', '1'),
        arrayOf('7', '-', '-', '-', '2', '-', '-', '-', '6'),
        arrayOf('-', '6', '-', '-', '-', '-', '2', '8', '-'),
        arrayOf('-', '-', '-', '4', '1', '9', '-', '-', '5'),
        arrayOf('5', '-', '-', '-', '8', '-', '5', '7', '9')
    )
    check("Duplicate value in both row and column", isValidSudoku(case9), false)

}


fun check(name: String, result: Boolean, correctResult: Boolean) {
    if (result == correctResult) {
        println("Success - $name")
    } else {
        println("Failed - $name")

    }
}

