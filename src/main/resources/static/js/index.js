//--------------model--------------------
let request = new XMLHttpRequest();
request.responseType = "json"
request.ontimeout = function(){
    err("сервер не отвечает")
}

let myStatus = document.getElementById("myStatus")
let infoResult = document.getElementById("infoResult")


function putEntry(form){
    request.open(form.method, form.action)
    request.send(new FormData(form))
    request.onload = function(){
    if(request.status==200){
        succ("запись внесена")
        form.reset()
    }        
    else{
        err(request.response.error)
}}}


function postFile(form){
    if (form.file.value == ""){
        err("файлы не выбраны")
        return
    }
    request.open(form.method, form.action)
    request.send(new FormData(form))
    request.onload = function(){      
    if(request.status==200){
        succ("файлы успешно отправлены")
        form.reset()
    }        
    else{
        err(request.response.error)
}}}

function requestInfo(form){
    infoResult.innerHTML = ""
    let param = "?secid=" + 
        encodeURIComponent(form.secid.value) +
        "&date=" + encodeURIComponent(form.date.value);
    request.open(form.method, form.action + param)
    request.send()
    request.onload = function(){   
    if(request.status==200){
        infoForm.reset()
        showInfo(request.response)
    }
    else{
        err(request.response.error)
}}}


function ePrevent(e){
    e.preventDefault()
    succ("запрос обрабатывается...")
}

function err(msg){
    myStatus.style.color = "red" 
    myStatus.innerText = msg
}

function succ(msg){
    myStatus.style.color = "green" 
    myStatus.innerText = msg
}

function showInfo(response){
    let count = response.length
    succ("найдено " + count + " записей")
    response.forEach(result => {
        let listItem = document.createElement('div')
        let line = document.createElement('hr')
        let info =""
        for (const key in result) {
            info += "| " + key + "=" + result[key] + " "
        }
        listItem.textContent = info + " |"
        infoResult.appendChild(listItem)
        infoResult.appendChild(line)
})}



document.secFile.onsubmit = function(e){
    ePrevent(e)
    postFile(document.secFile)
}

document.histFile.onsubmit = function(e){
    ePrevent(e)
    postFile(document.histFile)
}

document.histForm.onsubmit = function(e){
    ePrevent(e)
    putEntry(document.histForm)
}

document.secForm.onsubmit = function(e){
    ePrevent(e)
    putEntry(document.secForm)
}

document.infoForm.onsubmit = function(e){
    ePrevent(e)
    requestInfo(document.infoForm)
}