#include<iostream>
#include<ctime>
#include<cstdlib>
#include<omp.h>

using namespace std;



int * merge(int *arr,int l,int m,int r){
	
	int n1=m-l+1;
	int n2=r-m;
	
	int a[n1];
	int b[n2];
	
	for(int i=0;i<n1;i++){
		a[i]=arr[l+i];
	}
	
	for(int j=0;j<n2;j++){
		b[j]=arr[m+1+j];
	}
	
	
	int i=0;
	int j=0;
	int k=l;
	
	while(i<n1 and j<n2){
		
		if(a[i]<=b[i]){
			arr[k]=a[i];
			i++;
			k++;
		}
		else{
			arr[k]=b[j];
			j++;
			k++;
		}
	}
	
	
	while(i<n1){
		arr[k]=a[i];
			i++;
			k++;
	}
	
	while(j<n2){
			arr[k]=b[j];
			j++;
			k++;
	}
	
	return arr;
}

int * seqMergeSort(int *arr,int l,int r){
	if(l<r){
		int m=(l+r)/2;
		
		arr=seqMergeSort(arr,l,m);
		arr=seqMergeSort(arr,m+1,r);
		arr=merge(arr,l,m,r);
	}
	
	return arr;
}

int * parMergeSort(int *arr,int l,int r){
	

	
	
	if(l<r){
		
		int m=(l+r)/2;
		#pragma omp parallel sections
		{
		
		#pragma omp section 
		arr=parMergeSort(arr,l,m);
		
		#pragma omp section 
		arr=parMergeSort(arr,m+1,r);
			
		}
		
		arr=merge(arr,l,m,r);
	 }
	
	
return arr;
}


int main(){
	int n=500000;
	
	int *arr1=new int[n];
	int *arr2=new int[n];
	
	srand(time(NULL));
	
	for(int i=0;i<n;i++)
	{
	arr1[i]=rand()%1000;
	arr2[i]=rand()%1000;	
	} 
	
	omp_set_num_threads(4);
	
	
	
	clock_t seqst=clock();
	seqMergeSort(arr1,0,n);
	clock_t seqen=clock();
	double duration=(double)(seqen-seqst)/CLOCKS_PER_SEC;
	cout<<duration*1000<<endl;
	
	clock_t parst=clock();
	parMergeSort(arr2,0,n);
	clock_t paren=clock();
    duration=(double)(paren-parst)/CLOCKS_PER_SEC;
	cout<<duration*1000<<endl;
	
	
	return 0;
}
