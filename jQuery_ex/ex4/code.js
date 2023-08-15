$(function(){
    $(".sample_btn").click(function(){
         $.ajax({
            url:'test.html',
            type:'GET',
            dataType:'html'
         }).done(function(data){
            //通信が成功した場合
            $(".result").html(data);
         }).fail(function(){
            //通信が失敗してしまった時
            console.log("通信が失敗しました");
         });
    });

});