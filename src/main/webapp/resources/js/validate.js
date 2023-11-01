
document.addEventListener("DOMContentLoaded", function (){
    document.getElementById("coordinates-form:submit-button").
    addEventListener('click', (event) => {
        validateAll();
        let x = document.querySelector("input[type=radio]:checked");
        let y = document.getElementById("coordinates-form:input-y-coordinate");
        console.log(y.value);
        console.log(r);
        coordinatesArray.push(x);
        coordinatesArray.push(y);
        if(r!==0) {
            if (checkResult(parseInt(x.value), y.value, r)) {
                console.log("здесь зеленый");
                drawDotsGreen(x.value, y.value);
            } else {
                console.log("здесь красный");
                drawDotsRed(x.value, y.value);
            }
        }
    });
    document.getElementById("coordinates-form:clear-button").
        addEventListener('click', () =>{
            coordinatesArray.length = 0;
            r = 0;
            calculator.setState(stateDefault);
    })
});
function check_Y() {
    let Y_text = document.getElementById("coordinates-form:input-y-coordinate");
    let Y_value = Y_text.value;
    let Y = Number(Y_value);
    if(Y_value.trim() === ""){
        Y_text.setCustomValidity("Поле не может быть пустым!");
        return false;
    }
    else if(isNaN(Y)){
        Y_text.setCustomValidity("В поле должно быть лишь число!");
        return false;
    }else if(Y<-3 || Y > 3){
        Y_text.setCustomValidity("Значения должны быть в диапозоне от -3 до 3!");
        return false;
    }
        Y_text.setCustomValidity("");
        console.log("all is good!");
        return true;
}
function check_X(){
    let x_text = document.querySelector("input[type=radio]:checked");
    let x = parseInt(x_text);
    if(x===null){
        x_text.setCustomValidity("Выберете X");
        return false;
    }
    x_text.setCustomValidity("");
    return true;
}
function validateAll(){
    if(!(check_Y() && check_X())){
        return;
    }
}





