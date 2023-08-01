//ラベルの取得
let label = document.getElementById("label");
let button1 = document.getElementById("change-text1");
let button2 = document.getElementById("change-text2");
let button3 = document.getElementById("change-text3");


button1.addEventListener("click",()=>{
    label.innerHTML="リセット";
});

button2.addEventListener("click",()=>{
    label.innerHTML="1を加算";
});

button3.addEventListener("click",()=>{
    label.innerHTML="1を減算";
});