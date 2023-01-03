let request;
function getData() {
    const url = "data.jsp";

    if(window.XMLHttpRequest){
        request = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        request= new ActiveXObject("Microsoft.XMLHTTP");
    }
    try {
        request.onreadystatechange=getInfo;
        request.open("POST",url,true);
        request.send();
    } catch (e){
        alert("Unable to connect to server");
    }
}
function getInfo(){
    if(request.readyState===4){
        document.getElementById('result').innerHTML = request.responseText;
    }
}
function generator() {
    const url = "filegenerator.jsp";

    if(window.XMLHttpRequest){
        request = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        request= new ActiveXObject("Microsoft.XMLHTTP");
    }
    try {
        request.open("POST",url,true);
        request.send();
    } catch (e){
        alert("Unable to connect to server");
    }
}

setInterval(getData,2000);