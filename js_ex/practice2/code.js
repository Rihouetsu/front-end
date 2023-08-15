//ラベルの取得
let label = document.getElementById("label");
let button1 = document.getElementById("change-text1");
let button2 = document.getElementById("change-text2");
let button3 = document.getElementById("change-text3");

//リンゴに変更をクリックしたら初期表示を変更する
button1.addEventListener("click",() =>{
    label.innerHTML="リンゴ";
});
//バナナに変更をクリックしたら初期表示を変更する
button2.addEventListener("click",() =>{
    label.innerHTML="バナナ";
});
//みかんに変更をクリックしたら初期表示を変更する
button3.addEventListener("click",() =>{
    label.innerHTML="みかん";
});
