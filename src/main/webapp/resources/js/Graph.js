let elt = document.getElementById('calculator');
const commandLinks = document.querySelectorAll(".input-button");
let r = 0;
let coordinatesArray = [];
commandLinks.forEach(function (link){
    link.addEventListener('click', (event) =>{
        r = parseFloat(link.textContent.trim());
    })
})
    elt.addEventListener('click', (e)=>
    {
        handleGraphClick(e, r);
    });

let calculator = Desmos.GraphingCalculator(elt, {
    keypad: false,
    expressions: false,
    settingsMenu: false,
    invertedColors: true,
    xAxisLabel: 'x',
    yAxisLabel: 'y',
    xAxisStep: 0.5,
    yAxisStep: 0.5,
    xAxisArrowMode: Desmos.AxisArrowModes.POSITIVE,
    yAxisArrowMode: Desmos.AxisArrowModes.POSITIVE,

});

calculator.setMathBounds({
    left: -6.5,
    right: 6.5,
    bottom: -6.5,
    top: 6.5
});
let stateDefault = calculator.getState();

// Test function

// function drawDotsWhenChangeR(x, y){
//     if(coordinatesArray.length!=0) {
//         commandLinks.forEach(function (link) {
//             console.log(coordinatesArray);
//             link.addEventListener("click", function (event) {
//                 if (checkResult(x, y, r)) {
//                     drawDotsGreen(x, y, r);
//                 } else {
//                     drawDotsRed(x, y, r);
//                 }
//             });
//
//         });
//     }
//
// }


commandLinks.forEach(function(link) {
    link.addEventListener("click", function(event) {
        // Обновляем значение R в графике
        drawGraph(r);
        console.log(coordinatesArray)
        for(let i=0; i<coordinatesArray.length; i+=2) {
            let x = coordinatesArray[i];
            let y = coordinatesArray[i + 1];
            console.log(typeof (x));
            console.log(typeof (y));
            if (r !== 0) {
                if (checkResult(x, y, r)) {
                    drawDotsGreen(x, y);
                } else {
                    drawDotsRed(x, y);
                }
            }
        }
    });
});


function handleGraphClick(evt, r) {

    const rect = elt.getBoundingClientRect();
    const x = evt.clientX - rect.left;
    const y = evt.clientY - rect.top;
    // Note, pixelsToMath expects x and y to be referenced to the top left of
    // the calculator's parent container.
    const mathCoordinates = calculator.pixelsToMath({x: x, y: y});

    document.getElementById("graphSelect:graph-x").value = mathCoordinates.x;
    document.getElementById("graphSelect:graph-y").value = mathCoordinates.y;

    if(r!==0) {
        coordinatesArray.push(mathCoordinates.x);
        coordinatesArray.push(mathCoordinates.y);
        if (checkResult(mathCoordinates.x, mathCoordinates.y, r)) {
            drawDotsGreen(mathCoordinates.x, mathCoordinates.y, r);
        } else {
            console.log(r);
            drawDotsRed(mathCoordinates.x, mathCoordinates.y, r);
        }
    }
    updateBeanValues();
    updateTime();
}
function drawGraph(rValue) {
    // Предотвращаем стандартную отправку формы

    // Строим область на графике на основе выбранных значений
    calculator.setExpression({
        id: '1',
        latex: 'y\\ge' + -rValue + '+x\\left\\{0\\le x\\le ' + rValue + '\\left\\{y\\le0\\right\\}\\right\\}',
        color: '#1ea2a8',
    });

    calculator.setExpression({
        id: '2',
        latex: 'x\\ge' + -rValue + '\\left\\{0\\le y\\le\\frac{' + rValue + '}{2}\\left\\{x\\le0\\right\\}\\right\\}',
        color: '#1ea2a8',
    });
    calculator.setExpression({
        id: '3',
        latex: 'y\\ge-\\sqrt{' + rValue + '^{2}-x^{2}}\\left\\{' + -rValue + '\\le x\\le0\\left\\{y\\le0\\right\\}\\right\\}',
        color: '#1ea2a8',
    });


}




function drawDotsGreen(x, y) {
    calculator.setExpression({
        id: x + '' + y,
        latex: '\\left(' + x + ',' + y + '\\right)',
        color: "#910fe7",
        pointStyle: Desmos.Styles.CROSS
    })
}

function drawDotsRed(x, y){
    calculator.setExpression({
        id: x + '' + y,
        latex: '\\left(' + x + ',' + y + '\\right)',
        color: "#00e3ff",
        pointStyle: Desmos.Styles.CROSS
    })
}









