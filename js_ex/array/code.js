//配列の初期化
let arr = [1,2,3,"Alice",12.5]
//中身の確認
console.log(arr);
//配列の長さの確認
console.log(arr.length);
//配列に値の追加push
arr.push("追加しました");
//中身の確認
console.log(arr);
//中身の削除一番最後に書いたデータを削除できる
arr.pop();
console.log(arr);

//配列の作成
let arr2 = new Array(5);
arr2[0] = 23;
console.log(arr2);

//for-ofをしようして内容を出力する
for(let a of arr){
        console.log(a);
}