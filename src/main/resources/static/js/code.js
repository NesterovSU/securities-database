let myStatus = document.getElementById("myStatus")
let resultList = document.getElementById("resultList")
let url = document.location.origin + '/api/' + myPath
let request = new XMLHttpRequest()
request.responseType = "json"
request.ontimeout = function(){
    err("сервер не отвечает")
}

function err(msg){
    myStatus.style.color = "red" 
    myStatus.innerText = msg
}

function succ(msg){
    myStatus.style.color = "green" 
    myStatus.innerText = msg
}

function showResult(response){
    let count = response.length
    succ("найдено " + count + " записей")
    response.forEach(result => {
        let listItem = document.createElement('div')
        let info =""
        for (const key in result) {
            info += "| " + key + "=" + result[key] + " "
        }   
        listItem.textContent = info + " |  "
        let deleteItem = document.createElement('a')
        deleteItem.textContent = "<<удалить>>"
        deleteItem.setAttribute("href", "")
        deleteItem.setAttribute("onclick", "deleting(event, '" + deleteFormula(result) + "')")
        listItem.appendChild(deleteItem)
        resultList.appendChild(listItem)
})}


function getList(){
resultList.innerHTML = ""
succ("запрос обрабатывается...")
request.open("GET", url)
request.send()
request.onload = function(){
    if(request.status == 200){
        showResult(request.response)
    }
    else{
        err(request.response.error)
}}}


function deleting(e, id){
    e.preventDefault()
    succ("")
    let formData = new FormData()
    formData.append(deleteBy, id)
    request.open("DELETE", url)
    request.send(formData)
    request.onload = function(e){
        e.preventDefault()
        if(request.status == 200){
            succ("запись удалена")
        }
        else if(request.status == 204){
            succ("такой записи не существует")
        }
        else{
            err(request.response.error)
}}}



window.onload = getList()