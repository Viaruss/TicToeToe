//TODO: code cleanup
$(document).ready(function() {

    let board, yourTurn, yourName, stompClient

    $("#nameInputSubmit").click(async function(){
        yourName = document.getElementById("nameInputField").value
        if(yourName === ""){
            alert("please enter a name")
        } else {
            let exists = await doesBoardExist(yourName)
            if(exists){
                await updateBoard(yourName)
                await prepareBoard()
                $("#popUpBox").fadeOut()
                $(".board").fadeIn()
            } else {
                $("#popUpBox").fadeOut()
                $("#noBoardBox").fadeIn()
            }
        }
    })

    $("#newGameButton").click(async function(){
        const response = await post(yourName, "http://localhost:8080/api/v1/board")
        if(!response){
            alert("something went wrong, try again")
        } else {
            await updateBoard(yourName)
            await prepareBoard()
            $("#noBoardBox").fadeOut()
            $(".board").fadeIn()
        }
    })

    $("#joinGameButton").click(function(){
        $("#noBoardBox").fadeOut()
        $("#joinGameBox").fadeIn()
    })

    $("#nameInputSubmit2").click(async function(){
        let opponentName = document.getElementById("nameInputField2").value
        if(opponentName === ""){
            alert("please enter a name")
        } else {
            const found = await doesBoardExist(opponentName)
            if(found){
                if(await updateBoard(opponentName)){
                    let success = await post(yourName, "http://localhost:8080/api/v1/board/" + opponentName)
                    if(success){
                        await updateBoard(yourName)
                        await prepareBoard()
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
        if(board["state"] === "") {
            if (this.textContent !== '') {
                alert("field already taken!")
            } else if(board["nowMoving"] !== yourTurn){
                alert("wait for your turn!")
            } else {
                if(!makeMove(this.id)){
                    alert("something went wrong, try again")
                }
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
        if (board["state"] === ""){
            let response = await post(atId, "http://localhost:8080/api/v1/board/move/" + board["id"])
            return response !== undefined;
        }
        return false
    }

    async function doesBoardExist(playerName) {
        let response = await fetch("http://localhost:8080/api/v1/board/exist/" + playerName)
        return await response.json()
    }
    function prepareBoard(){
        yourTurn = ["X", "O"].at(board["playerNames"].indexOf(yourName))
        $("#player1").text(board["playerNames"][0] + " - X")
        $("#player2").text((board["playerNames"][1] !== undefined ? board["playerNames"][1] : "player 2") + " - O") //insert placeholder "player 2" if second player is not present yet
        connectToSocket()
    }

    async function updateBoard(playerName){
        let response = await fetch("http://localhost:8080/api/v1/board/" + playerName)
        let json = await response.json()
        if(json["playerNames"].length < 2 || json["playerNames"].includes(yourName)){
            syncData(json)
            return true
        } else {
            return false
        }
    }

    function connectToSocket(){
        let socket = new SockJS("http://localhost:8080/api/v1/board/connections")
        stompClient = Stomp.over(socket)
        stompClient.connect({}, function () {
            stompClient.subscribe("/topic/gameplay/" + board["id"], function (response) {
                let data = JSON.parse(response.body)
                syncData(data)
                if(document.getElementById("player2").value !== board["playerNames"][1] + " - O" && board["playerNames"][1] !== undefined){
                    $("#player2").text(board["playerNames"][1] + " - O")
                }
                if(board["state"] !== ""){
                    if(board["state"] === "-")$("#title").text("Game is a draw")
                    else{
                        let winner = board["state"] === "X" ? board["playerNames"][0] : board["playerNames"][1]
                        if(yourName === winner){
                            alert("CONGRATULATIONS!!!")
                        } else {
                            alert("TRY AGAIN :c ")
                        }
                        $("#title").text(winner + " is the winner!")
                    }
                    $("#reset").fadeIn()
                    stompClient.disconnect()
                }
            })
        })
    }

    $("#reset").click(function clearBoard() {
        $("#noBoardBox").fadeIn()
        $(".board").fadeOut()
        $("#reset").fadeOut();
    })

    async function post(data, endpoint) {
        try {
            const response = await fetch(endpoint, {
                method: "POST",
                body: JSON.stringify(data),
            });
            const result = await response.json();
            return await result
        } catch (error) {
            console.error("Error:", error);
        }
    }
})
