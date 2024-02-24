$(document).ready(function() {

    $("#nameInputSubmit").click(async function(){
        if(document.getElementById("nameInputField").value === ""){
            alert("please enter a name")
        } else {
            let exists = await doesBoardExist(document.getElementById("nameInputField").value)
            if(exists){
                await updateBoard(document.getElementById("nameInputField").value)
                $("#popUpBox").fadeOut()
                $(".board").fadeIn()
            } else {
                $("#popUpBox").fadeOut()
                $("#noBoardBox").fadeIn()
            }
        }
    })

    $("#newGameButton").click(async function(){
        const response = await fetch("http://localhost:8080/api/v1/board/get/new/" + document.getElementById("nameInputField").value)
        if(!response.ok){
            alert("something went wrong, try again")
        } else {
            await updateBoard(document.getElementById("nameInputField").value)
            $("#noBoardBox").fadeOut()
            $(".board").fadeIn()
        }
    })

    $("#joinGameButton").click(function(){
        $("#noBoardBox").fadeOut()
        $("#joinGameBox").fadeIn()
    })

    $("#nameInputSubmit2").click(async function(){
        if(document.getElementById("nameInputField").value === ""){
            alert("please enter a name")
        } else {
            const found = await doesBoardExist(document.getElementById("nameInputField2").value)
            if(found){
                if(await updateBoard(document.getElementById("nameInputField2").value)){
                    const response = await fetch("http://localhost:8080/api/v1/board/joinGame/" + document.getElementById("nameInputField2").value +
                        "/" + document.getElementById("nameInputField").value)
                    let success = await response.json()
                    if(success){
                        await updateBoard(document.getElementById("nameInputField").value)
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

    async function doesBoardExist(playerName) {
        let response = await fetch("http://localhost:8080/api/v1/board/ifExists/fromPlayer/" + playerName)
        return await response.json()
    }


    async function updateBoard(playerName){
        let response = await fetch("http://localhost:8080/api/v1/board/get/fromPlayer/" + playerName)
        let json = await response.json()
        if(json["playerNames"].length < 2 || json["playerNames"].includes(document.getElementById("nameInputField").value)){
            $("#player1").text(json["playerNames"][0])
            $("#player2").text(json["playerNames"][1])
            $("#title").text(json["nowMoving"] + ' turn')
            let htmlFields = document.getElementById("gameBoard").children
            for(let i = 0; i < htmlFields.length; i++) {
                htmlFields.item(i).textContent = json["fields"][i]
            }
            return true
        } else {
            return false
        }
    }


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