$(document).ready(function() {

    let board, yourTurn, yourName

    $("#nameInputSubmit").click(async function(){
        yourName = document.getElementById("nameInputField").value
        if(yourName === ""){
            alert("please enter a name")
        } else {
            let exists = await doesBoardExist(yourName)
            if(exists){
                await updateBoard(yourName)
                await assertTurns()
                $("#popUpBox").fadeOut()
                $(".board").fadeIn()
            } else {
                $("#popUpBox").fadeOut()
                $("#noBoardBox").fadeIn()
            }
        }
    })

    $("#newGameButton").click(async function(){
        const response = await fetch("http://localhost:8080/api/v1/board/get/new/" + yourName)
        if(!response.ok){
            alert("something went wrong, try again")
        } else {
            await updateBoard(yourName)
            $("#noBoardBox").fadeOut()
            $(".board").fadeIn()
        }
    })

    $("#joinGameButton").click(function(){
        $("#noBoardBox").fadeOut()
        $("#joinGameBox").fadeIn()
    })

    $("#nameInputSubmit2").click(async function(){
        if(yourName === ""){
            alert("please enter a name")
        } else {
            const found = await doesBoardExist(document.getElementById("nameInputField2").value)
            if(found){
                if(await updateBoard(document.getElementById("nameInputField2").value)){
                    const response = await fetch("http://localhost:8080/api/v1/board/joinGame/" + document.getElementById("nameInputField2").value +
                        "/" + yourName)
                    let success = await response.json()
                    if(success){
                        await updateBoard(yourName)
                        $("#joinGameBox").fadeOut()
                        $(".board").fadeIn()
                    } else {
                        alert("something went wrong")
                    }
                }
                else {
                    alert("player is already playing with someone else")
                }
            } else {
                alert("player not found")
            }
        }
    })

    $(".tic").click(function (){
        if (this.textContent !== '') {
            alert("field already taken!")
        } else if(board["nowMoving"] !== yourTurn){
            alert("wait for your turn!")
        } else {
            if(!makeMove(this.id)){
                alert("something went wrong, try again")
            }
        }
    })
    function syncData(data){
        $("#title").text(data["nowMoving"] + ' turn')
        let htmlFields = document.getElementById("gameBoard").children
        for(let i = 0; i < htmlFields.length; i++) {
            htmlFields.item(i).textContent = data["fields"][i]
        }
        board = data
    }

    async function makeMove(atId){
        let response = await fetch("http://localhost:8080/api/v1/board/move/" + board["id"] + "/" + atId)
        let json = await response.json()
        if (json !== undefined){
            await syncData(json)
            return true
        }
        return false
    }

    async function doesBoardExist(playerName) {
        let response = await fetch("http://localhost:8080/api/v1/board/ifExists/fromPlayer/" + playerName)
        return await response.json()
    }
    function assertTurns(){
        yourTurn = ["X", "O"].at(board["playerNames"].indexOf(yourName))
        $("#player1").text(board["playerNames"][0] + " - X")
        $("#player2").text((board["playerNames"][1] !== undefined ? board["playerNames"][1] : "player 2") + " - O") //insert placeholder "player 2" if second player is not present yet
    }

    async function updateBoard(playerName){
        let response = await fetch("http://localhost:8080/api/v1/board/get/fromPlayer/" + playerName)
        let json = await response.json()
        if(json["playerNames"].length < 2 || json["playerNames"].includes(yourName)){
            syncData(json)
            return true
        } else {
            return false
        }
    }

//temp

    $("#reset").click(function clearBoard() {
        $("#title").text('X turn')
        $(".tic").text('')
    })

})