//定数
const CALC_NUM = 100;

function isPrime(x){
    for(let i=2;i<x;i++){
        if(x%i==0){
        return false;
    }
}
return true;
}

function main(){
let primes = new Array(CALC_NUM);
let k = 0;
for(let i = 2;k<CALC_NUM;i++){
    if(isPrime(i)){
        primes[k++]=i;
    }   
}
console.log(primes);
}
main();