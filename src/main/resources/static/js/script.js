$(document).ready(function() {

    let turn = 'X'

    $(".tic").click(function (){
        if (this.textContent === '') {
            this.textContent = turn
            turn = turn === 'X' ? 'O' : 'X'
            $("#title").text(turn + ' turn')
        }
    })

    $("#reset").click(function clearBoard() {
        $("#title").text('X turn')
        $(".tic").text('')
        turn = 'X'
    })
})