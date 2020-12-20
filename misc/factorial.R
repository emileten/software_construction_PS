library(tictoc)

RecursiveFactorial <- function(n){

	if(n==0){
		return(1)
	} else {
		return(RecursiveFactorial(n-1)*n)
	}

}

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


tic()
RecursiveFactorial(200)
toc()

tic()
IterativeFactorial(200)
toc()

#Recursion is slow ! Large stack. Passing n=4000 breaks the code for the recursive version (too large stack)