$(function(){
 //この中にjQueryの処理を書く
 //要素の取得
 let text = $("#text-1");
 //要素の内容の取得Helloを取得
 console.log(text.html());
//HelloをGoodByeに変更する
 text.html("GoodBye")

 //リンクの話
 //リンク要素を取得する
 let link = $("#link-1");
 //リンクのURLを取得する
 console.log(link.attr("href"));

 //リンクの書き換え
 link.attr("href","https://kinarino.jp/cat8/38722");
 link.attr("target","_blank");

 //cssの話
 let texts = $(".text-class");
 //取ってきた要素に色をつける
 texts.css("color","red");
 texts.eq(0).css("color","red");
 texts.eq(2).css("color","red");
});