#include<iostream>
#include<cstdlib>
#include<omp.h>
#include<ctime>

using namespace std;

void Seqbubblesort(int arr[],int n){
	
	for(int i=0;i<n-1;i++){
		for(int j=0;j<n-i-1;j++){
			if(arr[j]>arr[j+1])
			{
				int temp=arr[j];
				arr[j]=arr[j+1];
				arr[j]=temp;
			}
		}
	}
	
}

void Parbubblesort(int arr[],int n){
	
	#pragma omp parallel
	{
	
	for(int i=0;i<n-1;i++){
        
        #pragma omp for
		for(int j=0;j<n-i-1;j++){
			if(arr[j]>arr[j+1])
			{
				int temp=arr[j];
				arr[j]=arr[j+1];
				arr[j]=temp;
			}
		}
	}
		
	}

	
}

int main()
{
	
	int n=50000;
	int arr[n];
	
	srand(time(NULL));
	
	for(int i=0;i<n;i++){
		arr[i]=rand()%1000;
	}
	
	clock_t seqst=clock();
	Seqbubblesort(arr,n);
	clock_t seqen=clock();
	double duration=(double)(seqen-seqst)/CLOCKS_PER_SEC;
	cout<<duration*1000<<endl;
	
	clock_t parst=clock();
	Parbubblesort(arr,n);
	clock_t paren=clock();
	duration=(double)(paren-parst)/CLOCKS_PER_SEC;
	cout<<duration*1000<<endl;

	
	
	
	
	return 0;
}
