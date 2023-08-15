//ラベルの取得
let result = document.getElementById("text-result");
let buttonReset = document.getElementById("change-text1");
let buttonAdd = document.getElementById("change-text2");
let buttonSub = document.getElementById("change-text3");

//値を文字列で定数にしておく
const BASE_TEXT = "値:"
//計算結果を格納する変数
let resultValue = 0;
//値を表示するメソッド
function changeResultText(){
    result.innerHTML = BASE_TEXT + resultValue;
}

//値を表示するメソッド
function changeResultValue(setValue){
    result.innerHTML = resultValue + setValue;
}

//リセットボタンを押したとき
buttonReset.addEventListener("click",()=>{
    resultValue = 0;
    changeResultText();
});
//1を加算ボタンを押したとき
buttonAdd.addEventListener("click",()=>{
    calculateResultValue(1);
    changeResultText();
});
//1を減算ボタンを押したとき
buttonSub.addEventListener("click",()=>{
    calculateResultValue(-1);
    changeResultText();
});




    
   
