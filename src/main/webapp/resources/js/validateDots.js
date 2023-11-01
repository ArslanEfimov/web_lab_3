function checkResult(x, y, r){
    return (checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r));
}

function checkRectangle(x, y, r){
    return ((x<=0 && y>=0) && (y<=r/2) && (x>=-r));
}
function checkTriangle(x, y, r){
    return ((x>=0 && y<=0) && (y>=-r + x));
}

function checkCircle(x, y, r){
    return ((x<=0 && y<=0) && (Math.pow(r, 2) >= Math.pow(x,2) + Math.pow(y,2)));
}
