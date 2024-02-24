$(document).ready(function() {

    let playerName = prompt("Whats Your name?", "player name")

    fetch("http://localhost:8080/api/v1/board/get/fromPlayer/" + playerName)
        .then(resp => resp.json())
        .then(json => {
            $("#player1").text(json["playerNames"][0])
            $("#player2").text(json["playerNames"][1])
            $("#title").text(json["nowMoving"] + ' turn')
            let htmlFields = document.getElementById("gameBoard").children
            for(let i = 0; i < htmlFields.length; i++) {
                htmlFields.item(i).textContent = json["fields"][i]
            }
        })



//temp
    $(".tic").click(function (){
        if (this.textContent === '') {
            $("#title").text(this.textContent === 'X' ? 'O' : 'X' + ' turn')
        }
    })

    $("#reset").click(function clearBoard() {
        $("#title").text('X turn')
        $(".tic").text('')
    })
})