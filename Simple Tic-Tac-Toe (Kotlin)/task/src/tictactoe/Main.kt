package tictactoe

import kotlin.math.absoluteValue

fun main() {
    val input = "         " // Entrada do estado do tabuleiro
    val multiList = mutableListOf(
        mutableListOf(input[0], input[1], input[2]),
        mutableListOf(input[3], input[4], input[5]),
        mutableListOf(input[6], input[7], input[8])
    )

    printBoard(multiList)

    // Verifica o número de 'X' e 'O'
    var count = 0
    for (ch in input) {
        if (ch == 'X') {
            count++
        } else if (ch == 'O') {
            count--
        }
    }

    // Verifica se a contagem de 'X' e 'O' é inválida
    if (count.absoluteValue > 1) {
        println("Impossible")
        return
    }

    var playerMove = 'X'

    // Loop para a entrada de jogadas até o jogo terminar
    while (true) {
        val movimento = makeAMove(multiList, playerMove)

        if (movimento.isNotEmpty()) {
            println(movimento) // Imprime mensagem de erro se houver
            continue // Se a jogada não for válida, peça uma nova entrada
        }

        printBoard(multiList)

        val gameStatus = gameState(multiList)
        println(gameStatus)
        if (gameStatus != "Game not finished") {
            break // Sai do loop se o jogo terminar
        }

        playerMove = if (playerMove == 'X') 'O' else 'X'
    }
}

fun printBoard(multiList: MutableList<MutableList<Char>>) {
    println(
        """
        ---------
        | ${multiList[0].joinToString(" ")} |
        | ${multiList[1].joinToString(" ")} |
        | ${multiList[2].joinToString(" ")} |
        ---------
        """.trimIndent()
    )
}

fun gameState(multiList: MutableList<MutableList<Char>>): String {
    val lines = listOf(
        // Linhas
        listOf(multiList[0][0], multiList[0][1], multiList[0][2]),
        listOf(multiList[1][0], multiList[1][1], multiList[1][2]),
        listOf(multiList[2][0], multiList[2][1], multiList[2][2]),

        // Colunas
        listOf(multiList[0][0], multiList[1][0], multiList[2][0]),
        listOf(multiList[0][1], multiList[1][1], multiList[2][1]),
        listOf(multiList[0][2], multiList[1][2], multiList[2][2]),

        // Diagonais
        listOf(multiList[0][0], multiList[1][1], multiList[2][2]),
        listOf(multiList[0][2], multiList[1][1], multiList[2][0])
    )

    // Verifica se X ou O ganhou
    val xWins = lines.any { it.all { c -> c == 'X' } }
    val oWins = lines.any { it.all { c -> c == 'O' } }

    // Se ambos vencerem, o estado é impossível
    if (xWins && oWins) return "Impossible"

    // Se 'X' venceu
    if (xWins) return "X wins"

    // Se 'O' venceu
    if (oWins) return "O wins"

    // Verifica se o jogo ainda não acabou (espaços em branco)
    return if (multiList.flatten().contains(' ') || multiList.flatten().contains('_')) {
        "Game not finished"
    } else {
        "Draw"
    }
}

fun makeAMove(multiList: MutableList<MutableList<Char>>, playerMove: Char): String {
    val move = readln().split(" ")

    // Verifica se as entradas são números
    if (move.size != 2 || move[0].toIntOrNull() == null || move[1].toIntOrNull() == null) {
        return "You should enter numbers!"
    }

    // Verifica se as coordenadas estão dentro do intervalo permitido
    var linha = move[0].toInt()
    var coluna = move[1].toInt()

    if (linha !in 1..3 || coluna !in 1..3) {
        return "Coordinates should be from 1 to 3!"
    }

    linha -= 1 // Ajusta para índice
    coluna -= 1 // Ajusta para índice

    // Verifica se a célula já está ocupada
    if (multiList[linha][coluna] != ' ' && multiList[linha][coluna] != '_') {
        return "This cell is occupied! Choose another one!"
    }

    // Faz a jogada
    multiList[linha][coluna] = playerMove
    return ""
}
