#include<iostream>
#include<ctime>
#include<omp.h>
#include<stack>

using namespace std;

class Graph{
	
	
	int **adj;
	int n;
	
	public:
		Graph(int v){
			n=v;
			
			adj= new int *[n];
			
			for(int i=0;i<n;i++){
				adj[i]=new int[n];
				
			}
		}
		
		void addEdge(int v,int w){
			adj[v][w]=1;
		}
		
		
		void SEQdfs(int src){
			
			stack<int> st;
			st.push(src);
			
			int vis[n]={0};
			
			
			vis[src]=1;
			
			while(!st.empty()){
				int node=st.top();
				st.pop();
				
				
				for(int i=0;i<n;i++){
					
					if(adj[node][i] and !vis[i])
					{
						vis[i]=1;
						st.push(i);
					}
				}
			}
		}
		
		
		void PARdfs(int src){
			stack<int> st;
			st.push(src);
			
			int vis[n]={0};
			
			
			vis[src]=1;
			
			while(!st.empty()){
				
				#pragma omp parallel
				{
					int node;
					#pragma omp single nowait
					{
						 node=st.top();
				         st.pop();	
					}
					
					
					#pragma omp for					
					for(int i=0;i<n;i++){
					
					  if(adj[node][i] and !vis[i])
					  {
						vis[i]=1;
						#pragma omp critical
						{
							st.push(i);
						}
						
					  }
				    }
					
				}
			
				
				
				
			}
		}
};

int main(){
	
	int n=10000;
	
	Graph g(n);
	
	
	for(int i=0;i<n;i++){
		for(int j=0;j<n;j++){
			if(i!=j) g.addEdge(i,j);
		}
	}
	
	clock_t stseq=clock();
	g.SEQdfs(0);
	clock_t enseq=clock();
	double duration=(double)(enseq-stseq)/CLOCKS_PER_SEC;
	
	cout<<duration*1000<<endl;
	
	clock_t stpar=clock();
	g.SEQdfs(0);
	clock_t enpar=clock();
	duration=(double)(enpar-stpar)/CLOCKS_PER_SEC;
	
	cout<<duration*1000<<endl;
	
	
	return 0;
}
