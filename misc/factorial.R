RecursiveFactorial <- function(n){

	if(n==0){
		return(1)
	} else {
		return(RecursiveFactorial(n-1)*n)
	}

}