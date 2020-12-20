library(tictoc)

runFactorial=FALSE
runFibonacci=TRUE

#' @return the factorial of n
RecursiveFactorial <- function(n){

	print(paste0('stack layer : ', n))

	if(n==0){
		return(1)
	} else {
		return(RecursiveFactorial(n-1)*n)
	}

}

#' @return the factorial of n
IterativeFactorial <- function(n){

	if(n==0){
		return(1)
	} else {

		result = 1

		for(i in (1:n)){

			result = result*i

		}

		return(result)
	}

}


if(runFactorial){
	tic()
	RecursiveFactorial(200)
	toc()

	tic()
	IterativeFactorial(200)
	toc()
}

#Recursion is slow ! Large stack. Passing n=4000 breaks the code for the recursive version (too large stack)

#' @return the first n elements of the Fibonacci sequence
RecursiveFibonacci <- function(n){

	if(n==1){
		return(0)
	} else if (n==2){
		return(1)
	} else if (n>2){
		return(RecursiveFibonacci(n-2)+RecursiveFibonacci(n-1))
	} else {
		stop("n must be a positive integer")
	}

}

if(runFibonacci){
	RecursiveFibonacci(7)
}